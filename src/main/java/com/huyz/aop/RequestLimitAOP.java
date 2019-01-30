package com.huyz.aop;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.huyz.annotation.RequestLimit;
import com.huyz.enumeration.LimitArithmetic;
import com.huyz.exception.RequestTooManyException;

/**
 * 
 * <p>
 * Title: RequestLimitAspect3
 * </p>
 * <p>
 * 描述: 令牌桶算法
 * 在令牌桶算法中，存在一个桶，用来存放固定数量的令牌。算法中存在一种机制，以一定的速率往桶中放令牌。每次请求调用需要先获取令牌，只有拿到令牌，才有机会继续执行，否则选择选择等待可用的令牌、或者直接拒绝。
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author huyz
 * @date 2018年9月25日 下午3:56:05
 */
@Aspect
@Component
@Order(-20)
public class RequestLimitAOP {

	private final String BEGINTIME_KEY = "BEGINTIME";
	private final String VISITCOUNT_KEY = "VISITCOUNT";

	private Map<Object, Object> methodLimiter = new ConcurrentHashMap<>(160);

	private ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(10);

	@Pointcut(value = "@annotation(com.huyz.annotation.RequestLimit)")
	public void limitPointCut() {

	}

	@Before(value = "limitPointCut()")
	public void limit(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		RequestLimit requestlimit = method.getDeclaredAnnotation(RequestLimit.class);
		int limitcount = requestlimit.limitCount();// 限制次数
		int limittime = requestlimit.limitTime();// 限制时间
		TimeUnit limitunit = requestlimit.limitUnit();// 限制时间单位
		LimitArithmetic limitStrategy = requestlimit.limitStrategy();// 限流策略
		chooseStrategy(method, limitcount, limittime, limitunit, limitStrategy);
	}

	private void chooseStrategy(Method method, int limitcount, int limittime, TimeUnit limitunit,
			LimitArithmetic limitStrategy) {
		switch (limitStrategy) {
		case COUNT:
			count(method, limitcount, limittime, limitunit);
			break;
		case LEAKY_BUCKET:
			leakyBucket(method, limitcount, limittime, limitunit);
			break;
		case TOKEN_BUCKET:
			tokenBucket(method, limitcount, limittime, limitunit);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void count(Method method, int limitcount, int limittime, TimeUnit limitunit) {
		int permitsPerSecond = (int) timeConvertSecond(limitcount, limittime, limitunit);
		if (methodLimiter.get(method.getName()) == null) {
			Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
			concurrentHashMap.put(BEGINTIME_KEY, LocalDateTime.now());
			concurrentHashMap.put(VISITCOUNT_KEY, new AtomicInteger(0));
			methodLimiter.put(method.getName(), concurrentHashMap);
		} else {
			synchronized (BEGINTIME_KEY) {
				Map<String, Object> map = (Map<String, Object>) methodLimiter.get(method.getName());
				LocalDateTime time = LocalDateTime.now();
				if (Duration.between(((LocalDateTime) map.get(BEGINTIME_KEY)), time).getSeconds() > limittime) {
					map.put(BEGINTIME_KEY, time);
					((AtomicInteger) map.get(VISITCOUNT_KEY)).set(0);
				} else if (((AtomicInteger) map.get(VISITCOUNT_KEY)).incrementAndGet() > permitsPerSecond
						&& Duration.between(((LocalDateTime) map.get(BEGINTIME_KEY)), time).getSeconds() < limittime) {

					throw new RequestTooManyException("系统繁忙，稍后再试");
				}
			}
		}
	}

	/**
	 * 
	 * <p>
	 * MethodName: leakyBucket
	 * </p>
	 * <p>
	 * 描述: 漏桶算法
	 * </p>
	 * 
	 * @param method
	 * @param limitcount
	 * @param limittime
	 * @param limitunit
	 * 
	 * @author huyz
	 * @date 2018年9月26日
	 * @version 1.0
	 *
	 */
	@SuppressWarnings("unchecked")
	private void leakyBucket(Method method, int limitcount, int limittime, TimeUnit limitunit) {
		try {
			if (methodLimiter.get(method.getName()) == null) {
				methodLimiter.put(method.getName(), new ArrayBlockingQueue<>(11));
				// 最开始时候 设置一个读取request的线程
				newScheduledThreadPool.scheduleAtFixedRate(() -> {
					try {
						BlockingQueue<String> blockQueue = (BlockingQueue<String>) methodLimiter.get(method.getName());
						blockQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}, 0, limittime / limitcount <= 0 ? 1 : limittime / limitcount, limitunit);
			} else {

				BlockingQueue<String> blockQueue = (BlockingQueue<String>) methodLimiter.get(method.getName());
				blockQueue.add(method.getName());
			}
		} catch (IllegalStateException e) {
			throw new RequestTooManyException("系统繁忙,稍后再试");
		}
	}

	/**
	 * 
	 * <p>
	 * MethodName: tokenBucket
	 * </p>
	 * <p>
	 * 描述: 令牌漏桶算法
	 * </p>
	 * 
	 * @param method
	 * @param limitcount
	 * @param limittime
	 * @param limitunit
	 * 
	 * @author huyz
	 * @date 2018年9月26日
	 * @version 1.0
	 *
	 */
	private void tokenBucket(Method method, int limitcount, int limittime, TimeUnit limitunit) {
		double permitsPerSecond = timeConvertSecond(limitcount, limittime, limitunit);
		if (methodLimiter.get(method.getName()) == null) {
			RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);
			methodLimiter.put(method.getName(), rateLimiter);
		} else {
			RateLimiter rateLimiter = (RateLimiter) methodLimiter.get(method.getName());
			rateLimiter.acquire();
		}
	}

	private double timeConvertSecond(int limitcount, int limittime, TimeUnit limitunit) {
		double value = 0;
		switch (limitunit) {
		case NANOSECONDS:// 纳秒
			value = (limitcount / limittime) * 1000000000;
			break;
		case MICROSECONDS:
			value = (limittime / limitcount) * 1000000;
			break;
		case MILLISECONDS:
			value = (limittime / limitcount) * 1000;
			break;
		case SECONDS:
			value = limitcount / limittime;
			break;
		case MINUTES:
			value = limitcount / limittime;
			break;
		default:
			value = 1;
			break;
		}
		return value <= 0 ? 1 : value;
	}
}

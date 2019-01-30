package com.huyz.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import com.huyz.enumeration.LimitArithmetic;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * Create at 2018年9月11日 下午2:09:06
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName HealthArchive
 *
 *          Description:
 * 
 */
public @interface RequestLimit {

	/**
	 * 
	 * <p>MethodName: limit</p>
	 * <p>描述: 限制的次数</p>
	 * @return
	 * 
	 * @author huyz
	 * @date 2018年9月11日
	 * @version 1.0
	 *
	 */
	int limitCount() default 10;
	
	/**
	 * 
	 * <p>MethodName: time</p>
	 * <p>描述: 限制的时间</p>
	 * @return
	 * 
	 * @author huyz
	 * @date 2018年9月11日
	 * @version 1.0
	 *
	 */
	int limitTime()  default 1;
	/**
	 * 
	 * <p>MethodName: unit</p>
	 * <p>描述: 限制的时间单位</p>
	 * @return
	 * 
	 * @author huyz
	 * @date 2018年9月11日
	 * @version 1.0
	 *
	 */
	TimeUnit limitUnit() default TimeUnit.SECONDS;
	
	/**
	 * 
	 * <p>MethodName: limitStrategy</p>
	 * <p>描述: 限流策略</p>
	 * @return
	 * 
	 * @author huyz
	 * @date 2018年9月26日
	 * @version 1.0
	 *
	 */
	LimitArithmetic limitStrategy() default LimitArithmetic.TOKEN_BUCKET;
}

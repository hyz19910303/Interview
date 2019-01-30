package com.huyz.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.huyz.exception.RequestTooManyException;

/**
 * Create at 2018年5月14日 上午8:57:22
 *
 * @autuor EVIL
 *
 * @version 1.0
 *
 *          ProjectName Document
 *
 *          Description:
 * 
 */

@ControllerAdvice
public class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@Autowired
	protected HttpSession session;

	protected <V> Map<String, V> success(V value) {
		Map<String, V> map = this.createMap("", value);
		map.put("success", value);
		return map;
	}

	protected Map<String, Boolean> defaultSuccess() {
		return this.success(true);
	}

	protected <V> Map<String, V> error(V value) {
		Map<String, V> map = this.createMap("", value);
		map.put("error", value);
		return map;
	}

	protected Map<String, Boolean> defaultError() {
		return this.error(true);
	}

	protected <K, V> Map<K, Object> common(K key, Object value) {
		// HashMap<K, ?> result = new HashMap<>();
		HashMap<K, Object> result = new HashMap<K, Object>();
		result.put(key, value);
		return result;
	}

	private <K, V extends Object> Map<K, V> createMap(K T, V V) {
		return new HashMap<K, V>();
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handleException(Exception e) {
		Map<String, Object> common = common("success", false);
		if (e instanceof RequestTooManyException) {
			common.put("message", e.getMessage());
		} else if (e instanceof NoHandlerFoundException) {
			common.put("message", "你访问的页面不存在");
		} else {
			common.put("message", e.getMessage());
		}
		return common;
	}

}

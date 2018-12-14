package com.huyz.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huyz.annotation.RequestLimit;
import com.huyz.enumeration.LimitArithmetic;

/**
 * Create at 2018年9月26日 上午11:05:26
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName interview
 *
 *          Description:
 * 
 */

@RestController
public class LimitController_Test {

	@RequestMapping(value = { "/limit" })
	@RequestLimit(limitCount = 1, limitTime = 2, limitUnit = TimeUnit.SECONDS, limitStrategy = LimitArithmetic.TOKEN_BUCKET)
	public String limit(HttpServletRequest request) {
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm ss SSS"));
		// System.out.println(time);
		return time;
	}
}

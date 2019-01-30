package com.huyz.annotation;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Create at 2018年9月11日 下午3:48:47
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
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Limit {

	private LocalDateTime lastAccessTime;

	private TimeUnit unit;

	private AtomicInteger time;
}

package com.huyz.enumeration;

/**
 * Create at 2018年9月26日 上午9:45:51
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName interview
 *
 *          Description: 限流算法
 * 
 */
public enum LimitArithmetic {

	/**
	 * 计数器算法
	 */
	COUNT {

	},

	/**
	 * 漏桶算法
	 */
	LEAKY_BUCKET {

	},
	/**
	 * 令牌漏桶算法
	 */
	TOKEN_BUCKET {

	};

}

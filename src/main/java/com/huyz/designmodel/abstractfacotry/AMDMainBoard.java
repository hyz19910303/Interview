package com.huyz.designmodel.abstractfacotry;

/**
 * Create at 2018年8月10日 上午10:25:36
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName designmodel
 *
 *          Description: cpu的实现厂商之一AMD
 * 
 */
public class AMDMainBoard implements MainBoard {

	@Override
	public void board() {
		System.out.println("AMD  MainBoard");
	}

}

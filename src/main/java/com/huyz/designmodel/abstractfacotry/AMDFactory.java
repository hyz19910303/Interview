package com.huyz.designmodel.abstractfacotry;

/**
 * Create at 2018年8月10日 上午10:31:56
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName designmodel
 *
 *          Description:
 * 
 */
public class AMDFactory implements AbsteactFactory {

	@Override
	public CPU createCpu() {
		return new AMDCpu();
	}

	@Override
	public MainBoard createMainBoard() {
		return new AMDMainBoard();
	}

}

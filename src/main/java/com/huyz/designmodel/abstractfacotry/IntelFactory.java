package com.huyz.designmodel.abstractfacotry;

/**
 * Create at 2018年8月10日 上午10:30:57
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName designmodel
 *
 *          Description: intel 工厂
 * 
 */
public class IntelFactory implements AbsteactFactory {

	@Override
	public CPU createCpu() {
		return new IntelCpu();
	}

	@Override
	public MainBoard createMainBoard() {
		return new IntelMainBoard();
	}

}

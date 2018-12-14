package com.huyz.designmodel.abstractfacotry;

/**
 * Create at 2018年8月10日 上午10:22:42
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName designmodel
 *
 *          Description: 抽象工厂模式 抽象工厂类
 * 
 */
public interface AbsteactFactory {

	 CPU createCpu();

	 MainBoard createMainBoard();
}

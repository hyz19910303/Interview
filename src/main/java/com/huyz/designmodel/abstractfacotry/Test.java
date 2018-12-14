package com.huyz.designmodel.abstractfacotry;

/**
 * Create at 2018年8月10日 上午10:32:33
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName designmodel
 *
 *          Description: 抽象工厂 使用于有多个产品的，就行电脑 要么全部用intel 要么全部用 amd
 *          切换特别方便如果新增一个厂商如 小米 也会特别方便 但是 如果新增一个产品 就要修改工厂的代码 不方便。
 *          类似生活中的流水线上的作业，该条流水线上的所有产品都是相互匹配的和其他流水线的是不能搭配使用，你要是想在该流水线上
 *          新加一个小部件就会很麻烦，但是你要是新增一个产品 只要新增一个流水线就ok.很方便【对使用方而言】
 * 
 */
public class Test {

	public static void main(String[] args) {

		AbsteactFactory factory = null;
//		factory=new IntelFactory();
		factory = new AMDFactory();
		factory.createCpu().cpu();
		factory.createMainBoard().board();

	}

}

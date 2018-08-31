package com.huyz.designmodel.buildmodel;

import java.awt.Color;

import com.huyz.designmodel.buildmodel.Square.SquareBuilder;

/**
 * Create at 2018年8月10日 上午9:38:11
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
public class Test {

	public static void main(String[] args) {
		SquareBuilder build = new Square.SquareBuilder();
		build.setHeight(100).setWidth(200).setColor(Color.BLUE);
		Square square = build.build();
		square.draw();
	}

}

package com.huyz.designmodel.buildmodel;

import java.awt.Color;

/**
 * Create at 2018年8月10日 上午9:15:05
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName designmodel
 *
 *          Description: 建造者模式
 * 
 */
public class Square {

	private int height;

	private int width;

	private Color color;

	private Square(SquareBuilder build) {
		super();
		this.height = build.height;
		this.width = build.width;
		this.color = build.color;
	}

	public static class SquareBuilder {

		private int height;

		private int width;

		private Color color;

		public SquareBuilder() {
		}

		public SquareBuilder setHeight(int height) {
			this.height = height;
			return this;
		}

		public SquareBuilder setWidth(int width) {
			this.width = width;
			return this;
		}

		public SquareBuilder setColor(Color color) {
			this.color = color;
			return this;
		}

		public Square build() {
			// 检查参数合法性
			return new Square(this);
		}
	}

	public void draw() {
		System.out.println("高度:" + this.height + "宽度：" + this.width + "颜色：" + this.color.getRGB());
	}

}

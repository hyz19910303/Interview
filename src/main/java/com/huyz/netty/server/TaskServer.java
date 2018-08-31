package com.huyz.netty.server;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Create at 2018年8月28日 下午2:42:02
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
public class TaskServer {

	public static void main(String[] args) {
		try {
			new TaskServer().register(2112);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void register(int port) throws InterruptedException {
		NioEventLoopGroup boosgroup = new NioEventLoopGroup();
		NioEventLoopGroup workgroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boosgroup, workgroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024).handler(new LoggingHandler(LogLevel.INFO) {
					@Override
					protected String format(ChannelHandlerContext ctx, String message) {
						System.out.println(message);
						return super.format(ctx, message);
					}
				}).childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel channel) throws Exception {
						channel.pipeline().addLast(new StringEncoder());
						// 没有访问 一分钟后关闭
						channel.pipeline().addLast(new ReadTimeoutHandler(1, TimeUnit.MINUTES));
					}

				});
		ChannelFuture cf = serverBootstrap.bind(port).sync();

		cf.channel().closeFuture().sync();
		boosgroup.shutdownGracefully();
		workgroup.shutdownGracefully();
	}

}

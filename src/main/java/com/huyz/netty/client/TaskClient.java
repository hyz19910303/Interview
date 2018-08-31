package com.huyz.netty.client;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Create at 2018年8月28日 下午4:59:12
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
public class TaskClient {

	NioEventLoopGroup group = null;
	Bootstrap b;

	public static void main(String[] args) throws InterruptedException {
		TaskClient taskClient = new TaskClient();
		ChannelFuture connect = taskClient.connect("127.0.0.1", 2112);
		for (int i = 0; i < 100; i++) {
			Thread.sleep(500);
			connect.channel().writeAndFlush("message=> " + i);
		}
		connect.channel().closeFuture().sync();
	}

	private TaskClient() {
		group = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						// 超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
						sc.pipeline().addLast(new ReadTimeoutHandler(1, TimeUnit.MINUTES));
						// sc.pipeline().addLast(new ClientHandler()); //客户端业务处理类
					}
				});
	}

	public ChannelFuture connect(String address, int port) throws InterruptedException {
		ChannelFuture connect = b.connect(address, port).sync();
		return connect;
	}

}

package com.ruoyi.xindian.webSocket.config;

import com.ruoyi.xindian.webSocket.domain.ChattingRecord;
import com.ruoyi.xindian.webSocket.handler.ChatHandler;
import com.ruoyi.xindian.webSocket.service.ChattingRecordService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;


@Component
public class WebSocketServerComponent implements ApplicationListener<ContextClosedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerComponent.class);

	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	private ChannelFuture channelFuture;



	@Autowired
	private ChattingRecordService chattingRecordService;




	@Async
	@PostConstruct
	public void startServer() {
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_REUSEADDR, true);
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new HttpServerCodec());
							pipeline.addLast(new WebSocketServerProtocolHandler("/ecg", null, true, Integer.MAX_VALUE, false));
							pipeline.addLast(new StringDecoder());
							pipeline.addLast(new StringEncoder());
							pipeline.addLast(new ChatHandler(chattingRecordService));
						}
					});

			channelFuture = b.bind(9002).sync();
			LOGGER.info("WebSocket server started and listening on port 9002.");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LOGGER.error("Failed to start WebSocket server due to interruption.", e);
		}
	}

	@PreDestroy
	public void shutdown() {
		if (channelFuture != null) {
			channelFuture.channel().closeFuture().syncUninterruptibly();
		}
		if (bossGroup != null && !bossGroup.isShutdown()) {
			bossGroup.shutdownGracefully().awaitUninterruptibly();
		}
		if (workerGroup != null && !workerGroup.isShutdown()) {
			workerGroup.shutdownGracefully().awaitUninterruptibly();
		}
		LOGGER.info("WebSocket server stopped.");
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		System.out.println("Shutting down Netty server...");
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
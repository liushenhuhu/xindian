package com.ruoyi.xindian.webSocket.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.xindian.webSocket.domain.ChattingRecord;
import com.ruoyi.xindian.webSocket.service.ChattingRecordService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {



	/** 用户集合 */
	private static final Map<String, Channel> USER_MAP = new ConcurrentHashMap<>();

	/** Channel 绑定自己的用户 ID */
	public static final io.netty.util.AttributeKey<String> UID = io.netty.util.AttributeKey.newInstance("uid");

	private final ChattingRecordService chattingRecordService;

	public ChatHandler(ChattingRecordService chattingRecordService) {
		this.chattingRecordService = chattingRecordService;
	}



	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("Channel active: " + ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		String uid = ctx.channel().attr(UID).get();
		if (StringUtil.isNullOrEmpty(uid)) {
			super.channelInactive(ctx);
			return;
		}
		ctx.channel().attr(UID).set(null);
		USER_MAP.remove(uid);
		notifyLeave(ctx, uid);
		super.channelInactive(ctx);
		System.out.println("Channel active: " + ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
		String text = msg.text();
		JSONObject parseObj = JSONUtil.parseObj(text);
		System.out.println("Received message: " + text);
		Integer type = parseObj.getInt("t");
		String uid = parseObj.getStr("uid");
		String tid = parseObj.getStr("tid");
		handleMessageType(ctx, type, uid, tid,text,parseObj);
	}

	private void handleMessageType(ChannelHandlerContext ctx, Integer type, String uid, String tid,String text,JSONObject parseObj) {
		switch (type) {
			case 0:
				// 心跳
				break;
			case 1:
				// 用户加入聊天室
				USER_MAP.put(uid, ctx.channel());
				ctx.channel().attr(UID).set(uid);
				notifyJoin(ctx, uid);
				break;
			case 2:
				Channel targetChannel = USER_MAP.get(tid);
				if (targetChannel != null) {
					targetChannel.writeAndFlush(new TextWebSocketFrame(text));
				}
				notifySave(uid, tid,text,parseObj);
				break;
			case 9:
				// 用户退出聊天室
				USER_MAP.remove(uid);
				notifyLeave(ctx, uid);
				ctx.close();
				break;
			case 10:
				// 用户退出聊天室
				USER_MAP.remove(uid);
				notifyLeave(ctx, uid);
				ctx.close();
				break;
			default:
				System.out.println("Unsupported message type: " + ctx);
				break;
		}
	}

	/**
	 * 加入
	 * @param ctx
	 * @param uid
	 */
	private void notifyJoin(ChannelHandlerContext ctx, String uid) {
		USER_MAP.forEach((x, y) -> {
			if (!x.equals(uid)) {
				JSONObject json = new JSONObject();
				json.set("t", 1); // 注意这里应该是正确的消息类型
				json.set("uid", uid);
				json.set("type", "join");
				y.writeAndFlush(new TextWebSocketFrame(json.toString()));
			}
		});
	}

	/**
	 * 记录数据
	 * @param uid
	 */
	private void notifySave( String uid, String tid,String text,JSONObject parseObj) {


		ChattingRecord chattingRecord = new ChattingRecord();
		chattingRecord.setSenderId(uid);
		chattingRecord.setReceiverId(tid);
		chattingRecord.setContent(text);
		chattingRecord.setContentType(parseObj.getStr("type"));
		chattingRecord.setUserType(parseObj.getStr("userType"));
		chattingRecord.setCreateTime(new Date());
		chattingRecordService.insertChattingRecord(chattingRecord);

	}



	/**
	 * 退出
	 * @param ctx
	 * @param uid
	 */
	private void notifyLeave(ChannelHandlerContext ctx, String uid) {
		USER_MAP.forEach((x, y) -> {
			if (!x.equals(uid)) {
				JSONObject json = new JSONObject();
				json.set("t", 9);
				json.set("uid", uid);
				y.writeAndFlush(new TextWebSocketFrame(json.toString()));
			}
		});
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println("Exception caught in ChatHandler: " + cause);
		ctx.close();
	}
}
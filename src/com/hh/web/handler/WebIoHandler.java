/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:22:34
 *
 * */
package com.hh.web.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hh.message.IMessage;
import com.hh.message.request.HeartBeatRequest;
import com.hh.message.request.LogOnRequest;
import com.hh.web.test.MessageWriter;

/**
 * 处理真正与CoreServer交互的部分
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:22:44
 * @author Wangweiwei
 *
 * */
public class WebIoHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(WebIoHandler.class);
	private static int count = 99;
	private static final Object lock = new Object();
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		if(status == IdleStatus.WRITER_IDLE){//如果好长时间没有写数据的话就发送心跳
			HeartBeatRequest h = new HeartBeatRequest();
			session.write(h);
			logger.info("send heartbeat to coreserver ...........");
		}
		
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		//发送登陆信息
		LogOnRequest lor = new LogOnRequest();
		lor.setServerCode(1);
		lor.setPassword("abcdefghijklmnop");
		session.write(lor);
		logger.info(session.getAttribute("name") +" send LogOnRequest to coreServer ");
		//add session to hashset
		MessageWriter.addSession(session);
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("New session between coreserver and web server created!");
		sb.append(" Local address :" + session.getLocalAddress());
		sb.append(", Remote Address :" + session.getRemoteAddress());
		logger.info(sb.toString());
		session.setAttribute("name", "session" + getSessionCount());
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		logger.info("<<< "+session.getAttribute("name") +" Receive message{" + message+"} from CoreServer");
		IMessage msg = (IMessage)message;
		//test vtrId
		if(msg.getVtrId() != 0l){
			long vtrId = Long.valueOf(session.getAttribute("vtrId",0).toString());
			if(msg.getVtrId() != vtrId){
				String line = "["+session.getAttribute("name") + "] web请求vtrId为"+vtrId+"的信息,系统却返回了vtrId为"+msg.getVtrId()+"的信息";
				logger.error(line);
				throw new RuntimeException(line);
			}
		}
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info(">>> "+session.getAttribute("name") + " had sent " + message + " to coreServer");
		//test vtrId
		IMessage msg = (IMessage)message;
		if(msg.getVtrId() != 0l){
			session.setAttribute("vtrId", msg.getVtrId());
		}
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info(session.getAttribute("name") + " close connection");
		MessageWriter.removeSession(session);
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.info("Exception occured! Try to close the connection!");
		session.close(true);
		logger.error(cause.getMessage(),cause);
		
	}
	
	private static int getSessionCount(){
		synchronized(lock){
			count++;
		}
		return count;
	}
}

/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午12:50:50
 *
 * */
package com.hh.web.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.hh.web.codec.factory.MessageCodecFactory;
import com.hh.web.handler.WebIoHandler;

/**
 *  模拟Web端，用于向coreServer发送请求
 * 
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午12:50:59
 * @author Wangweiwei
 *
 * */
public class WebClient {

	/**连接CoreServer的连接器*/
	IoConnector connector = null;
	/**CoreServer端的IP与端口号*/
	private static String IP = null;
	private static int PORT = 9009;
	/**连接超时*/
	private static int CONNECT_TIME_OUT = 5000;
	/**心跳发送间隔*/
	private static int HEART_BEAT_DELAY = 3000;
	
	static{
		Properties config = new Properties();
		try {
			config.load(ClassLoader.getSystemResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		IP = config.getProperty("core_ip");
		PORT = Integer.valueOf(config.getProperty("core_port"));
		CONNECT_TIME_OUT = Integer.valueOf(config.getProperty("connect_time_out"));
		HEART_BEAT_DELAY = Integer.valueOf(config.getProperty("heart_beat_delay"));
	}
	
	public WebClient(){
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(CONNECT_TIME_OUT);
		connector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, HEART_BEAT_DELAY);
		connector.getFilterChain().addLast("executor", new ExecutorFilter(Executors.newCachedThreadPool()));
		//connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessageCodecFactory()));
		connector.setHandler(new WebIoHandler());
		ConnectFuture f = connector.connect(new InetSocketAddress(IP,PORT));
		//f.awaitUninterruptibly();
	}
	
}

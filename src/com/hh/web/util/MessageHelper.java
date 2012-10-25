/**
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:52:04
 *
 * */
package com.hh.web.util;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hh.message.IMessage;
import com.hh.message.request.HeartBeatRequest;
import com.hh.message.request.LogOnRequest;
import com.hh.message.request.SendTextRequest;
import com.hh.message.response.GeneralResponse;
import com.hh.message.response.HeartBeatResponse;
import com.hh.message.response.LogOnResponse;

/**
 * 用于解析与反解析message的帮助类
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:52:28
 * @author Wangweiwei
 *
 * */
public class MessageHelper {
	/**存储cmdKey到IMessage子类的映射*/
	private final Map<Short,Class> map = new HashMap<Short,Class>();
	private static final MessageHelper instance = new MessageHelper();
	private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);
	private MessageHelper(){
		//初始化map的地方
		map.put((short) 13, LogOnRequest.class);
		map.put((short)14, LogOnResponse.class);
		map.put((short)15,HeartBeatRequest.class);
		map.put((short)16,HeartBeatResponse.class);
		map.put((short)2005, SendTextRequest.class);
		map.put((short)2050,GeneralResponse.class);
		
	}
	public static MessageHelper getInstance(){
		return instance;
	}
	

	/**
	 * 此方法将IMessage子类包装成以0x08 0x06开头的协议 
	 * @version 1.0
	 * @params message
	 * @return byte[]
	 * @notes
	 * @description 
	 * @time 2012-10-24 上午10:37:03
	 * @author Wangweiwei
	 *
	 * */
	public byte[] wrapMessage(IMessage message){
		ByteBuffer buffer = ByteBuffer.allocate(IMessage.DEFAULT_BUFFER_SIZE);
		buffer.order(ByteOrderUtil.ORDER);
		buffer.put(IMessage.COMMAND_START);//命令开始
		byte[] body = message.getBytes();
		buffer.putShort((short) body.length);//数据体长度
		buffer.putShort(message.getCommandKey());//命令key
		buffer.putInt(0);//0占位
		buffer.put(body);//message体
		buffer.flip();
		body = new byte[buffer.limit()];
		buffer.get(body);
		buffer.limit(buffer.limit()+1);
		buffer.put(getXOrCode(body));
		buffer.position(0);
		body = new byte[buffer.limit()];
		buffer.get(body);
		return body;
	}

	/**
	 * 此方法将一个完整的协议字节流转换成IMessage的子类
	 * @version 1.0
	 * @params full
	 * @return IMessage
	 * @notes
	 * @description 
	 * @time 2012-10-24 上午10:39:38
	 * @author Wangweiwei
	 *
	 * */
	public IMessage parseMessage(byte[] full){
		ByteBuffer buffer = ByteBuffer.wrap(full);
		buffer.order(ByteOrderUtil.ORDER);
		//先获得commandKey
		Short cmdKey = buffer.getShort(4);
		IMessage message = null;
		try{
			message = (IMessage)map.get(cmdKey).newInstance();
			short len = buffer.getShort(2);
			byte[] body = new byte[len];
			buffer.position(10);
			buffer.get(body);
			message = message.parse(body);
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
		}

		return message;
	}

	/**
	 * 获取一段字节数组的验证码，即取异或
	 * @version 1.0
	 * @params bytes
	 * @return byte
	 * @notes
	 * @description 
	 * @time 2012-10-24 上午10:31:04
	 * @author Wangweiwei
	 *
	 * */
	public byte getXOrCode(byte[] bytes){
		byte result = 0;
		int i = 0;
		for(byte b : bytes){
			if((++i) == 1)
				result = b;
			else 
				result ^= b;
		}
		return result;
	}

}

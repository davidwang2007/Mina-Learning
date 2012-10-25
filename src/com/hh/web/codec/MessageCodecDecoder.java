/**
 * @author David
 * */
package com.hh.web.codec;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hh.message.IMessage;
import com.hh.web.util.ByteOrderUtil;
import com.hh.web.util.MessageHelper;
import com.hh.web.util.PrintHelper;

/**
 * 此类是用来解析CoreServer回应的字节流的类
 * 
 * <table border="1">
 * <caption>数据体格式 </caption>
 * <tr><td>命令开始</td><td>{0x08,0x06}</td></tr>
 * <tr><td>命令长度</td><td>2bytes</td></tr>
 * <tr><td>命令key</td><td>2bytes</td></tr>
 * <tr><td>0占位</td><td>4bytes</td></tr>
 * <tr><td>数据体</td><td>n bytes</td></tr>
 * <tr><td>校验码</td><td>1byte</td></tr>
 * </table>
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:19:01
 * @author Wangweiwei
 *
 * */
public class MessageCodecDecoder extends CumulativeProtocolDecoder {

	private static final Logger logger = LoggerFactory.getLogger(MessageCodecDecoder.class);
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		in.order(ByteOrderUtil.ORDER);
		short n = 0;
		if(in.remaining() >= 4){//先看是否足够开始的10字节
			//读取命令长度
			n = in.getShort(2);//这种方法不会移动buffer中的指针
			/* 数据体长度可以为0
			if(n == 0)
				throw new RuntimeException("数据体长度为0! Error!");
			*/
			if(!(in.remaining() >= 11 + n))//如果不是一个完整的流则不能解析，证明还未接收完毕
				return false;
				
		}else{
			return false;
		}
		//通过检验之后开始获得一个协议的完整bytes
		byte[] frame = new byte[11+n];
		in.get(frame);
		PrintHelper.print("<<< 接收到完整数据包  ", frame);
		//解析该bytes,如果有必要的话可以验证一下  结合最后一个字节的验证码
		IMessage message = MessageHelper.getInstance().parseMessage(frame);
		out.write(message);
		return true;//????
	}

}

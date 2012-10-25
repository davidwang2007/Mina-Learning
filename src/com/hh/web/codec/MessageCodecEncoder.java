/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:21:13
 *
 * */
package com.hh.web.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.hh.message.IMessage;
import com.hh.web.util.MessageHelper;
import com.hh.web.util.PrintHelper;

/**
 * 此类是用来编码AbstractMessage到字节流的
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:21:25
 *
 * */
public class MessageCodecEncoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		IMessage msg = (IMessage)message;
		IoBuffer buffer = IoBuffer.allocate(1024).setAutoExpand(true);
		buffer.put(MessageHelper.getInstance().wrapMessage(msg));
		buffer.flip();
		byte[] bs = new byte[buffer.limit()];
		buffer.get(bs);
		PrintHelper.print(">>> 发送数据包为  ", bs);
		buffer.flip();
		out.write(buffer);
	}

}

/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午2:35:37
 *
 * */
package com.hh.message.request;

import java.nio.ByteBuffer;

import com.hh.message.IMessage;
import com.hh.web.util.ByteOrderUtil;

/**
 * 文本下发
 * 
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午2:35:47
 * @author Wangweiwei
 *
 * */
public class SendTextRequest implements IMessage {

	private long vtrId;
	/**下发类型*/
	private byte flag;
	/**信息内容*/
	private String message;
	
	@Override
	public byte[] getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
		buffer.order(ByteOrderUtil.ORDER);
		buffer.putLong(vtrId);
		buffer.put(flag);
		buffer.put(message.getBytes(UTF8));
		buffer.flip();
		byte[] bs = new byte[buffer.limit()];
		buffer.get(bs);
		buffer = null;
		return bs;
	}

	@Override
	public IMessage parse(byte[] body) {
		ByteBuffer buffer = ByteBuffer.wrap(body);
		buffer.order(ByteOrderUtil.ORDER);
		vtrId = buffer.getLong();
		flag = buffer.get();
		byte[] bs = new byte[buffer.limit() - buffer.position()];
		buffer.get(bs);
		message = new String(bs,UTF8);
		return this;
	}

	@Override
	public short getCommandKey() {
		// TODO Auto-generated method stub
		return 2005;
	}

	public long getVtrId() {
		return vtrId;
	}

	public void setVtrId(long vtrId) {
		this.vtrId = vtrId;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SendTextRequest [vtrId=" + vtrId + ", flag=" + flag
				+ ", message=" + message + "]";
	}

}

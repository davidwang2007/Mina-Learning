package com.hh.message.response;

import java.nio.ByteBuffer;

import com.hh.message.IMessage;
import com.hh.web.util.ByteOrderUtil;

public class GeneralResponse implements IMessage {
	
	private long vtrId;
	/**0:成功,1:失败,2发送成功终端无响应*/
	private byte flag;
	/**成功失败原因描述*/
	private String message;
	
	@Override
	public byte[] getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
		buffer.order(ByteOrderUtil.ORDER);
		buffer.putLong(vtrId);
		buffer.put(flag);
		buffer.put(message.getBytes(GBK));
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
		message = new String(bs,GBK);
		return this;
	}

	@Override
	public short getCommandKey() {
		// TODO Auto-generated method stub
		return 2050;
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
		return "GeneralResponse [vtrId=" + vtrId + ", flag=" + flag
				+ ", message=" + message + "]";
	}

}

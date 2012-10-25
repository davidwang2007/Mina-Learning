package com.hh.message.request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;

import com.hh.message.IMessage;
import com.hh.web.util.ByteOrderUtil;

public class LogOnRequest implements IMessage {

	/**core.xml中的*/
	private int serverCode;
	private String password;
	
	@Override
	public byte[] getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
		buffer.order(ByteOrderUtil.ORDER);
		buffer.putInt(serverCode);
		buffer.put(password.getBytes(UTF8));
		buffer.flip();
		byte[] bs = new byte[buffer.limit()];
		buffer.get(bs);
		buffer = null;
		return bs;
	}

	@Override
	public IMessage parse(byte[] body) {
		ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);
		buffer.order(ByteOrderUtil.ORDER);
		serverCode = buffer.getInt();
		byte[] bs = new byte[buffer.limit() - buffer.position()];
		buffer.get(bs);
		password = new String(bs,UTF8);
		buffer = null;
		return this;
	}

	@Override
	public short getCommandKey() {
		// TODO Auto-generated method stub
		return 13;
	}

	public int getServerCode() {
		return serverCode;
	}

	public void setServerCode(int serverCode) {
		this.serverCode = serverCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LogOnRequest [serverCode=" + serverCode + ", password="
				+ password + "]";
	}

	@Override
	public long getVtrId() {
		// TODO Auto-generated method stub
		return 0;
	}

}

/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午11:16:04
 *
 * */
package com.hh.message.response;

import com.hh.message.IMessage;

/**
 * 
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午11:16:13
 * @author Wangweiwei
 *
 * */
public class HeartBeatResponse implements IMessage {

	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return new byte[0];
	}

	@Override
	public IMessage parse(byte[] body) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public short getCommandKey() {
		// TODO Auto-generated method stub
		return 16;
	}

	@Override
	public String toString() {
		return "HeartBeatResponse []";
	}

	@Override
	public long getVtrId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午11:13:35
 *
 * */
package com.hh.message.request;

import com.hh.message.IMessage;

/**
 * web向coreServer发的心跳
 * 
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午11:13:49
 * @author Wangweiwei
 *
 * */
public class HeartBeatRequest implements IMessage {

	
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
		return 15;
	}

	@Override
	public String toString() {
		return "HeartBeatRequest []";
	}

	@Override
	public long getVtrId() {
		// TODO Auto-generated method stub
		return 0;
	}

}

/**
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午11:09:18
 *
 * */
package com.hh.message.response;

import com.hh.message.IMessage;

/**
 * WEB端登陆后CoreServer返回的命令
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午11:09:29
 * @author Wangweiwei
 *
 * */
public class LogOnResponse implements IMessage {

	private String welcome;
	
	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return welcome.getBytes(UTF8);
	}

	@Override
	public IMessage parse(byte[] body) {
		// TODO Auto-generated method stub
		welcome = new String(body,UTF8);
		return this;
	}

	@Override
	public short getCommandKey() {
		// TODO Auto-generated method stub
		return 14;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	@Override
	public String toString() {
		return "LogOnResponse [welcome=" + welcome + "]";
	}

	@Override
	public long getVtrId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}

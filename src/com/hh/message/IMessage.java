/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:59:08
 *
 * */
package com.hh.message;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 协议的基接口
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:59:15
 * @author Wangweiwei
 *
 * */
public interface IMessage {

	/**utf8编码*/
	Charset UTF8 = Charset.forName("utf-8");
	/**gbk编码*/
	Charset GBK = Charset.forName("GBK");
	/**命令开始标记*/
	byte[] COMMAND_START = {0x08,0x06};
	/**默认缓冲区大小*/
	int DEFAULT_BUFFER_SIZE = 1024;
	Logger logger = LoggerFactory.getLogger(IMessage.class);

	/**
	 * 每个协议只负责将息的内容部分生成为bytes数组
	 * @version 1.0
	 * @params 
	 * @return byte[]
	 * @notes
	 * @description 
	 * @time 2012-10-24 上午10:01:14
	 * @author Wangweiwei
	 *
	 * */
	byte[] getBytes();

	/**
	 * 每个协议只负责将自己对应的bytes解析成自己,此方法由帮助类调用
	 * @version 1.0
	 * @params body
	 * @return IMessage
	 * @notes
	 * @description 
	 * @time 2012-10-24 上午10:02:56
	 * @author Wangweiwei
	 *
	 * */
	IMessage parse(byte[] body);

	/**
	 * 不同的协议以commandKey作区分，所以每个协议里面应有此方法以相互区别
	 * 
	 * @version 1.0
	 * @params 
	 * @return short
	 * @notes
	 * @description 
	 * @time 2012-10-24 上午10:00:22
	 * @author Wangweiwei
	 *
	 * */
	short getCommandKey();
	/**
	 * 大部分协议中都包括vtrId所以最好写上此方法
	 * 
	 * @version 1.0
	 * @params 
	 * @return long
	 * @notes
	 * @description 
	 * @time 2012-10-24 下午2:46:24
	 * @author Wangweiwei
	 *
	 * */
	long getVtrId();
}

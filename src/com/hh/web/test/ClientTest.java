/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午1:03:23
 *
 * */
package com.hh.web.test;

import com.hh.web.client.WebClient;

/**
 * 此类用于测试web向coreserver多连接的情况下，返回信息是否会冲突 
 * 
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午1:03:52
 * @author Wangweiwei
 *
 * */
public class ClientTest {

	/**几个客户端对应几个session*/
	private static final int CLIENT_COUNT = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MessageWriter().start();
		for(int i = 0; i < CLIENT_COUNT; i++){
			new WebClient();
		}
	}

}

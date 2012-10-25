/**
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:45:01
 *
 * */
package com.hh.web.util;

/**
 * 
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 上午9:45:13
 * @author Wangweiwei
 *
 * */
public class PrintHelper {


	public static void print(String msg,byte[] bytes){
		StringBuilder sb = new StringBuilder(msg == null ? "" : msg);
		for(byte b : bytes){
			sb.append(Integer.toHexString(b & 0xff)).append(" ");
		}
		System.out.println(sb.toString());
	}
}

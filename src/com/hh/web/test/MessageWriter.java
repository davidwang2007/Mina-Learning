/**
 * 
 * @author Wangweiwei
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午2:55:27
 *
 * */
package com.hh.web.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hh.message.request.SendTextRequest;


/**
 * 此类主要负责向coreServer不停的发送消息体
 * 
 * @version 1.0
 * @notes
 * @description 
 * @time 2012-10-24 下午2:56:10
 * @author Wangweiwei
 *
 * */
public class MessageWriter extends Thread {

	/**此集合用于存储连接到coreServer的连接session,以用来发数据*/
	private static final Set<IoSession> pool = new HashSet<IoSession>();
	private static final Logger logger = LoggerFactory.getLogger(MessageWriter.class);
	/**发送命令间隔*/
	private static final long DELAY = 9000l;
	@Override
	public void run() {
		while(true){
			Iterator<IoSession> it = null;
			synchronized(pool){
				if(pool.size() == 0){
					try{pool.wait();}catch(Exception ex){ex.printStackTrace();}
				}
				it = pool.iterator();
				IoSession session = null;
				SendTextRequest r = null;
				long vtrId = 0l;
				while(it.hasNext()){
					session = it.next();
					vtrId = Long.valueOf(session.getAttribute("name").toString().replace("session",""));
					r = new SendTextRequest();
					r.setFlag((byte) (0x01));
					r.setVtrId(vtrId);
					r.setMessage("hello send text message");
					session.write(r);
				}
			}
			try{Thread.currentThread().sleep(DELAY);}catch(Exception ex){ex.printStackTrace();}
		}
	}
	
	public static void addSession(IoSession session){
		synchronized(pool){
			pool.add(session);
			pool.notifyAll();
		}
	}
	
	public static void removeSession(IoSession session){
		synchronized(pool){
			pool.remove(session);
			System.err.println("session pool size = " + pool.size());
			if(pool.size() == 0){
				System.exit(0);
			}
		}
	}
}

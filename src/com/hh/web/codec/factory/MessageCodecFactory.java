package com.hh.web.codec.factory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.hh.web.codec.MessageCodecDecoder;
import com.hh.web.codec.MessageCodecEncoder;

public class MessageCodecFactory implements ProtocolCodecFactory {

	private final ProtocolDecoder decoder;
	private final ProtocolEncoder encoder;
	public MessageCodecFactory(){
		decoder = new MessageCodecDecoder();
		encoder = new MessageCodecEncoder();
	}
	
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

}

package orderservice.server.core.netty.websocket;

import io.netty.channel.ChannelHandler;

public interface INettySocketSetter {

	public ChannelHandler getChildHandler();
	
	
	public ChannelHandler getLastHandler();
	
}

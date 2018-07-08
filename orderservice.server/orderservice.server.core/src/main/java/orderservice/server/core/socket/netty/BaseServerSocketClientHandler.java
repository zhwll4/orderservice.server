package orderservice.server.core.socket.netty;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.StringUtil;
import orderservice.server.core.socket.netty.websocket.NettyWebSocketServer;

public abstract class BaseServerSocketClientHandler<T>  extends  SimpleChannelInboundHandler<T> {

	
	protected String clientId=null;
	protected ScheduledFuture<?> loginSchedule;
	
	protected ScheduledFuture<?> scheduleLoginChecker(ChannelHandlerContext ctx,int seconds){
		loginSchedule = ctx.executor().schedule(new DefaultConnectionLoginChecker(ctx), seconds, TimeUnit.SECONDS);
		
		return loginSchedule;
	}
	
	protected boolean cancelLoginSchedule(){
		if(this.loginSchedule!=null){
			return this.loginSchedule.cancel(false);
		}
		return true;
	}
	
    protected class DefaultConnectionLoginChecker implements Runnable{
    	ChannelHandlerContext ctx;
        public DefaultConnectionLoginChecker(ChannelHandlerContext ctx) {
            // TODO Auto-generated constructor stub
        	this.ctx = ctx;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if(StringUtil.isNullOrEmpty(clientId)){
                ctx.close();
            }
        }
    } 
	
}

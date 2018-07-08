package orderservice.server.core.socket.netty.websocket;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import orderservice.server.core.socket.netty.BaseServerSocketClientHandler;

public abstract class BaseWebSocketClientHandler<T> extends  BaseServerSocketClientHandler<T> {

	
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
		
        if (StringUtil.isNullOrEmpty(this.clientId)) {
           //先去登录
        	handlerLogin(ctx,msg);
        }else{
        	handlerMessage(ctx,msg);
        }
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	this.scheduleLoginChecker(ctx, 30);
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        
    	this.cancelLoginSchedule();
    	super.handlerRemoved(ctx);
    	
    	channelClosed(ctx);
    	
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       
        channelClosed(ctx);
        
        ctx.channel().close();
        
    }
    
    public abstract void handlerLogin(ChannelHandlerContext ctx, T msg);
    public abstract void handlerMessage(ChannelHandlerContext ctx, T msg);
    public abstract void channelClosed(ChannelHandlerContext ctx);
}

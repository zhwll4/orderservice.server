package orderservice.server.core.socket.netty.websocket;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tomcat.util.net.SSLUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.internal.StringUtil;
import orderservice.server.core.basic.SSLUtils;
import orderservice.server.core.socket.netty.BaseSocketServer;

public class NettyWebSocketServer extends BaseSocketServer{

	protected boolean isClientdMode = false;
	
	private ChannelPipeline pipeline;
	
	public NettyWebSocketServer(String host, Integer port) {
		super(host, port, 10, null, null, null, null);
		// TODO Auto-generated constructor stub
	}
	
	public NettyWebSocketServer(String host, Integer port, int workThreads, String sslpath, String ssltype,
			String password, String sslinstance,boolean isClientdMode) {
		super(host, port, workThreads, sslpath, ssltype, password, sslinstance);
		this.isClientdMode = isClientdMode;
		// TODO Auto-generated constructor stub
	}
	
	
	public <T extends ChannelHandler> void  startServer(Map<String,Class<T>> handlers) {
		// Boss线程：由这个线程池提供的线程是boss种类的，用于创建、连接、绑定socket，
		// （有点像门卫）然后把这些socket传给worker线程池。
		// 在服务器端每个监听的socket都有一个boss线程来处理。在客户端，只有一个boss线程来处理所有的socket。
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// Worker线程：Worker线程执行所有的异步I/O，即处理操作
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			// ServerBootstrap 启动NIO服务的辅助启动类,负责初始话netty服务器，并且开始监听端口的socket请求
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup);
			// 设置非阻塞,用它来建立新accept的连接,用于构造serversocketchannel的工厂类
			b.channel(NioServerSocketChannel.class);
			
			b.childHandler(new ChildChannelHandler(handlers));
			
			Channel ch ;
			if(StringUtil.isNullOrEmpty(host)){
				ch = b.bind(port).sync().channel();
			}else{
				ch = b.bind(host,port).sync().channel();
			}
			
			ch.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	public ChannelPipeline getPipeline(){
		return this.pipeline;
	}
	
	class ChildChannelHandler<T extends ChannelHandler> extends ChannelInitializer<Channel>{
		
		private Map<String,Class<T>> handlers;
		
		public ChildChannelHandler(Map<String, Class<T>> handlers){
			this.handlers = handlers;
		}
		
		
		@Override
		protected void initChannel(Channel ch) throws Exception {
			 pipeline = ch.pipeline();
	        
			if(!StringUtil.isNullOrEmpty(sslpath)&&password!=null){
				pipeline.addLast(new SslHandler(SSLUtils.createSSLEngine(ssltype, sslpath, password, sslinstance, isClientdMode)));
			}
			
			pipeline.addLast("http-codec",new HttpServerCodec());
			pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
			pipeline.addLast("http-chunked",new ChunkedWriteHandler());
			
			for(String name : handlers.keySet()){
				pipeline.addLast(name,handlers.get(name).newInstance());
			}
			
			
		}
		
	}
}


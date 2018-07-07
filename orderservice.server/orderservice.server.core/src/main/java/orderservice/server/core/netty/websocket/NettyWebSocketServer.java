package orderservice.server.core.netty.websocket;

import java.util.concurrent.ConcurrentHashMap;

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
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.internal.StringUtil;

public class NettyWebSocketServer {

	
	private String hostIp;
	private int hostPort;
	private INettySocketSetter socketSetter;
	
	private ConcurrentHashMap<String,DefaultWebSocketServerHandler> authClients = new ConcurrentHashMap<String,DefaultWebSocketServerHandler>();
	
	
	
	public NettyWebSocketServer(String hostIp,int hostPort,INettySocketSetter socketSetter){
		
		this.hostIp = hostIp;
		this.hostPort = hostPort;
		this.socketSetter = socketSetter;
		
	}
	
	private NettyWebSocketServer getServer(){
		return this;
	}
	
	public void configServer() {
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
			// ChildChannelHandler 对出入的数据进行的业务操作,其继承ChannelInitializer
			
			ChannelHandler  chandler= null;
			if(socketSetter!=null){
				chandler = socketSetter.getChildHandler();
			}
			
			if(chandler == null){
				chandler = new ChildChannelHandler();
			}
			
			b.childHandler(chandler);

			Channel ch ;
			if(StringUtil.isNullOrEmpty(hostIp)){
				ch = b.bind(hostPort).sync().channel();
			}else{
				ch = b.bind(hostIp,hostPort).sync().channel();
			}
			ch.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	class ChildChannelHandler extends ChannelInitializer<Channel>{
		
		@Override
		protected void initChannel(Channel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();
	         
			pipeline.addLast("http-codec",new HttpServerCodec());
			pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
			pipeline.addLast("http-chunked",new ChunkedWriteHandler());
			
			//ChannelHandler chandler = socketSetter.getLastHandler();
			ChannelHandler  chandler= null;
			if(socketSetter!=null){
				chandler = socketSetter.getLastHandler();
			}
			
			if(chandler == null){
				DefaultWebSocketServerHandler dfb = new DefaultWebSocketServerHandler();
				dfb.setNettyServer(getServer());
				chandler = dfb;
			}
			
			pipeline.addLast("handler",chandler);
		}
		
	}
}


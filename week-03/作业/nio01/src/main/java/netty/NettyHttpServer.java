package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyHttpServer  {
    public static void main(String[] args) throws Exception{
        int port = 8808;

        //负责监听客户端的连接请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //建立连接后交给workerGroup进行处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG,128) //设置队列得到链接个数
                    .childOption(ChannelOption.TCP_NODELAY,true) //TCP参数，立即发送数据，默认值为Ture
                    .childOption(ChannelOption.SO_KEEPALIVE,true) //Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时。Netty默认关闭该功能。
                    .childOption(ChannelOption.SO_REUSEADDR,true) //地址复用
                    .childOption(ChannelOption.SO_RCVBUF,32 * 1024) //TCP数据接收缓冲区大小
                    .childOption(ChannelOption.SO_SNDBUF,32 * 1024) //TCP数据发送缓冲区大小
                    .childOption(EpollChannelOption.SO_REUSEPORT,true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT); //ByteBuf的分配器
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInitializer());

            ChannelFuture ch = b.bind(port).sync();
            System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            if (ch.isSuccess()) {
                System.out.println("启动成功");
            }
            ch.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

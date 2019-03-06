package org.jim.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MyNIOServer {

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    public void init() throws IOException  {
        serverSocketChannel = ServerSocketChannel.open();

        // 非阻塞
        serverSocketChannel.configureBlocking(false);

        // 监听端口号
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 8788));
        System.out.println("Server Socket Channel listening on 8788");


        selector = Selector.open();

        // 注册IO事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server Socket Channel accepting");
    }

    public void listen() throws IOException {
        for(;;) {
            // 阻塞式获取IO就绪事件列表
            System.out.println("Begin select");
            selector.select();
            System.out.println("End select");

            Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
            while (itr.hasNext()) {
                SelectionKey sk = itr.next();
                System.out.println(sk.readyOps());

                // 接受一个新的连接
                if (sk.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 非阻塞
                    socketChannel.configureBlocking(false);
                    // 注册IO事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    echo(sk);
                }
                itr.remove();
            }
        }
    }

    public void echo(SelectionKey sk) throws IOException {
        SocketChannel socketChannel = (SocketChannel)sk.channel();

        // 申请堆外内存
        ByteBuffer buffer = ByteBuffer.allocate(512);

        // 数据从内核空间拷贝到用户空间
        int len = -1;
        try {
            len = socketChannel.read(buffer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (-1 == len) {
            socketChannel.close();
            System.out.println("Socket Channel closed");
        } else {
            String msg = new String(buffer.array());
            System.out.println("READ: " + msg);
            String rsg = "ABCECHO " + msg;

            // 数据从用户空间拷贝到内核空间
            buffer.clear();
            buffer = ByteBuffer.wrap(rsg.getBytes());
            int num = socketChannel.write(buffer);
            System.out.println("Written " + num);
        }
    }

    public static void main(String[] args) throws IOException {
        MyNIOServer nioServer = new MyNIOServer();
        nioServer.init();
        nioServer.listen();
    }
}

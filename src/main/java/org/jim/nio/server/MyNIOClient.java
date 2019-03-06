package org.jim.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MyNIOClient {

    private SocketChannel socketChannel;

    public void connect() throws IOException {
        // 连接server
        socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8788));
        System.out.println("Connected");
    }

    public void send(String msg) throws IOException, InterruptedException {
        System.out.println("To send: " + msg);
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        // 数据从用户空间拷贝到内核空间
        socketChannel.write(buffer);
        buffer.clear();
        System.out.println("Sent");

        Thread.sleep(2_000L);

        // 等待server的回复
        int len = socketChannel.read(buffer);
        System.out.println(len);
        System.out.println("Received: " + new String(buffer.array()));
    }

    public void close() throws IOException {
        socketChannel.close();
    }
}

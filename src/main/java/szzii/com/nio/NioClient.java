package szzii.com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author szz
 */
public class NioClient {

    private static Selector selector;
    private static SocketChannel socketChannel;
    private static String host = "120.53.7.32";
    private static Integer port = 6669;


    public static ThreadLocal<ByteBuffer> tl = ThreadLocal.withInitial(()->ByteBuffer.allocate(1024 * 10));

    public static void main(String[] args) throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(host,port));
        socketChannel.register(selector,SelectionKey.OP_READ);
        if (!socketChannel.isConnectionPending()){
            socketChannel.finishConnect();
        }
        new Thread(()->{
            readMessage();
        }).start();

        System.out.println("请输入用户名:");
        Scanner scanner1 = new Scanner(System.in);
        String name = scanner1.next();
        sendMessage("#"+name);

        while (true){
            Scanner scanner = new Scanner(System.in);
            String sendMessage = scanner.next();
            sendMessage(sendMessage);
        }
    }

    private static void sendMessage(String info){
        try {
            while (!socketChannel.finishConnect()) {
            }
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readMessage(){
        try {
            while (!socketChannel.finishConnect()) {
            }
            while (true){
                selector.select();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 10);
                socketChannel.read(byteBuffer);
                if (new String(byteBuffer.array()).trim().length() <= 0) continue;
                System.out.println(new String(byteBuffer.array()).trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

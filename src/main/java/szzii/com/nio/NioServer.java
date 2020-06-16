package szzii.com.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author szz
 */
public class NioServer {

    private static int port = 6666;

    private static Selector selector;

    static ServerSocketChannel serverSocketChannel;

    static {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务已经启动,监听端口:"+port);
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            System.out.println("当前服务器人数:"+ selector.keys().size());
            System.out.println("当前事件数:"+ selector.selectedKeys().size());
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey == null) continue;
                if (selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel socketChannel = server.accept();
                    if (null != socketChannel){
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        String online = socketChannel.getRemoteAddress() + "已上线";
                        System.out.println(online);
                        sendMessage(socketChannel,online);
                    }
                }else if (selectionKey.isReadable()){
                        readMessage(selectionKey);
                }
            }
        }
    }

    public static void readMessage(SelectionKey selectionKey){
        try {
            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            String info = new String(byteBuffer.array()).trim();
            if (info.length() <= 0) return;
            if ("#".equals(info.substring(0,1))) {
                reName(selectionKey,info.substring(1));
                sendMessage(null,selectionKey,getOnlinePeople());
                return;
            }
            String message = (selectionKey.attachment()+": " + info).trim();
            System.out.println(message);
            if (byteBuffer.hasArray()){
                sendMessage(socketChannel,message);
            }
        } catch (IOException e) {
            System.out.println(Optional.ofNullable(selectionKey.attachment()).map(Object::toString).orElse("")+"默默的退出了房间");
            sendMessage((SocketChannel)selectionKey.channel(),Optional.ofNullable(selectionKey.attachment()).map(Object::toString).orElse("")+"默默的退出了房间");
            try {
                selectionKey.channel().close();
            } catch (IOException ioException) {
            }
        }
    }


    public static void sendMessage(SocketChannel socketChannel,String message){
        assert message.length() < 1;
        Set<SelectionKey> selectionKeys = selector.keys();
        for (SelectionKey selectionKey : selectionKeys) {
            SelectableChannel selectableChannel = selectionKey.channel();
        if (selectableChannel instanceof SocketChannel && selectableChannel!=socketChannel){
                SocketChannel channel = (SocketChannel)selectableChannel;
                try {
                    ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                    channel.write(buffer);
                } catch (IOException e) {
                    System.out.println(selectionKey.attachment()+"默默的退出了房间");
                    sendMessage(socketChannel,selectionKey.attachment()+"默默的退出了房间");
                    try {
                        selectionKey.channel().close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }


    public static void sendMessage(SelectionKey sourceChannel,SelectionKey targetChannel,String message){
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        try {
            if (!(targetChannel.channel() instanceof SocketChannel)) return;
            ((SocketChannel)targetChannel.channel()).write(buffer);
        } catch (IOException e) {
            try {
                System.out.println(targetChannel.attachment()+"默默的退出了房间");
                sendMessage(((SocketChannel)targetChannel.channel()),targetChannel.attachment()+"默默的退出了房间");
                ((SocketChannel)targetChannel.channel()).close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }


    public static void reName(SelectionKey selectionKey,String name){
        selectionKey.attach(name);
    }


    public static String getOnlinePeople(){
        int size = selector.keys().size() - 1;
        List<Object> users = selector.keys().stream().filter(s -> {
            return s.channel() instanceof SocketChannel;
        }).map(SelectionKey::attachment).collect(Collectors.toList());
        return "当前服务器在在线人数："+ size +"在线人员:" + users.toString();
    }
}



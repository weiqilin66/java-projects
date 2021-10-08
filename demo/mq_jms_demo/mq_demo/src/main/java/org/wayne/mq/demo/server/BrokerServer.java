package org.wayne.mq.demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: 涉及到服务器,都会涉及 线程(Thread) 套接字(Socket)
 * 一个用于启动服务 一个用于服务端客户端间通讯
 * @author: lwq
 */
public class BrokerServer implements Runnable{

    /**
     * 端口
     */
    public final static int SERVER_PORT = 999;

    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())){

            while (true){
                String str = in.readLine();
                if (str==null) {
                    continue;
                }
                System.out.println("接收到原始数据:"+ str );
                if (str.equalsIgnoreCase("consumer")) {
                    String msg = BrokerQ.consumer();
                    out.println(msg);
                    out.flush();
                }else {
                    BrokerQ.produce(str);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * 其实许多服务器的主线程就是一个死循环, 例如Redis
     */
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(SERVER_PORT);
        System.out.println("MQ处理中心服务启动");
        while (true){
            BrokerServer brokerServer = new BrokerServer(socket.accept());
            new Thread(brokerServer).start();
        }
    }
}

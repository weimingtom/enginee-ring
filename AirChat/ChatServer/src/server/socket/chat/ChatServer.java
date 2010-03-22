/**
 * 
 */
package server.socket.chat;
import java.io.*;
import java.net.*;

/**
 * チャットサーバー
 */
public class ChatServer {
	/**
	 * チャットサーバー起動開始
	 */
    public void start(int port) {
        ServerSocket     server;//サーバソケット
        Socket           socket;//ソケット
        ChatServerThread thread;//スレッド

        try {
            server=new ServerSocket(port);
            System.out.println("チャットサーバ実行開始!!");
            while(true) {
                try {
                    //接続待機
                    socket=server.accept();
                    
                    //チャットサーバスレッド開始
                    thread=new ChatServerThread(socket);
                    thread.start();
                } catch (IOException e) {
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * メイン処理
     */
    public static void main(String[] args) {
        ChatServer server=new ChatServer();
        server.start(49153);
    }

}

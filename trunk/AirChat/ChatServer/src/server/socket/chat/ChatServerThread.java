/**
 * 
 */
package server.socket.chat;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * チャット制御用スレッド
 */
public class ChatServerThread extends Thread {
	// static スレッド：常に、保持されますよ！！
    private static List<ChatServerThread> threads= new ArrayList<ChatServerThread>();//スレッド郡
    private Socket socket;//ソケット

    /**
     * コンストラクタ
     */
    public ChatServerThread(Socket socket) {
        super();
        this.socket=socket;
        // スレッド追加
        threads.add(this);
       	// ユーザ情報を表示する
   		//who();
    }

    /**
     * スレッド開始
     */
    @Override
    public void run() {
        String message;
        int size;
        byte[] w=new byte[10240];
        
        BufferedReader readerIn=null;
        try {
            // 出力ストリームを生成
            readerIn = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF8"));
            while(true) {
                try {
                	// 受信文字
                	message = readerIn.readLine();
                	if (message == null) {
                		throw new IOException();
                	}
                	
                	if(message.trim().equals("WHO")) {
                		who();
                		continue;
                	}
                	if(message.trim().equals("EXIT")) {
                		// 処理終了。オブジェクトはGCにて処理
                		System.out.println("EXIT さようなら");
                		break;
                	}
                    //全員にメッセージ送信
                    sendMessageAll(message);
                } catch (IOException e) {
                    socket.close();
                    threads.remove(this);
                    return;
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
        	threads.remove(this);
        	// ユーザ情報を表示する
       		who();
        }
    }
    
    /**
     * WHOコマンド
     * 現在何人がチャットルームに参加しているか調べるコマンド
     */
    public void who(){
    	int cnt = 0;
        for (int i=0;i<threads.size();i++) {
        	ChatServerThread thread=(ChatServerThread)threads.get(i);
            if (thread.isAlive()) cnt++;
        }
        String msg = "━━ 只今"+cnt+"人 ━━<br>";
        sendMessageAll(msg);
    }

    /**
     * 全員にメッセージ送信
     */
    public void sendMessageAll(String message) {
    	if ( message == null || "".equals(message) ) return;
    	
        ChatServerThread thread;
        for (int i=0;i<threads.size();i++) {
            thread=(ChatServerThread)threads.get(i);
            if (thread.isAlive()) thread.sendMessage(this,message);
        }
        //System.out.println("メッセージ送信="+message);
    }

    /**
     * メッセージ送信
     */
    public void sendMessage(ChatServerThread talker,String message){
        try {
            OutputStream out=socket.getOutputStream();
            byte[] w=message.getBytes("UTF8");
            out.write(w);
            out.flush();
        } catch (IOException e) {
        }
    }

}

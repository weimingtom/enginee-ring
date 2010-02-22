package jp.co.ziro.surpre.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import jp.co.ziro.surpre.util.XmlUtil;

import org.w3c.dom.Document;

/**
 * サービスアクセス
 * @author z001
 */
public class ServiceHelper {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ServiceHelper.class.getName());

    /**
     * 設定する文字コード
     */
    private static final String CHARSET = "UTF-8";

    /**
     * アクセスステータス
     */
    private int status;
    /**
     * 引数用マップ
     */
    private Map<String, String> paramMap = new TreeMap<String,String>();

    /**
     * URLからXMLの取得
     * @param endPoint アクセスするURL
     * @return REST
     */
    public Document getDocument(String endPoint) {
        //logger.info(buffer.toString());
        Document rtnDoc = XmlUtil.getXml(getHttp(endPoint));
        return rtnDoc;
    }
   
    /**
     * HTTPを取得
     * @param endPoint
     * @return
     */
    public String getHttp(String endPoint) {
        
        try {
            URL url = new URL(endPoint);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
    
            InputStream inStream = connection.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inStream,"utf-8"));
            //ステータスを取得
            status = connection.getResponseCode();
            if ( status != HttpURLConnection.HTTP_OK ) {
                return null;
            }
 
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(new String(line)+ "\r\n");
            }
            return buffer.toString();
        } catch ( Exception ex ) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 引数の追加
     * @param aKey
     * @param aValue
     */
    public void addParam(String aKey,String aValue) {
        paramMap.put(aKey,aValue);
    }

    /**
     * URLデータを作成
     * @param sortedParamMap URL用変数マップ
     * @return 接続文字列
     */
    public String canonicalize() {
        if (paramMap.isEmpty()) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        Iterator<Entry<String, String>> iter = paramMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> kvpair = iter.next();
            buffer.append(percentEncodeRfc3986(kvpair.getKey()));
            buffer.append("=");
            buffer.append(percentEncodeRfc3986(kvpair.getValue()));
            if (iter.hasNext()) {
                buffer.append("&");
            }
        }
        String cannoical = buffer.toString();
        return cannoical;
    }

    /**
     * エンコードを行う
     * @param s
     * @return
     */
    protected String percentEncodeRfc3986(String s) {
        String out;
        try {
            out = URLEncoder.encode(s, CHARSET).replace("+", "%20")
                    .replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            out = s;
        }
        return out;
    }
    
    /**
     * HTTPアクセスの状態取得
     * @return
     */
    public int getStatus() {
        return status;
    }
}

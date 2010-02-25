package jp.co.ziro.surpre.helper;

import java.util.logging.Logger;

import org.slim3.util.ApplicationMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * bit.lyアクセス
 * @author z001
 */
public class BitlyServiceHelper extends ServiceHelper {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(PhotozoServiceHelper.class.getName());

    /**
     * APIキー
     */
    private static final String APIKEY = ApplicationMessage.get("bitly.key");
    /**
     * ログインID
     */
    private static final String LOGIN  = "surpre";
    /**
     * バージョン
     */
    private static final String VARSION = "2.0.1";
    /**
     * 取得フォーマット
     */
    private static final String FORMAT = "xml";

    /**
     * コンストラクタ
     */
    public BitlyServiceHelper() {
    }

    @Override
    public String canonicalize() {
        return super.canonicalize();
    }
   
    /**
     * 短いURLを取得
     * @return
     */
    public String getShortUrl(String longUrl) {

        addParam("apiKey", APIKEY);
        addParam("login", LOGIN);
        addParam("version", VARSION);
        addParam("format", FORMAT);
        addParam("longUrl", longUrl);

        String url = "http://api.bit.ly/shorten";
        //URLを作成
        String endPoint = url + "?" + canonicalize();
        //RESTをDocumentで取得する
        Document rtnDoc = getDocument(endPoint);

        //取得したURLを取得
        NodeList nodeList = rtnDoc.getElementsByTagName("shortUrl");
        Element urlElm = (Element)nodeList.item(0);
        
        return urlElm.getTextContent();
    }
}

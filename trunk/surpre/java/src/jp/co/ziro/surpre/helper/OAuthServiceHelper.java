package jp.co.ziro.surpre.helper;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;

/**
 * OAuthアクセス用サービスヘルパー
 * @author z001
 */
public abstract class OAuthServiceHelper extends ServiceHelper {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(OAuthServiceHelper.class.getName());

    /**
     * 文字コード
     */
    private static final String CHARSET = "UTF-8";
   
    /**
     * 暗号化用キー
     */
    private SecretKeySpec secretKeySpec = null;
    /**
     * 暗号化オブジェクト
     */
    private Mac mac = null;
   
    /**
     * キー作成用のGETメソッド名
     */
    protected static final String REQUEST_METHOD = "GET";
    
    /**
     * リクエストURL
     */
    protected String requestURI = "";

    /**
     * コンストラクタ
     */
    public OAuthServiceHelper(String secret,String hmac) {
        try {
            byte[] secretyKeyBytes = secret.getBytes(CHARSET);
            secretKeySpec = new SecretKeySpec(secretyKeyBytes,hmac);
            mac = Mac.getInstance(hmac);
            mac.init(secretKeySpec);
        } catch (Exception e) {
            throw new RuntimeException("コンストラクタでの例外",e);
        }
    }

    /**
     * アクセスする
     * @param uri
     */
    public void setURI(String uri) {
        requestURI = uri;
    }

    /**
     * 認証を行うURLを作成
     * @param stringToSign
     * @return
     */
    protected String hmac(String stringToSign) {
        String signature = null;
        byte[] data;
        byte[] rawHmac;
        try {
            data = stringToSign.getBytes(CHARSET);
            rawHmac = mac.doFinal(data);
            Base64 encoder = new Base64();
            signature = new String(encoder.encode(rawHmac));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(CHARSET + " is unsupported!", e);
        }
        return signature;
    }

    /**
     * URL作成用のメソッド
     * @param params
     * @return
     */
    public abstract String sign();

    /**
     * 作成したURLからRESTデータとしてDocumentを作成する
     * @param map リクエストマップ
     * @return 作成されたXML
     */
    public Document getREST() {
        //URLを作成
        String request = sign();
        Document rtnDoc = getDocument(request);
        return rtnDoc;
    }
}

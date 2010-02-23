package jp.co.ziro.surpre.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class OAuthTestServiceHelper extends OAuthServiceHelper {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(OAuthTestServiceHelper.class.getName());
 
    private static final String KEY = "dpf43f3p2l4k3l03";
    private static final String SECRET = "kd94hf93k423kf44";
    private static final String endpoint = "provider.example.net"; 
    private static final String HMAC = "HmacSHA1";
    private static final String REQUEST_URI = "/profile";
    private static final String NONCE = "kllo9940pd9333jh";
    
    /**
     * コンストラクタ
     */
    public OAuthTestServiceHelper() {
        super(SECRET + "&",HMAC);
    }
    /**
     * URLの作成
     * @param params リクエストマップ
     * @return
     */
    public String sign() {

        //基本的な引数をすべて設定
        addParam("oauth_consumer_key", KEY);
        addParam("oauth_signature_method", "HMAC-SHA1");
        addParam("oauth_timestamp", "1191242096");
        addParam("oauth_version", "1.0");
        addParam("oauth_nonce", NONCE);

        String canonicalQS = canonicalize();

        String method = REQUEST_METHOD;
        String http   ="http://" + endpoint + REQUEST_URI ;
        String args   = canonicalQS;

        String toSign;
        try {
            toSign = URLEncoder.encode(method,"UTF-8")+"&"+
                            URLEncoder.encode(http,"UTF-8")+"&"+
            				URLEncoder.encode(args,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
 
        String hmac = hmac(toSign);
        hmac = hmac.substring(0, hmac.length()-2);
        
        return hmac;
    }
    
}

package jp.co.ziro.surpre.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import jp.co.ziro.surpre.dto.MixiDto;
import jp.co.ziro.surpre.util.XmlUtil;

import org.slim3.util.ApplicationMessage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * mixiアクセス用のヘルパー
 * @author z001
 */
public class MixiServiceHelper extends OAuthServiceHelper {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(MixiServiceHelper.class.getName());
   
    /**
     * アプリケーションキー
     */
    private static final String KEY      = ApplicationMessage.get("mixi.key");
    /**
     * アプリケーションシークレットキー
     */
    private static final String SECRET   = ApplicationMessage.get("mixi.secret");
    /**
     * エンドポイント
     */
    private static final String endpoint = "api.mixi-platform.com/os/0.8"; 

    /**
     * 暗号化キー
     */
    private static final String HMAC     = "HmacSHA1";


    /**
     * コンストラクタ
     */
    public MixiServiceHelper() {
        super(SECRET + "&",HMAC);
    }

    /**
     * URLの作成
     * @param params リクエストマップ
     * @return
     */
    @Override
    public String sign() {

        Calendar cal = Calendar.getInstance();
        String nonce = UUID.randomUUID().toString();
        nonce = nonce.replaceAll("-", "");

        //基本的な引数をすべて設定
        addParam("oauth_consumer_key", KEY);
        addParam("oauth_signature_method", "HMAC-SHA1");
        addParam("oauth_timestamp", String.valueOf(cal.getTimeInMillis()).substring(0,10));
        addParam("oauth_version", "1.0");
        addParam("oauth_nonce", nonce);

        String canonicalQS = canonicalize();
 
        String method = REQUEST_METHOD;
        String http   ="http://" + endpoint + requestURI ;
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
        hmac = hmac.substring(0, hmac.length() - 2);
       
        addParam("oauth_signature", hmac);
        canonicalQS = canonicalize();
        
        String url = "http://" + endpoint + requestURI + "?" + canonicalQS;
        return url;
    }
   
    /**
     * 友達の一覧取得
     * @param id
     * @return
     */
    public List<MixiDto> createFriendList(String id,boolean appFlag) {

        //URI設定
        setURI("/people/" + id + "/@friends");
        //アプリを選定する場合
        if ( appFlag ) {
            addParam("filterBy", "hasApp");
        }
        //IDを設定
        addParam("xoauth_requestor_id", id);
        //XMLを指定
        addParam("format", "atom");

        Document doc = getREST();
        if ( doc == null ) {
            return null;
        }
        return createFriendList(doc);
    }
    
    /**
     * 
     * @param doc
     * @return
     */
    public List<MixiDto> createFriendList(Document doc) {
        List<MixiDto> friendList = new ArrayList<MixiDto>();
        
        NodeList idList = doc.getElementsByTagName("id");
        NodeList nameList = doc.getElementsByTagName("displayName");
        NodeList nickNameList = doc.getElementsByTagName("nickname");
        NodeList thumbnailList = doc.getElementsByTagName("thumbnailUrl");
        
        //データ数回繰り返す
        for ( int cnt = 0; cnt < idList.getLength(); ++cnt ) {

            MixiDto dto = new MixiDto();

            String userId    = XmlUtil.getText(idList.item(cnt));
            String name      = XmlUtil.getText(nameList.item(cnt));
            String nickname  = XmlUtil.getText(nickNameList.item(cnt));
            String thumbnail = XmlUtil.getText(thumbnailList.item(cnt));

            //無駄をけずる
            dto.setId(userId.replaceAll("urn:guid:mixi.jp:", ""));
           
            if ( name != null && !name.equals("") ){
                dto.setName(name);
                dto.setNickName(nickname);
                dto.setThumbnailUrl(thumbnail);
                //リストに追加
                friendList.add(dto);
            }
        }
        return friendList;
    }
}

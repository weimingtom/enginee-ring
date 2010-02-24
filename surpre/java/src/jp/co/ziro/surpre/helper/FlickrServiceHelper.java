package jp.co.ziro.surpre.helper;

import java.util.UUID;
import java.util.logging.Logger;

import org.slim3.controller.upload.FileItem;
import org.slim3.util.ApplicationMessage;

import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.uploader.UploadMetaData;
import com.aetrion.flickr.uploader.Uploader;

/**
 * Flickrのサービスヘルパー
 * @author z001
 */
public class FlickrServiceHelper extends ServiceHelper {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(FlickrServiceHelper.class.getName());
    
    private static final String KEY = ApplicationMessage.get("flickr.key");
    private static final String SECRET = ApplicationMessage.get("flickr.secret");

    /**
     * コンストラクタ
     */
    public FlickrServiceHelper() {
    }

    /**
     * 認証情報を取得
     * @return
     */
    public AuthInterface getAuthInterface() {
        try {
            Flickr.debugStream = false;
            Flickr flickr = new Flickr( KEY, SECRET, new REST() );
            return flickr.getAuthInterface();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * アップロードを行う
     * @param uploadFile
     * @param nsid 
     * @resutn 画像URL
     */
    public String upload(FileItem uploadFile, String nsid) {

        //ユーザからトークンを取得
        SurpreService service = new SurpreService();
        SurpreData data = service.findByNsid(nsid);

        RequestContext requestContext = RequestContext.getRequestContext();

        Auth auth = new Auth();
        auth.setPermission(Permission.WRITE);
        auth.setToken(data.getFlickrToken());

        requestContext.setAuth(auth);
        Flickr.debugRequest = false;
        Flickr.debugStream = false;
       
        Uploader uploader = new Uploader(KEY,SECRET);
        UploadMetaData meta = new UploadMetaData();

        String nonce = UUID.randomUUID().toString();
        nonce = nonce.replaceAll("-", "");
 
        //タイトルの設定
        meta.setTitle(uploadFile.getFileName());
        meta.setPublicFlag(true);
        String rtn = "";
        try {
            //アップロード。IDを取得
            rtn = uploader.upload(uploadFile.getData(), meta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        rtn = "http://www.flickr.com/photos/" + nsid + "/" + rtn + "/";
        return rtn;
    }

}

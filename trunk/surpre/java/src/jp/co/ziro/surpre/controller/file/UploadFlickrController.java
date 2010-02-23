package jp.co.ziro.surpre.controller.file;


import java.util.logging.Logger;

import jp.co.ziro.surpre.helper.FlickrServiceHelper;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

/**
 * フリッカーのアップロード処理
 * @author z001
 */
public class UploadFlickrController extends Controller {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(UploadFlickrController.class.getName());
    
    @Override
    public Navigation run() {
        
        //ログイン情報は取れない

        String nsid = requestScope("nsid");
        //ファイルデータを取得
        FileItem file = requestScope("uploadFile");

        FlickrServiceHelper helper = new FlickrServiceHelper();
        String url = helper.upload(file,nsid);

        requestScope("url",url);
        return forward("../rest/url.jsp");
    }
}

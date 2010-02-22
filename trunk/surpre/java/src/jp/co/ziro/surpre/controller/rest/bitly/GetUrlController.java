package jp.co.ziro.surpre.controller.rest.bitly;

import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.helper.BitlyServiceHelper;

import org.slim3.controller.Navigation;

/**
 * URL短縮
 * @author z001
 */
public class GetUrlController extends RestController {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetUrlController.class.getName());

    @Override
    public Navigation createRestData() {
        
        String longUrl = requestScope("url");
        
        BitlyServiceHelper helper = new BitlyServiceHelper();
        //URLとしてリクエストに設定
        requestScope("url",helper.getShortUrl(longUrl));

        return forward("../url.jsp");
    }
}

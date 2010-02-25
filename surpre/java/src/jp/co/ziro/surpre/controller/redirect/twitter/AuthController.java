package jp.co.ziro.surpre.controller.redirect.twitter;

import java.util.logging.Logger;

import jp.co.ziro.surpre.helper.TwitterServiceHelper;
import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * 登録へ移動
 * @author z001
 *
 */
public class AuthController extends Controller {

    //@SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    
    @Override
    public Navigation run() {
        
        SurpreService service = new SurpreService();
        //ユーザでトークンを検索
        SurpreData data = service.find();
        //登録済みの場合
        if ( service.isRegistTwitter(data) ) {
            logger.warning("すでにユーザ登録済");
            return redirect("../../home");
        }

        //取得する
        Twitter twitter = TwitterServiceHelper.getTwitter();
        try {
        	RequestToken requestToken = twitter.getOAuthRequestToken();
            String url = requestToken.getAuthorizationURL();
            sessionScope("requestToken",requestToken);

            return redirect(url);
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
    }

}

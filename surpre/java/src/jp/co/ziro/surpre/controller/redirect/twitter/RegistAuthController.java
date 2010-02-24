package jp.co.ziro.surpre.controller.redirect.twitter;

import java.util.logging.Logger;

import jp.co.ziro.surpre.helper.TwitterServiceHelper;
import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * Twitter登録コントローラー
 * @author z001
 */
public class RegistAuthController extends Controller {

    //@SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(RegistAuthController.class.getName());

    @Override
    public Navigation run() {
        
        String redirectBuf = "../../home?select=twitter";

        SurpreService service = new SurpreService();
        SurpreData model = service.find();

        if ( service.isRegistTwitter(model) ) {
           logger.warning("すでにユーザ登録済");
           return redirect(redirectBuf);
        }

        String denied = requestScope("denied");
        if ( denied != null && !denied.equals("") ) {
            return redirect(redirectBuf);
        }

        Twitter twitter = TwitterServiceHelper.getTwitter();

        RequestToken requestToken = sessionScope("requestToken");
        sessionScope("requestToken",null);

        AccessToken accessToken = null;
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken);
        } catch (TwitterException e) {
            logger.warning(e.getMessage());
            throw new RuntimeException(e);
        }
        String token = accessToken.getToken();
        String tokenSecret = accessToken.getTokenSecret();
 
        //登録を行う
        if ( model == null ) {
            model = new SurpreData();
            model.setKey(Datastore.allocateId(SurpreData.class));
        }

        model.setTwitterToken(token);
        model.setTwitterTokenSecret(tokenSecret);

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        model.setUser(user);

        Datastore.put(model);

        return redirect(redirectBuf);
    }
}

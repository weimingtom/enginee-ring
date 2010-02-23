package jp.co.ziro.surpre.helper;

import java.util.logging.Logger;

import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

public class TwitterServiceHelper {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(TwitterServiceHelper.class.getName());

    private static final String KEY = "DVBdI8QMgIdi5lTmhtC91g";
    private static final String KEY_SECRET = "0nhZiof2GOhdobaQ05BwHNlZuuPp3n3Wh8i2zsNtIg";

    public static Twitter getTwitter() {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(KEY, KEY_SECRET);
        return twitter;
    }

    /**
     * 
     * @return
     */
    public static Twitter getAuthTwitter() {

        Twitter twitter = getTwitter();

        SurpreService service = new SurpreService();
        //検索を行う
        SurpreData data = service.find();

        AccessToken accessToken = new AccessToken(data.getTwitterToken(),data.getTwitterTokenSecret());
        twitter.setOAuthAccessToken(accessToken);

        return twitter;
    }
}

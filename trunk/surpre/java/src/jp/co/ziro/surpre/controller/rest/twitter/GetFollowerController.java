package jp.co.ziro.surpre.controller.rest.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.dto.TimelineDto;
import jp.co.ziro.surpre.helper.TwitterServiceHelper;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * 友人を取得
 * @author z001
 */
public class GetFollowerController extends Controller {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetFollowerController.class.getName());

    @Override
    public Navigation run() {

        //認証済のデータを取得
        Twitter twitter = TwitterServiceHelper.getAuthTwitter(); 

        List<TimelineDto> timelineList = new ArrayList<TimelineDto>();
        try {
            PagableResponseList<User> users = twitter.getFriendsStatuses();
            for ( User user : users ) {
                TimelineDto timeline = new TimelineDto(user);
                timelineList.add(timeline);
            }
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        requestScope("timelineList",timelineList);
        return forward("timeline.jsp");
    }
}

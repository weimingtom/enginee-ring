package jp.co.ziro.surpre.controller.rest.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.TimelineDto;
import jp.co.ziro.surpre.helper.TwitterServiceHelper;

import org.slim3.controller.Navigation;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class GetFriendController extends RestController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetFollowerController.class.getName());

    @Override
    public Navigation createRestData() {

        String screenName = requestScope("screenName");
        //認証済のデータを取得
        Twitter twitter = TwitterServiceHelper.getAuthTwitter(); 

        List<TimelineDto> timelineList = new ArrayList<TimelineDto>();
        try {
            ResponseList<Status> statues = twitter.getUserTimeline(screenName);
            for ( Status status : statues ) {
                TimelineDto timeline = new TimelineDto(status);
                timelineList.add(timeline);
            }
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        requestScope("timelineList",timelineList);
        return forward("timeline.jsp");
    }
}

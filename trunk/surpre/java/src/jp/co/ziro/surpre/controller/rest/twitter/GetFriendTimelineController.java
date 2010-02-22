package jp.co.ziro.surpre.controller.rest.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.TimelineDto;
import jp.co.ziro.surpre.helper.TwitterServiceHelper;

import org.slim3.controller.Navigation;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class GetFriendTimelineController extends RestController {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetFriendTimelineController.class.getName());

    @Override
    public Navigation createRestData() {
       
        //タイムラインの取得を行う
        //認証済のデータを取得
        Twitter twitter = TwitterServiceHelper.getAuthTwitter();
       
        Paging paging = new Paging();
        paging.setCount(9);

        ResponseList<Status> statusList = null;
        //友人のタイムラインを取得
        try {
            statusList = twitter.getFriendsTimeline(paging);
        } catch (TwitterException e) {
            throw new RuntimeException();
        }
       
        List<TimelineDto> timelineList = new ArrayList<TimelineDto>();
        for ( Status status : statusList ) {
            TimelineDto timeline = new TimelineDto(status);
            timelineList.add(timeline);
        }
        requestScope("timelineList",timelineList);

        return forward("timeline.jsp");
    }
}

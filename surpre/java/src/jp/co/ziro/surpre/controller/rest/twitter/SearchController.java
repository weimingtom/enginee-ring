package jp.co.ziro.surpre.controller.rest.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.dto.TimelineDto;
import jp.co.ziro.surpre.helper.TwitterServiceHelper;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SearchController extends Controller {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchController.class.getName());
    
    @Override
    public Navigation run() {
        String keyword = requestScope("keyword");
        //認証済のデータを取得
        //Twitter twitter = TwitterServiceHelper.getAuthTwitter();
        Twitter twitter = TwitterServiceHelper.getTwitter();
        
        Query query = new Query(keyword);
        try {
            QueryResult result = twitter.search(query);

            List<Tweet> tweets = result.getTweets();
            List<TimelineDto> timelineList = new ArrayList<TimelineDto>();
            for ( Tweet tweet : tweets ) {
                TimelineDto timeline = new TimelineDto(tweet);
                timelineList.add(timeline);
            }
            requestScope("timelineList",timelineList);
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        return forward("timeline.jsp");
    }
}

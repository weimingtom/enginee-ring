package jp.co.ziro.surpre.controller.rest.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.helper.AmazonServiceHelper;
import jp.co.ziro.surpre.helper.TwitterServiceHelper;
import jp.co.ziro.surpre.helper.YahooServiceHelper;

import org.slim3.controller.Navigation;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;

import twitter4j.Twitter;

public class TweetController extends RestController {
    
    //@SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(TweetController.class.getName());

    @Override
    public Navigation createRestData() {
        String tweet = requestScope("tweet");
        //認証済のデータを取得
        Twitter twitter = TwitterServiceHelper.getAuthTwitter();
        //文字列を送り込む
        try {
            //つぶやく
            twitter.updateStatus(tweet);

            List<ItemDto> itemList = new ArrayList<ItemDto>();
            //空のオブジェクトを設定
            itemList.add(new ItemDto());
            itemList.add(new ItemDto());
            itemList.add(new ItemDto());
            itemList.add(new ItemDto());

            YahooServiceHelper langHelper = new YahooServiceHelper();
            //キーワードを取得
            String keyword = langHelper.getSurface(tweet);

            List<ItemDto> searchList = null;
            //キーワードがなかった場合
            if ( !StringUtil.isEmpty(keyword) ) {
                logger.info(keyword);
                //その名詞をアマゾンで検索
            	AmazonServiceHelper amazonHelper = new AmazonServiceHelper();
            	searchList = amazonHelper.searchKeyword("All",keyword);
            }

            //検索結果を取得
            ItemDto addDto = null;
            if ( searchList == null || searchList.size() <= 0 ) {
                addDto = new ItemDto();
            } else {
                int leng = searchList.size();
                Random rnd = new Random();
                int ran = rnd.nextInt(leng); 
                addDto = searchList.get(ran);
            }

            //実際のデータを設定
            itemList.add(addDto);
            //IDのリストを設定
            requestScope("itemList",itemList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return forward("../itemList.jsp");
    }

}

package jp.co.ziro.surpre.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import jp.co.ziro.surpre.util.HtmlUtil;


import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.User;

public class TimelineDto {
    
    private String userId;
    private String userName;
    private String date;
    private String image;
    private String text;
    
    /**
     * 生成する
     * @param status
     */
    public TimelineDto(Status status) {
        userId =  status.getUser().getScreenName();
        userName = status.getUser().getName();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(status.getCreatedAt());
        //TODO 日本時間を追加
        cal.add(Calendar.HOUR_OF_DAY, 9);
        date = sdf.format(cal.getTime());
        image =  status.getUser().getProfileImageURL().toExternalForm();
        text = HtmlUtil.autoLink(status.getText());
    }
    /**
     * 生成する
     * @param status
     */
    public TimelineDto(User user) {
        userId  =  user.getScreenName();
        userName = user.getName();
 
        if ( user.getStatusCreatedAt() != null ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(user.getStatusCreatedAt());
        	//TODO 日本時間を追加
        	cal.add(Calendar.HOUR_OF_DAY, 9);
        	date = sdf.format(cal.getTime());
        } else {
           date = ""; 
        }
        image =  user.getProfileImageURL().toExternalForm();
        text = HtmlUtil.autoLink(user.getStatusText());
    }
    public TimelineDto(Tweet tweet) {
        userId  =  tweet.getFromUser();
        userName = "userName";
 
        if ( tweet.getCreatedAt() != null ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.setTime(tweet.getCreatedAt());
            //TODO 日本時間を追加
            cal.add(Calendar.HOUR_OF_DAY, 9);
            date = sdf.format(cal.getTime());
        } else {
           date = ""; 
        }
        image = tweet.getProfileImageUrl();
        text = HtmlUtil.autoLink(tweet.getText());
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}

package jp.co.ziro.surpre.service;

import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;

import jp.co.ziro.surpre.meta.SurpreDataMeta;
import jp.co.ziro.surpre.model.SurpreData;

/**
 * サプレデータアクセス
 * @author z001
 */
public class SurpreService extends Service<SurpreData> {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SurpreService.class.getName());

    private static SurpreDataMeta meta = new SurpreDataMeta();
   
    /**
     * ユーザのデータを検索
     * @return
     */
    public SurpreData find() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();   
        return Datastore.query(SurpreData.class).filter(meta.user.equal(user)).asSingle();
    }

    /**
     * mixiから取得
     * @param nsid
     * @return
     */
    public SurpreData findByMixiId(String mixiId) {
        return Datastore.query(SurpreData.class).filter(meta.mixiId.equal(mixiId)).asSingle();
    }

    /**
     * nsidから取得
     * @param nsid
     * @return
     */
    public SurpreData findByNsid(String nsid) {
        return Datastore.query(SurpreData.class).filter(meta.flickrNsid.equal(nsid)).asSingle();
    }
   
    /**
     * Twitterの認証を終えているか？
     * @param model データモデル
     * @return true 登録済
     */
    public boolean isRegistTwitter(SurpreData model) {
        if ( model == null ) return false;
        if ( StringUtil.isEmpty(model.getTwitterToken()) ) return false;
        return true;
    }

    /**
     * Flickrの認証を終えているか？
     * @param model データモデル
     * @return true 登録済
     */
    public boolean isRegistFlickr(SurpreData model) {
        if ( model == null ) return false;
        if ( StringUtil.isEmpty(model.getFlickrToken()) ) return false;
        return true;
    }

    /**
     * Mixiの認証を終えているか？
     * @param model データモデル
     * @return true 登録済
     */
    public boolean isRegistMixi(SurpreData model) {
        if ( model == null ) return false;
        if ( StringUtil.isEmpty(model.getMixiId()) ) return false;
        return true;
    }

    /**
     * Amazonの認証を終えているか？
     * @param model データモデル
     * @return true 登録済
     */
    public boolean isRegistWishListId(SurpreData model) {
        if ( model == null ) return false;
        if ( StringUtil.isEmpty(model.getWishListId()) ) return false;
        return true;
    }
}

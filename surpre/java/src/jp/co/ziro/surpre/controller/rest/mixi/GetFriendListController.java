package jp.co.ziro.surpre.controller.rest.mixi;

import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.dto.MixiDto;
import jp.co.ziro.surpre.helper.MixiServiceHelper;
import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * mixi友人検索
 * @author z001
 */
public class GetFriendListController extends Controller {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetFriendListController.class.getName());

    @Override
    public Navigation run() {

        SurpreService service = new SurpreService();
        //ユーザでトークンを検索
        SurpreData data = service.find();
 
        MixiServiceHelper helper = new MixiServiceHelper();
        List<MixiDto> friendList = helper.createFriendList(data.getMixiId(),true);
        
        if ( friendList == null ) {
            requestScope("status",helper.getStatus());
        } else {
            requestScope("friendList",friendList);
        }
        return forward("friendList.jsp");
    }
}

package jp.co.ziro.surpre.controller.rest.amazon;

import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.helper.AmazonServiceHelper;
import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class GetWishItemController extends Controller {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetWishItemController.class.getName());

    @Override
    public Navigation run() {

        //ウィッシュリスト検索
        String mixiId = requestScope("mixiId");
 
        SurpreService service = new SurpreService();
        SurpreData model = service.findByMixiId(mixiId);
        //そのmixiIdのユーザが存在しない場合
        if ( model == null ) {
            return forward("../itemList.jsp");
        }
        
        //ウィッシュID取得
        if ( !service.isRegistWishListId(model) ) {
            //TODO 存在しなかった場合
            return forward("../itemList.jsp");
        }

        //ウィッシュリストの１件検索
        AmazonServiceHelper helper = new AmazonServiceHelper();
        List<ItemDto> itemList = helper.searchWishList(model.getWishListId());
        
        //IDのリストを設定
        requestScope("itemList",itemList);
        
        return forward("../itemList.jsp");
    }
}

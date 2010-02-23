package jp.co.ziro.surpre.controller.rest.amazon;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Navigation;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.helper.AmazonServiceHelper;
import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;

/**
 * ほしいものリスト取得
 * @author z001
 */
public class SearchWishListController extends RestController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchWishListController.class.getName());

    @Override
    protected Navigation createRestData() {

        //ウィッシュリスト検索
        String listId = requestScope("listId");
        if ( StringUtil.isEmpty(listId) ) {
            //一旦検索する
            SurpreService service = new SurpreService();
            SurpreData model = service.find();
            if ( model != null && model.getWishListId() != null ) {
                listId = model.getWishListId();
            }
        }

        //ウィッシュリストの１件検索
        AmazonServiceHelper helper = new AmazonServiceHelper();
        List<ItemDto> itemList = helper.searchWishList(listId);
        //IDのリストを設定
        requestScope("itemList",itemList);
        
        return forward("../itemList.jsp");
    }
}

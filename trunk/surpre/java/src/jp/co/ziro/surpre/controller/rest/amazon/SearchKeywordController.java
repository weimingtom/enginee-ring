package jp.co.ziro.surpre.controller.rest.amazon;

import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.helper.AmazonServiceHelper;

import org.slim3.controller.Navigation;

/**
 * キーワード検索
 * @author z001
 */
public class SearchKeywordController extends RestController {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchWishListController.class.getName());
    
    @Override
    protected Navigation createRestData() {

        String keyword = requestScope("keyword");
        String searchIndex = requestScope("searchIndex");

        AmazonServiceHelper helper = new AmazonServiceHelper();
        List<ItemDto> itemList = helper.searchKeyword(searchIndex,keyword);
        //IDのリストを設定
        requestScope("itemList",itemList);
        
        return forward("../itemList.jsp");
    }
}

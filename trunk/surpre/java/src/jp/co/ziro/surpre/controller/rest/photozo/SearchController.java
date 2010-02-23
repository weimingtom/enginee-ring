package jp.co.ziro.surpre.controller.rest.photozo;

import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.helper.PhotozoServiceHelper;

import org.slim3.controller.Navigation;

/**
 * フォト蔵取得
 * @author z001
 */
public class SearchController extends RestController {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchController.class.getName());
    
    @Override
    public Navigation createRestData() {
        
        String keyword = requestScope("keyword");

        PhotozoServiceHelper helper = new PhotozoServiceHelper();
        List<ItemDto> itemList = helper.searchKeyword(keyword);
        //IDのリストを設定
        requestScope("itemList",itemList);

        return forward("../itemList.jsp");
    }
}

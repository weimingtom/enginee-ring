package jp.co.ziro.surpre.controller.rest.yahoo;

import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.helper.YahooServiceHelper;

import org.slim3.controller.Navigation;

public class SearchController extends RestController {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchController.class.getName());

    @Override
    public Navigation createRestData() {
       
        String keyword = requestScope("keyword");

        YahooServiceHelper helper = new YahooServiceHelper();
        List<ItemDto> itemList = helper.searchKeyword(keyword);
        //IDのリストを設定
        requestScope("itemList",itemList); 
        
        return forward("../itemList.jsp");
    }
}

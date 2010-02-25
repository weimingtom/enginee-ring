package jp.co.ziro.surpre.controller.rest.youtube;

import java.util.List;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.helper.YoutubeServiceHelper;

import org.slim3.controller.Navigation;

public class SearchController extends RestController {

    @Override
    public Navigation createRestData() {
        String keyword = requestScope("keyword");

        YoutubeServiceHelper helper = new YoutubeServiceHelper();
        List<ItemDto> itemList = helper.searchKeyword(keyword);
        //IDのリストを設定
        requestScope("itemList",itemList);

        return forward("../itemList.jsp");
    }
}

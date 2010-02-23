package jp.co.ziro.surpre.controller.rest.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jp.co.ziro.surpre.controller.rest.RestController;
import jp.co.ziro.surpre.dto.ListIdDto;
import jp.co.ziro.surpre.helper.AmazonServiceHelper;

import org.slim3.controller.Navigation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 欲しいものリストのIDリスト作成
 * @author z001
 */
public class SearchWishListIdController extends RestController {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchWishListIdController.class.getName());

    @Override
    protected Navigation createRestData() {

        
        String mail = requestScope("mail");
        //メールアドレスを変更
        mail.replaceAll("@", "40");
 
        AmazonServiceHelper helper = new AmazonServiceHelper();

        //ウィッシュリスト検索
        //TODO 関数化対象
        helper.addParam("Operation", "ListSearch");
        helper.addParam("ListType", "WishList");
        helper.addParam("Email", mail);

        //AmazonでWishListを検索
        Document xml = helper.getREST();

        NodeList nodeList = xml.getElementsByTagName("List");
        
        List<ListIdDto> listList = new ArrayList<ListIdDto>();
        //IDのリスト数回繰り返す
        for ( int cnt = 0; cnt < nodeList.getLength(); ++cnt ) {
            Element node = (Element)nodeList.item(cnt);
            NodeList listId = node.getElementsByTagName("ListId");
            NodeList listName = node.getElementsByTagName("ListName");
            
            ListIdDto dto = new ListIdDto();
            //ウィッシュリストのIDを設定
            dto.setId(listId.item(0).getTextContent());
            //名称を設定
            dto.setName(listName.item(0).getTextContent());
            listList.add(dto);
        }
 
        //IDのリストを設定
        requestScope("wishIdList",listList);
        
        return forward("searchWishList.jsp");
    }
}

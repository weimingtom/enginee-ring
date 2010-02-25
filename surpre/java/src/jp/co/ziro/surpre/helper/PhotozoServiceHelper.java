package jp.co.ziro.surpre.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.util.XmlUtil;

/**
 * アマゾンアクセス用のヘルパー
 * @author Administrator
 */
public class PhotozoServiceHelper extends ServiceHelper {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(PhotozoServiceHelper.class.getName());

    /**
     * コンストラクタ
     */
    public PhotozoServiceHelper() {
    }
   
    /**
     * キーワード検索
     * @param keyword 検索ワード
     * @return アイテムリスト
     */
    public List<ItemDto> searchKeyword(String keyword) {

        String endPoint = "http://api.photozou.jp/rest/search_public";
        addParam("keyword", keyword);
        addParam("limit", "10");
        endPoint += "?" + canonicalize();
        //RESTをDocumentで取得する
        Document rtnDoc = getDocument(endPoint);
        List<ItemDto> itemList = new ArrayList<ItemDto>();
        NodeList photoList = rtnDoc.getElementsByTagName("photo");
            
        //写真数回繰り返す
        for ( int pcnt = 0 ; pcnt < photoList.getLength(); ++pcnt ) {
            ItemDto dto = new ItemDto();
            //アイテムを取得
            Element photo = (Element)photoList.item(pcnt);

            NodeList name = photo.getElementsByTagName("photo_title");
            NodeList link = photo.getElementsByTagName("url");
            NodeList imageUrl = photo.getElementsByTagName("image_url");

            dto.setName(XmlUtil.escape(name.item(0).getTextContent()));
            dto.setLink(link.item(0).getTextContent());
            dto.setImageUrl(imageUrl.item(0).getTextContent());
            itemList.add(dto);
        }
        return itemList;
    }
}

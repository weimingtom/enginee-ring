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
public class YoutubeServiceHelper extends ServiceHelper {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(YoutubeServiceHelper.class.getName());

    /**
     * コンストラクタ
     */
    public YoutubeServiceHelper() {
    }
   
    /**
     * キーワード検索
     * @param keyword 検索ワード
     * @return アイテムリスト
     */
    public List<ItemDto> searchKeyword(String keyword) {

        String endPoint = "http://gdata.youtube.com/feeds/api/videos";
        addParam("vq", keyword);
        addParam("max-results", "10");
        endPoint += "?" + canonicalize();

        //RESTをDocumentで取得する
        Document rtnDoc = getDocument(endPoint);

        List<ItemDto> itemList = new ArrayList<ItemDto>();
        NodeList videoList = rtnDoc.getElementsByTagName("entry");
            
        //写真数回繰り返す
        for ( int pcnt = 0 ; pcnt < videoList.getLength(); ++pcnt ) {
            ItemDto dto = new ItemDto();
            //アイテムを取得
            Element movie = (Element)videoList.item(pcnt);

            NodeList name = movie.getElementsByTagName("title");
            NodeList detail = movie.getElementsByTagName("id");
            NodeList link     = movie.getElementsByTagName("media:player");
            NodeList imageUrl = movie.getElementsByTagName("media:thumbnail");

            dto.setName(XmlUtil.escape(name.item(0).getTextContent()));
            dto.setDetail(detail.item(0).getTextContent().replaceAll("http://gdata.youtube.com/feeds/api/videos/",""));
            dto.setLink(XmlUtil.getAttribute(link,"url"));
            dto.setImageUrl(XmlUtil.getAttribute(imageUrl,"url"));
            itemList.add(dto);
        }
        return itemList;
    }
}

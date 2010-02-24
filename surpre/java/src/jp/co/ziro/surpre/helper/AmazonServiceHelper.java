package jp.co.ziro.surpre.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.slim3.util.ApplicationMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.util.XmlUtil;

/**
 * アマゾンアクセス用のヘルパー
 * @author Administrator
 */
public class AmazonServiceHelper extends OAuthServiceHelper {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AmazonServiceHelper.class.getName());

    /**
     * 暗号化方式
     */
    private static final String HMAC = "HmacSHA256";

    /**
     * URL
     */
    private static final String endpoint = "webservices.amazon.co.jp";

    /**
     * キー
     */
    private static final String KEY = ApplicationMessage.get("amazon.key");
    /**
     * シークレット
     */
    private static final String SECRET = ApplicationMessage.get("amazon.secret");
    /**
     * URI
     */
    private static final String REQUEST_URI = "/onca/xml";

    /**
     * コンストラクタ
     */
    public AmazonServiceHelper() {
        super(SECRET,HMAC);
    }

    /**
     * URLの作成
     * @param params リクエストマップ
     * @return
     */
    @Override
    public String sign() {

        //基本的な引数をすべて設定
        addParam("AWSAccessKeyId", KEY);
        addParam("Timestamp", timestamp());
        addParam("Service", "AWSECommerceService");
        addParam("AssociateTag", "surpre-22");
        addParam("Version", "2009-10-01");

        //シグネーチャ作成
        String canonicalQS = canonicalize();
        String toSign = REQUEST_METHOD + "\n" + 
                        endpoint + "\n" + 
                        REQUEST_URI + "\n" + 
                        canonicalQS;

        String hmac = hmac(toSign);
        hmac = hmac.substring(0, hmac.length() - 2);

        addParam("Signature", hmac);
        canonicalQS = canonicalize();
        
        String url = "http://" + endpoint + REQUEST_URI + "?" + canonicalQS ;
        return url;
    }
    /**
     * タイムスタンプを作成
     * @return タイムスタンプ文字列
     */
    protected String timestamp() {
        String timestamp = null;
        Calendar cal = Calendar.getInstance();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
        timestamp = dfm.format(cal.getTime());
        return timestamp;
    }
   

    /**
     * キーワード検索
     * @param index
     * @param keyword
     * @return
     */
    public List<ItemDto> searchKeyword(String index,String keyword) {

        addParam("Operation", "ItemSearch");
        addParam("SearchIndex", index);
        addParam("ResponseGroup","Medium");
        addParam("Keywords", keyword);

        //Amazonで検索
        Document xml = getREST();
        return createItemList(xml);
    }

    /**
     * ウィッシュリストの検索
     * @param listId
     * @return
     */
    public List<ItemDto> searchWishList(String listId) {
        //Amazonで検索
        addParam("Operation", "ListLookup");
        addParam("ListType", "WishList");
        addParam("ResponseGroup","ListInfo,Medium");
        addParam("ListId",listId);
        //Amazonで検索
        Document xml = getREST();
        return createItemList(xml);
    }
    /**
     * Amazon用アイテムリストの作成
     * @param doc
     * @return
     */
    private List<ItemDto> createItemList(Document doc)  {
       
        NodeList nodeList = doc.getElementsByTagName("Item");
        List<ItemDto> itemList = new ArrayList<ItemDto>();

        //IDのリスト数回繰り返す
        for ( int cnt = 0; cnt < nodeList.getLength(); ++cnt ) {

            //アイテムを取得
            Element node = (Element)nodeList.item(cnt);

            NodeList link = node.getElementsByTagName("DetailPageURL");
            NodeList title = node.getElementsByTagName("Title");
            
            NodeList lowPrice = node.getElementsByTagName("LowestNewPrice");
            NodeList price = null;
            if ( lowPrice.getLength() > 0 ) {
                Element pri = (Element)lowPrice.item(0); 
            	price = pri.getElementsByTagName("FormattedPrice");
            }
            if ( price == null ) {
                price = node.getElementsByTagName("FormattedPrice");
            }
            
            NodeList addWishList = node.getElementsByTagName("ItemLink");
            NodeList addWish = null;

            for ( int wcnt = 0; wcnt < addWishList.getLength(); ++wcnt ) {
               Element wish = (Element)addWishList.item(wcnt); 
               NodeList descList = wish.getElementsByTagName("Description");
               //
               for ( int dcnt = 0; dcnt < descList.getLength(); ++dcnt ) {
                   Node desc = descList.item(dcnt);
                   if ( desc.getTextContent().equals("Add To Wishlist")){
                       addWish = wish.getElementsByTagName("URL");
                       break;
                   }
               }
               if ( addWish != null ) {
                   break;
               }
            }

            String[] targetArray = {"MediumImage","SmallImage","LargeImage"};
            NodeList image = null;
            for ( String imageId : targetArray ) {
                NodeList imageList = node.getElementsByTagName(imageId);
                //存在した場合
                if ( !XmlUtil.isEmpty(imageList) ) {
                    Node imageNode = imageList.item(0);
                    image = ((Element)imageNode).getElementsByTagName("URL");
                }
                if ( image != null ) {
                   break; 
                }
            }

            ItemDto dto = new ItemDto();

            //リンクを設定
            dto.setLink(link.item(0).getTextContent());
            //名称を設定
            dto.setName(XmlUtil.escape(title.item(0).getTextContent()));

            //値段を詳細に設定
            if ( !XmlUtil.isEmpty(price) ) {
                dto.setDetail(price.item(0).getTextContent());
            }

            if ( !XmlUtil.isEmpty(image) ) {
                dto.setImageUrl(image.item(0).getTextContent());
            }
            dto.setAddWishListLink(addWish.item(0).getTextContent());

            itemList.add(dto);
        }
        return itemList;
    }
}

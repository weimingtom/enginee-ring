package jp.co.ziro.surpre.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jp.co.ziro.surpre.dto.ItemDto;
import jp.co.ziro.surpre.util.XmlUtil;

public class YahooServiceHelper extends ServiceHelper {

    /**
     * 
     */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(YahooServiceHelper.class.getName());
    
    //APIID
    private static final String APIID    = "dpgTvTuxg65vvxrQW3m7xPaYwDKaQjlLxKwyED9DHVcnq2WcfoALP9MgeCT41DX.CaCT";
    //１個だけ
    private static final String RESULT   = "uniq";
    //バージョン
    private static final String RESPONCE = "surface";
    //名詞だけ
    private static final String FILTER   = "9";
   
    /**
     * 
     * @param sentence 検索用語
     * @return
     */
    public String getSurface(String sentence) {

        //エンドポイント
        String endPoint = "http://jlp.yahooapis.jp/MAService/V1/parse";
        
        //各種引数の設定
        addParam("appid", APIID);
        addParam("results", RESULT);
        addParam("response", RESPONCE);
        addParam("filter", FILTER);
        addParam("sentence", sentence);

        String args = super.canonicalize();
        //URL作成
        String url = endPoint + "?" + args;
        Document doc = super.getDocument(url);

        //ログ出力
        String keyword = XmlUtil.getText(doc,"surface",false);
        return keyword;
    }

    /**
     * キーワード検索
     * @param keyword 検索ワード
     * @return 検索結果
     */
    public List<ItemDto> searchKeyword(String keyword) {

        String endPoint = "http://search.yahooapis.jp/WebSearchService/V1/webSearch";
        addParam("appid", APIID);
        addParam("query", keyword);

        String args = canonicalize();
        //URL作成
        String url = endPoint + "?" + args;
        Document doc = getDocument(url);

        List<ItemDto> itemList = new ArrayList<ItemDto>();
        NodeList wordList = doc.getElementsByTagName("Result");
        
        //検索結果数回繰り返す
        for ( int pcnt = 0 ; pcnt < wordList.getLength(); ++pcnt ) {
            ItemDto dto = new ItemDto();
            //アイテムを取得
            Element result = (Element)wordList.item(pcnt);

            NodeList name   = result.getElementsByTagName("Title");
            NodeList detail = result.getElementsByTagName("Summary");
            NodeList link   = result.getElementsByTagName("Url");

            dto.setName(XmlUtil.escape(name.item(0).getTextContent()));
            dto.setText(XmlUtil.escape(detail.item(0).getTextContent()));
            dto.setLink(link.item(0).getTextContent());
            itemList.add(dto);
        }
        return itemList;
    }
}

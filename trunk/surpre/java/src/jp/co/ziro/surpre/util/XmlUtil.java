package jp.co.ziro.surpre.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML操作
 * @author z001
 */
public class XmlUtil {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(XmlUtil.class.getName());

    /**
     * XML書き換え
     * @return XML文字列
     */
    public static String getString(Document aDoc) {
        StringWriter sw = new StringWriter();
        try {
            TransformerFactory tfactory = TransformerFactory.newInstance(); 
            Transformer transformer;
            transformer = tfactory.newTransformer();
            transformer.transform(new DOMSource(aDoc), new StreamResult(sw)); 
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return sw.toString();
    }
    
    /**
     * XML書き換え
     * @param aXml
     * @return
     */
    public static Document getXml(String aXml) {
        Document rtnDoc = null;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
        	rtnDoc = docbuilder.parse(new ByteArrayInputStream(aXml.getBytes("utf-8")));
        } catch ( Exception ex ) {
            throw new RuntimeException(ex);
        }
        return rtnDoc;
    }
   
    /**
     * テキストを取得
     * @param node
     * @return
     */
    public static String getText(Node node){
        if ( node == null ) return "";
        return node.getTextContent();
    }

    /**
     * テキストを取得
     * @param node
     * @return
     */
    public static String getText(Document doc,String tagName){
        return getText(doc,tagName,0);
    }

    /**
     * テキストを取得
     * @param node
     * @return
     */
    public static String getText(Document doc,String tagName,boolean flag){

        NodeList nodeList = doc.getElementsByTagName(tagName);
        int leng = nodeList.getLength();
        if ( leng <= 0 ) return "";
        
        Random rnd = new Random();
        int ran = rnd.nextInt(leng); 
        
        return nodeList.item(ran).getTextContent();
    }

    /**
     * テキスト取得
     * @param doc
     * @param tagName
     * @param idx
     * @return
     */
    private static String getText(Document doc,String tagName,int idx){
        NodeList nodeList = doc.getElementsByTagName(tagName);
        if ( nodeList.getLength() <= 0 ) return "";
        return nodeList.item(idx).getTextContent();
    }
    /**
     * ノードリストが空かを判定
     * @param nodeList
     * @return
     */
    public static boolean isEmpty(NodeList nodeList) {
        if ( nodeList == null || nodeList.getLength() <= 0 ) {
            return true;
        }
        return false;
    }

    /**
     * escapeする
     * @param target
     * @return
     */
    public static String escape(String target) {
        String escape = target;
        escape = escape.replaceAll("&","&amp;");
        escape = escape.replaceAll(">","&gt;");
        escape = escape.replaceAll("<","&lt;");
        escape = escape.replaceAll("\"","&quot;");
        escape = escape.replaceAll("'","&apos;");

        return escape;
    }

    /**
     * 属性の取得
     * @param link
     * @param string
     * @return
     */
    public static String getAttribute(NodeList nodeList, String attName) {
        Element elm = (Element)nodeList.item(0);
        return elm.getAttribute(attName);
    }
}

package jp.co.ziro.surpre.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;

public class HtmlUtil {

    public static String autoLink(String aValue) {
        if ( StringUtil.isEmpty(aValue) ) return "";
        // コンパイルされた正規表現
        Pattern objPtn = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+", Pattern.CASE_INSENSITIVE);
        // マッチング結果をMatcherオブジェクトに代入
        Matcher objMch = objPtn.matcher(aValue);
        // 置き換え処理（[$0]はマッチングした文字列）
        return objMch.replaceAll("<u><a href='$0' target=\"_blank\">$0</a></u>");
    }

}

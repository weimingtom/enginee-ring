/**
 * セキュリティー系の関数をまとめたもの
 */

/**
 * クロスサイト・スクリプティング用対策
 * 特殊文字を変換することにより、クロスサイトスクリプティングを無害化する
 * @param {Object} str　Form等入力文字
 */
function sanitizeXSS(str){
	// < > " & ' 　　の文字を置換する
	var rtnStr = str;
	rtnStr = rtnStr.replace(/\&/g, '&amp;');
	rtnStr = rtnStr.replace(/</g, '&lt;');
	rtnStr = rtnStr.replace(/>/g, '&gt;');
	rtnStr = rtnStr.replace(/\"/g, '&quot;');
	rtnStr = rtnStr.replace(/\'/g, '&#39;');
	return rtnStr;
}

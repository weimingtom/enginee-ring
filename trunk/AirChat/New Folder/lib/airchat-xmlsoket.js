var xmlsock = new air.XMLSocket(); 
var response;

/**
 * ソケットサーバーへ接続を開始する
 * @param {Object} url ソケットサーバーのURL
 * @param {Object} port　ソケットサーバーのあけているポート番号
 */
function connectSocketServer(url,port) {
	// 指定された TCP ポートを使用して指定されたインターネットホストへの接続を確立します
	xmlsock.connect(url, port);
	

    // サーバーによりソケット接続が閉じられたときに送出されます。
    xmlsock.addEventListener(air.Event.CLOSE, closeHandler);
	// サーバからデータを受信すると、そのたびに data イベントが送出されます
	xmlsock.addEventListener(air.DataEvent.DATA, onData);

    // XMLSocket.connect() メソッドの呼び出しが成功した後に送出されます。
    xmlsock.addEventListener(air.Event.CONNECT, connectHandler);

	// 入出力エラーが発生して送信または受信操作が失敗したときに送出されます。 
    xmlsock.addEventListener(air.IOErrorEvent.IO_ERROR, ioErrorHandler);

	// XMLSocket.connect() メソッドの呼び出しによって、呼び出し元のセキュリティサンドボックスの外にあるサーバーや 1024 未満のポート番号に対して接続しようとしたときに送出されます。
    xmlsock.addEventListener(air.SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
	
}


/**
 * メッセージ送信（XMLSocket用）
 * @param {Object} msg
 */
function sendSocketMessage(msg){
	var xmlMsg = msg + "\n";
	xmlsock.send(xmlMsg);
	xmlsock.send("\n");
}

/**
 * データ受信時の処理
 * @param {Object} event 受信イベント
 */
function onData(event) 
{ 
	var msg = event.data;
	if ( msg.length <= 0) return;

    air.trace("[" + event.type + "] " + msg); 
	var elm = document.getElementById("str");
	var oldmsg = elm.innerHTML;
	// どんどん前にいれていく
	elm.innerHTML = msg + oldmsg ;
	// マウスオーバー時の処理追加
	//glow(elm);
	var strelm = document.getElementById("str");
	var first = strelm.firstChild;
	var imgObj = first.lastChild;
	if ( imgObj != null && imgObj.localName == "img") {
		// 画像イメージ読み込み完了時、ファイルのリサイズを行う
		imgObj.onload = function() { 
			var orgWidth=this.naturalWidth; 
			var orgHeight=this.naturalHeight; 
			// 高さを基本的に３００ｐｘにあわせる。
			var rate = orgHeight / 300;
			var wkwidth = orgWidth / rate;
			this.style.width = wkwidth+"px";
			this.style.height = "300px";
		}
	}
	
}


/**
 * 
 * @param {Object} str
 */
function writeln(str) {
	str += "\n";
	try {
		xmlsock.writeUTFBytes(str);
	} catch(e) {
		air.trace(e);
	}
}

/**
 * リクエスト送信
 */
function sendRequest() {
	//無し
}

/**
 * 
 */
function readResponse() {
	var str = xmlsock.readUTFBytes(xmlsock.bytesAvailable);
	response += str;
}

/**
 * ソケットが閉じた時のイベント
 * @param {Object} event
 */
function closeHandler(event) {
	air.trace("closeHandler: " + event);
	air.trace(response.toString());
}

/**
 * コネクションの接続に成功した場合のイベントハンドラ
 * @param {Object} event
 */
function connectHandler(event) {
	air.trace("connectHandler: " + event);
	// 礼儀として、HNを全員に通達
	sendSocketMessage("HN="+myHandleName);
	// 誰がいるかしらべるコマンドを発行
	sendSocketMessage("WHO");
}

/**
 * ソケットエラーが発生した場合のイベントハンドラ
 * @param {Object} event
 */
function ioErrorHandler(event) {
	air.trace("ioErrorHandler: " + event);
}
/**
 * セキュリティーエラーが発生した場合のイベントハンドラ
 * @param {Object} event
 */
function securityErrorHandler(event) {
	air.trace("securityErrorHandler: " + event);
}


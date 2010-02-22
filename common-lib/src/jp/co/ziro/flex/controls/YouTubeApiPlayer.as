package jp.co.ziro.flex.controls
{
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.net.URLRequest;
	import flash.system.Security;

	public class YouTubeApiPlayer extends Sprite
	{
		private var player:Object;
		private var videoId:String;

		public function YouTubeApiPlayer()
		{
			super();
			var loader:Loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.INIT,initHandler);
			/**
			 * クロムレスなプレイヤーをロードしてカスタマイズするために、以下のURLを使用する
			 */ 
			loader.load(new URLRequest("http://www.youtube.com/apiplayer?version=3"));

			Security.allowDomain("*");
		}
	
		//ビデオID設定
		public function setVideoId(aVideoId:String):void {
			videoId = aVideoId;
		}

		private function initHandler(e:Event):void{
			player = Loader(LoaderInfo(e.currentTarget).loader).content as Object;
			addChild(player as DisplayObject);
			/**
			 * イベントリスナー の登録
			 * onReady プレイヤーのロードと初期化が完了したときに送出
			 * onError プレイヤーのエラー発生時に送出
			 * onStateChange プレイヤーの状態が変化したときに送出
			 * onPlaybackQualityChange プレイヤーの再生品質が変化したときに送出
			 */
			player.addEventListener("onReady",onReadyHandler);
			player.addEventListener("onError", onErrorHandler);
			player.addEventListener("onStateChange", onStateChangeHandler);
			player.addEventListener("onPlaybackQualityChange", onPlaybackQualityChangeHandler);
		}
		private function onReadyHandler(e:Event):void{
			trace("Ready",Object(e).data);
			/**
			 * プレイヤーのサイズ
			 */ 
			player.setSize(160, 120);
			/**
			 * 再生に関するメソッドは、以下の4種類
			 * 1.指定するvideoIDの動画のサムネイルをロード。スタートボタンを中央に表示したまま停止。
			 * player.cueVideoById(videoId:String, startSeconds:Number, suggestedQuality:String):Void
			 * 2.指定するvideoURLの動画のサムネイルをロード。スタートボタンを中央に表示したまま停止。
			 * player.cueVideoByUrl(mediaContentUrl:String, startSeconds:Number):Void
			 * 3.指定するvideoIDの動画をロードして自動的に再生を開始する。
			 * player.loadVideoById(videoId:String, startSeconds:Number, suggestedQuality:String):Void
			 * 4.指定するvideoURLの動画をロードして自動的に再生を開始する。
			 * player.loadVideoByUrl(mediaContentUrl:String, startSeconds:Number):Void
			 */ 
			player.cueVideoById(videoId);
		}
		private function onErrorHandler(e:Event):void{
			/**
			 * エラーコード
			 * 100 要求した動画が見つからないとき
			 * 150,101 要求した動画が組み込みプレイヤーでの再生を許可していないとき
			 */ 
			trace("Error",Object(e).data);
		}
		private function onStateChangeHandler(e:Event):void{
			/**
			 * -1 未スタート（SWFが最初にロードされたとき）
			 * 0 終了
			 * 1 再生中
			 * 2 一時停止
			 * 3 バッファリング
			 * 5 停止（SWFが読み込まれ，再生可能になったとき）
			 */ 
			trace("StateChange",Object(e).data);
		}
		private function onPlaybackQualityChangeHandler(e:Event):void{
			trace("PlaybackQualityChange",Object(e).data);
		}

	}
}
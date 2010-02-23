package jp.co.ziro.surpre
{
	import com.google.analytics.AnalyticsTracker;
	import com.google.analytics.GATracker;
	
	import flash.display.DisplayObject;
	
	public class Globals
	{
		import mx.core.Application;
		import mx.core.FlexGlobals;
		import mx.rpc.events.FaultEvent;

		private static var gblDomain:String = null;
		private static var tracker:AnalyticsTracker = null;

		public function Globals() {
		}
		public static function initTracker(object:DisplayObject):void {
			if (tracker == null) {
				tracker = new GATracker(object, "UA-51600-18", "AS3");
			}
		}
		public static function viewPage(pageUrl:String):void {
			if ( tracker == null ) return;
			tracker.trackPageview(pageUrl);
		}

		/**
		 * ドメインの取得
		 */ 
		public static function getDomain():String {

			//まだ取得してない場合	
			if ( gblDomain == null ) {
				
				//ローカル設定にする
				gblDomain = "http://localhost:8888/"; 
				//URLの取得
				//var url:String = Application.application.url;	
				var url:String = FlexGlobals.topLevelApplication.url;	
				//比較対象を設定
				var httpExp:RegExp = new RegExp("http://[^/]*/");

				//HTTPだった場合
				if ( httpExp.test(url) ) {
					//ローカルホストアクセスにする
					var localExp:RegExp = new RegExp("http://localhost:8888/");
					//ローカルと違った場合
					if ( !localExp.test(url) ) {
						//URLを抜き出してドメインに設定
						gblDomain = httpExp.exec(url).toString();
					}
				}
			}
				
			return gblDomain;
		}

		public static function getBoolean(value:String):Boolean {
			if ( value == "true" ) return true;
			return false;
		}
	}
}
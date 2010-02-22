package jp.co.ziro.flex.events 
{
	import flash.events.Event;
	
	/**
	 * 変更イベントクラス
	 */
	public class PagerEvent extends Event 
	{
		//
		private var myNum:Number;
	
		/**
		 * コンストラクタ
		 */	
		public function PagerEvent(type:String,aNum:Number) 
		{ 
			super(type);
			//現在のデータに設定しておく
			myNum = aNum;
		}

		/**
		 * ページのデータを取得
		 */
		public function getPageNum():Number {
			return myNum;
		}
	}
	
}
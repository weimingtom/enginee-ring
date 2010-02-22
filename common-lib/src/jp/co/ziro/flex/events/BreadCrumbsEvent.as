package jp.co.ziro.flex.events 
{
	import flash.events.Event;
	
	/**
	 * 変更イベントクラス
	 */
	public class BreadCrumbsEvent extends Event 
	{
		/**
		 * 対象のXMLデータ
		 */
		private var myXML:XML;
	
		/**
		 * コンストラクタ
		 */	
		public function BreadCrumbsEvent(type:String,aXML:XML) 
		{ 
			super(type);
			//現在のデータに設定しておく
			myXML = aXML;
		}

		/**
		 * 選択した下の階層のデータリストを取得
		 */
		public function getChildrenList():XMLList {
			return new XMLList(myXML.children());
		}

		/**
		 * 選択したデータと同じ階層のデータリストを取得
		 */
		public function getList():XMLList {
			return new XMLList(myXML.children());
		}
	
		/**
		 * 選択したデータの上の階層のデータを取得
		 */	
		public function getParent():XML {
			return new XML(myXML.parent());
		}
	}
	
}
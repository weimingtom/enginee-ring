package jp.co.ziro.surpre.display
{
	import mx.containers.VBox;

	public class BaseItem extends VBox
	{
		protected var xmlData:XML;
		protected var _idx:Number;

		public function BaseItem()
		{
			super();
			setStyle("verticalGap","2");
		}

		public function init(xml:XML,idx:Number):void {
			xmlData = xml;
			_idx = idx;
			this.callLater(changeItem);
		}

		public function changeItem():void {
		}
	}
}
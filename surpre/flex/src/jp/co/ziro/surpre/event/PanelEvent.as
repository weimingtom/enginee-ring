package jp.co.ziro.surpre.event
{
	import flash.events.Event;

	public class PanelEvent extends Event
	{
		private var myRest:XML;
		private var myItemType:String;
		private var myIndex:Number;
		public function PanelEvent(type:String,result:XML,itemType:String,index:Number,bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			myRest=result;
			myItemType=itemType;
			myIndex=index;
		}

		public function getRest():XML {
			return myRest;	
		}
		public function getItemType():String {
			return myItemType;	
		}
		public function getIndex():Number {
			return myIndex;	
		}
		
		public override function clone():Event {
			return new PanelEvent(type,myRest,myItemType,myIndex);
		}	
	}
}
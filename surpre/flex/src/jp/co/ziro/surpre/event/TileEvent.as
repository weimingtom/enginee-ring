package jp.co.ziro.surpre.event
{
	import flash.events.Event;

	public class TileEvent extends Event
	{
		private var myIdx:Number;
		private var myType:String;

		public function TileEvent(type:String,itemType:String,idx:Number,bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			myIdx=idx;
			myType = itemType;
		}

		public function getIndex():Number {
			return myIdx;	
		}
		public function getType():String {
			return myType;	
		}
	}
}
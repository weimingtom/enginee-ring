package jp.co.ziro.flex.events
{
	import flash.events.Event;

	public class WaitEvent extends Event
	{
		public static var CANCEL:String = "bz.ziro.flex.events.WaitEvent.CANCEL";

		public function WaitEvent(aType:String):void {
			super(aType);
		}
	}
}
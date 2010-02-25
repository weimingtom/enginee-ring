package jp.co.ziro.surpre.event
{
	import flash.events.Event;

	public class TimelineEvent extends Event
	{
		private var myUserId:String;
		public static const REPLY:String = "reply";

		public function TimelineEvent(type:String,userId:String,bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			myUserId = userId;
		}

		public function getUserId():String {
			return myUserId;	
		}

		public override function clone():Event {
			return new TimelineEvent(type,myUserId);
		}		
		
	}
}
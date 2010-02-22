package org.efflex.mx.dataStackEffects
{
	import mx.controls.listClasses.ListBase;
	import mx.core.UIComponent;
	import mx.effects.IEffectInstance;
	
	import org.efflex.mx.dataStackEffects.effectClasses.MoveResizeToFromDisplayObjectInstance;

	public class MoveResizeToFromDisplayObject extends DataStackTweenEffect
	{
		
		
		private static var AFFECTED_PROPERTIES	: Array = [ "x", "y", "scaleX", "scaleY" ];
		
		[Inspectable(category="General", type="Function")]
		public var displayObjectFunction		: Function;
		
		public function MoveResizeToFromDisplayObject( target:UIComponent=null )
		{
			super( target );
			
			instanceClass = MoveResizeToFromDisplayObjectInstance;
		}
		
		override public function getAffectedProperties():Array
		{
			return AFFECTED_PROPERTIES;
		}
		
		override protected function initInstance( instance:IEffectInstance ):void
		{
			super.initInstance( instance );
			
			var effectInstance:MoveResizeToFromDisplayObjectInstance = MoveResizeToFromDisplayObjectInstance( instance );
			effectInstance.displayObjectFunction = displayObjectFunction;
		}
	}
}
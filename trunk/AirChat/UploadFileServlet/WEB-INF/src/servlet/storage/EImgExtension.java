/**
 * 
 */
package servlet.storage;

/**
 * 画像を現す拡張子を示す、列挙
 */
public enum EImgExtension {
	JPEG("jpeg","jpg","jfif"),
	GIF("gif"),
	TIF("tif","tiff"),
	BITMAP("bmp","dib"),
	PNG("png");

	String[] myTypes ;
	private EImgExtension ( String... type) {
		myTypes = type;
	}
	
	public String[] toArray(){
		return myTypes;
	}
	
	
	// 画像かどうか確認する
	public static boolean isImg(String extension) {
		// 全て小文字に変換する
		String wkExt = extension.toLowerCase();
		
		EImgExtension[] objs = EImgExtension.values();
		for ( EImgExtension obj : objs ) {
			String[] exts = obj.toArray();
			for ( String ext : exts ) {
				if ( ext.equals(wkExt) ) {
					return true;
				}
			}
		}
		return false;
	}
}

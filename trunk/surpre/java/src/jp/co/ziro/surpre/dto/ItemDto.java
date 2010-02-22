package jp.co.ziro.surpre.dto;

public class ItemDto {

    /**
     * 名称
     */
    private String name;
  
    /**
     * 詳細
     */
    private String detail;
    /**
     * 詳細
     */
    private String text;

    /**
     * 画像URL
     */
    private String imageUrl;
   
    /**
     * 商品URL
     */
    private String link;

    /**
     * ウィッシュリスト追加用リンク
     */
    private String addWishListLink;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getAddWishListLink() {
        return addWishListLink;
    }
    public void setAddWishListLink(String addWishListLink) {
        this.addWishListLink = addWishListLink;
    }
    @Override
    public String toString() {
        return name;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }


    
}

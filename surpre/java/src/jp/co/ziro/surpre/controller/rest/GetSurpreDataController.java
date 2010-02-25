package jp.co.ziro.surpre.controller.rest;

import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Navigation;

/**
 * データの取得
 * @author z001
 */
public class GetSurpreDataController extends RestController {

    @Override
    protected Navigation createRestData() {
       
        SurpreService service = new SurpreService();
        SurpreData model = service.find();
        
        //Twitterの登録状況
        requestScope("twitter",service.isRegistTwitter(model));

        boolean flickr = service.isRegistFlickr(model);
        //Flickrのnsid
        requestScope("flickr",flickr);
        if ( flickr ) {
            requestScope("nsid",model.getFlickrNsid());
        }

        //mixiId
        requestScope("mixi",service.isRegistMixi(model));
        //wishListId
        requestScope("wish",service.isRegistWishListId(model));
        return forward("surpreData.jsp");
    }
}

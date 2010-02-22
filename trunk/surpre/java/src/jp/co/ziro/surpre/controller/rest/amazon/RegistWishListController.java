package jp.co.ziro.surpre.controller.rest.amazon;

import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * ほしいものIDを登録
 * @author z001
 */
public class RegistWishListController extends Controller {

    @Override
    public Navigation run() {

        String wishListId = requestScope("listId");

        SurpreService service = new SurpreService();
        SurpreData model = service.find();
    
        if ( model == null ) {
            model = new SurpreData();
            model.setKey(Datastore.allocateId(SurpreData.class));

            UserService userService = UserServiceFactory.getUserService();
        	User user = userService.getCurrentUser();
        	model.setUser(user);
        }

        model.setWishListId(wishListId);
        //登録を行う
        Datastore.put(model);
        
        return null;
    }
}

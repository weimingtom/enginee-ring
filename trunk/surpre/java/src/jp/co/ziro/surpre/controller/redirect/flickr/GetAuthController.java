package jp.co.ziro.surpre.controller.redirect.flickr;

import java.util.logging.Logger;

import jp.co.ziro.surpre.helper.FlickrServiceHelper;
import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GetAuthController extends Controller {

    //@SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetAuthController.class.getName());

    @Override
    public Navigation run() {

        SurpreService service = new SurpreService();
        SurpreData model = service.find();
        if ( service.isRegistFlickr(model) ) {
            logger.warning("すでにユーザ登録済");
            return redirect("../../home");
        }

        FlickrServiceHelper helper = new FlickrServiceHelper();
 
        AuthInterface authInterface = helper.getAuthInterface();
        String frob = requestScope("frob");

        try {
            Auth auth = authInterface.getToken(frob);
            if ( model == null ) {
                model = new SurpreData();
                model.setKey(Datastore.allocateId(SurpreData.class));
            }

            model.setFlickrToken(auth.getToken());
            model.setFlickrNsid(auth.getUser().getId());

            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            model.setUser(user);

            Datastore.put(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return redirect("../../home");
    }
}

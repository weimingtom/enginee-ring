package jp.co.ziro.surpre.controller.redirect.mixi;

import java.util.logging.Logger;

import jp.co.ziro.surpre.model.SurpreData;
import jp.co.ziro.surpre.service.SurpreService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GetAuthController extends Controller {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(GetAuthController.class.getName());
    @Override
    public Navigation run() {
        
        String mixiId = requestScope("mixiId");
        if ( mixiId == null || mixiId.equals("") ) {
            return redirect("../../home");
        }

        SurpreService service = new SurpreService();
        SurpreData model = service.find();
        if (model == null) {
            //登録を行う
            model = new SurpreData();
            model.setKey(Datastore.allocateId(SurpreData.class));
        }
        model.setMixiId(mixiId);

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        model.setUser(user);
        Datastore.put(model);

        return redirect("../../home");
    }
}

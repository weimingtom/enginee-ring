package jp.co.ziro.surpre.controller;

import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * ログアウト
 * @author z001
 */
public class LogoutController extends Controller {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(LogoutController.class.getName());

    @Override
    public Navigation run() {
        UserService userService = UserServiceFactory.getUserService();
        return redirect(userService.createLogoutURL("/"));
    }
}

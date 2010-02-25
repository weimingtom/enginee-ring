package jp.co.ziro.surpre.controller;

import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * コントロールパネル
 * @author z001
 */
public class HomeController extends Controller {
 
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    @Override
    public Navigation run() {
        String select = requestScope("select");
        logger.info(select);
        return forward("home.jsp");
    }
}

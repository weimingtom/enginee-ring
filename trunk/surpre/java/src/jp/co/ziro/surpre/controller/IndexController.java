package jp.co.ziro.surpre.controller;

import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * トップページ表示
 * @author z001
 */
public class IndexController extends Controller {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(IndexController.class.getName());

    @Override
    public Navigation run() {
        //TODO 携帯用の処理
        return forward("index.jsp");
    }
}

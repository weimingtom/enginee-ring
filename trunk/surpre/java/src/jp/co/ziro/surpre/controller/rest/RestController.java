package jp.co.ziro.surpre.controller.rest;

import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public abstract class RestController extends Controller {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(RestController.class.getName());

    @Override
    public Navigation run() {
        response.setContentType("text/xml");
        return createRestData();
    }

    protected abstract Navigation createRestData();
}

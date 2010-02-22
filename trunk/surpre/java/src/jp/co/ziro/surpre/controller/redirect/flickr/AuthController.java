package jp.co.ziro.surpre.controller.redirect.flickr;

import java.net.URL;
import java.util.logging.Logger;

import jp.co.ziro.surpre.helper.FlickrServiceHelper;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;

public class AuthController extends Controller {
    
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Override
    public Navigation run() {
 
        FlickrServiceHelper helper = new FlickrServiceHelper();
        AuthInterface authInterface = helper.getAuthInterface();

        String frob;
        try {
            frob = authInterface.getFrob();
            URL url = authInterface.buildAuthenticationUrl(Permission.WRITE, frob);
            return redirect(url.toExternalForm());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package jp.co.ziro.surpre.controller.rest.flickr;

import jp.co.ziro.surpre.controller.redirect.flickr.AuthController;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetAuthUrlControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/flickr/getAuthUrl");
        AuthController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/rest/flickr/getAuthUrl.jsp"));
    }
}

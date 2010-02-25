package jp.co.ziro.surpre.controller.rest.twitter;

import jp.co.ziro.surpre.controller.redirect.twitter.AuthController;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetOAuthUrlControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/twitter/getOAuthUrl");
        AuthController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/rest/twitter/getOAuthUrl.jsp"));
    }
}

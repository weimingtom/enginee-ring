package jp.co.ziro.surpre.controller.rest.twitter;

import jp.co.ziro.surpre.controller.redirect.twitter.RegistAuthController;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetAuthControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/twitter/getAuth");
        RegistAuthController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/rest/twitter/getAuth.jsp"));
    }
}

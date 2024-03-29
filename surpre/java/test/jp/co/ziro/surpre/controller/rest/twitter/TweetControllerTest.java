package jp.co.ziro.surpre.controller.rest.twitter;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TweetControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/twitter/tweet");
        TweetController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}

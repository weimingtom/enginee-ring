package jp.co.ziro.surpre.controller.rest.yahoo;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetUniqControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/yahoo/getUniq");
        SearchController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/rest/yahoo/getUniq.jsp"));
    }
}

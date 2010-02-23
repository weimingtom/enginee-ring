package jp.co.ziro.surpre.controller.rest.amazon;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetWishItemControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/amazon/getWishItem");
        GetWishItemController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}

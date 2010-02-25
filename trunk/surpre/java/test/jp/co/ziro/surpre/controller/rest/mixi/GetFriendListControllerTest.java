package jp.co.ziro.surpre.controller.rest.mixi;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetFriendListControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/mixi/getFriendList");
        GetFriendListController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/rest/mixi/getFriendList.jsp"));
    }
}

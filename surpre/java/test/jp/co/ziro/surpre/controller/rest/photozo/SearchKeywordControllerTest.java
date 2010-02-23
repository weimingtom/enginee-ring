package jp.co.ziro.surpre.controller.rest.photozo;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SearchKeywordControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/rest/photozo/searchKeyword");
        SearchController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}

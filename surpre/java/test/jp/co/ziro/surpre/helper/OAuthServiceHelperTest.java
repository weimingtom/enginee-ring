package jp.co.ziro.surpre.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class OAuthServiceHelperTest {
    
    @Test
    public void testOAuthService() {

        OAuthTestServiceHelper helper = new OAuthTestServiceHelper();
        String hmac = helper.sign();
        assertEquals(hmac,"SGtGiOrgTGF5Dd4RUMguopweOSU=");
    }
    
}

package learn.deepak.java.singleton;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by dtelkar on 7/31/14.
 */
public class TestEnumSingleton {

    @Test
    public void testEnumSinleton() {

        Assert.assertEquals(EnumSingleton.INSTANCE.getMember(), 10);
    }
}

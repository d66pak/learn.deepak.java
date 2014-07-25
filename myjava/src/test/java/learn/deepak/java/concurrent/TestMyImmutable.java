package learn.deepak.java.concurrent;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/17/14.
 */
public class TestMyImmutable {

    @Test
    public void createMyImmutable() {

        MyImmutable.Builder builder = new MyImmutable.Builder();
        MyImmutable mi = builder.one(3).two("intellij").build();
        Assert.assertNotNull(mi);
    }
}

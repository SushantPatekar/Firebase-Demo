package com.firebase.sample.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Sushant.Patekar on 6/16/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogInActivityTest {
    @Test
    public void shouldNottoBenull() {
        LogInActivity activity = Robolectric.setupActivity(LogInActivity.class);
        assertNotNull(activity);
    }

    @Test
    public void name() throws Exception {

    }
}
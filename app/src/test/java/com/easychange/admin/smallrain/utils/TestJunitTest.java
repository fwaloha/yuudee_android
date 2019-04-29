package com.easychange.admin.smallrain.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * gradle clean connectedAndroidTest
 */
public class TestJunitTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {
        new TestJunit().test();
    }
}
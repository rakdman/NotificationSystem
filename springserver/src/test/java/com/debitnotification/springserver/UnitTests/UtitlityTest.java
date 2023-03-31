package com.debitnotification.springserver.UnitTests;

import com.debitnotification.springserver.configuration.user.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtitlityTest {

    @Test
    public void testNullCheckOfValueExists() {
        String emptyString = null;
        Assertions.assertFalse(Utility.isValueExist(emptyString));
    }

    @Test
    public void testEmptyCheckOfValueExists() {
        String emptyString = "";
        Assertions.assertFalse(Utility.isValueExist(emptyString));
    }

    @Test
    public void testNotEmptyCheckOfValueExists() {
        String emptyString = "";
        Assertions.assertTrue(Utility.isValueExist(emptyString));
    }

}

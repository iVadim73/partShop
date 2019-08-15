package by.gvozdovich.partshop.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartValidatorTest {
    CartValidator validator = new CartValidator();

    @Test
    public void isCountValidateEmpty() {
        Assert.assertFalse(validator.countValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isCountValidateCorrect() {
        Assert.assertTrue(validator.countValidate(ParametresTest.CORRECT_COUNT));
    }

    @Test
    public void isCountValidateUncorrect() {
        Assert.assertFalse(validator.countValidate(ParametresTest.UNCORRECT_COUNT));
    }

    @Test
    public void isCountValidateNegative() {
        Assert.assertFalse(validator.countValidate(ParametresTest.NEGATIVE_COUNT));
    }
}
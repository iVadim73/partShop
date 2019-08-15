package by.gvozdovich.partshop.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderValidatorTest {
    OrderValidator validator = new OrderValidator();

    @Test
    public void isStarValidateEmpty() {
        Assert.assertFalse(validator.orderIdValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isStarValidateCorrect() {
        Assert.assertTrue(validator.orderIdValidate(ParametresTest.CORRECT_ORDER_ID));
    }

    @Test
    public void isStarValidateUncorrect() {
        Assert.assertFalse(validator.orderIdValidate(ParametresTest.UNCORRECT_ORDER_ID));
    }

    @Test
    public void isStarValidateBigSize() {
        Assert.assertFalse(validator.orderIdValidate(ParametresTest.NEGATIVE_ORDER_ID));
    }

}
package by.gvozdovich.partshop.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BillValidatorTest {
    BillValidator validator = new BillValidator();

    @Test
    public void isSumValidateEmpty() {
        Assert.assertFalse(validator.sumValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isSumValidateCorrect() {
        Assert.assertTrue(validator.sumValidate(ParametresTest.CORRECT_COST));
    }

    @Test
    public void isSumValidateUncorrect() {
        Assert.assertFalse(validator.sumValidate(ParametresTest.UNCORRECT_COST));
    }

    @Test
    public void isSumValidateNegative() {
        Assert.assertTrue(validator.sumValidate(ParametresTest.NEGATIVE_COST));
    }

}
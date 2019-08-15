package by.gvozdovich.partshop.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PartValidatorTest {

    PartValidator validator = new PartValidator();

    @Test
    public void isCatalogNoValidateEmpty() {
        Assert.assertFalse(validator.catalogNoValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isCatalogNoValidateCorrect() {
        Assert.assertTrue(validator.catalogNoValidate(ParametresTest.CORRECT_CATALOG_NO));
    }

    @Test
    public void isCatalogNoValidateUncorrect() {
        Assert.assertFalse(validator.catalogNoValidate(ParametresTest.UNCORRECT_CATALOG_NO));
    }

    @Test
    public void isNameValidateBigSize() {
        Assert.assertFalse(validator.catalogNoValidate(ParametresTest.BIG_SIZE_400));
    }


    @Test
    public void isSumValidateEmpty() {
        Assert.assertFalse(validator.priceValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isSumValidateCorrect() {
        Assert.assertTrue(validator.priceValidate(ParametresTest.CORRECT_COST));
    }

    @Test
    public void isSumValidateUncorrect() {
        Assert.assertFalse(validator.priceValidate(ParametresTest.UNCORRECT_COST));
    }

    @Test
    public void isSumValidateNegative() {
        Assert.assertFalse(validator.priceValidate(ParametresTest.NEGATIVE_COST));
    }


    @Test
    public void isInfoValidateEmpty() {
        Assert.assertTrue(validator.infoValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isInfoValidateCorrect() {
        Assert.assertTrue(validator.infoValidate(ParametresTest.CORRECT_BRAND_NAME));
    }

    @Test
    public void isInfoValidateUncorrect() {
        Assert.assertFalse(validator.infoValidate(ParametresTest.UNCORRECT_BRAND_NAME));
    }

    @Test
    public void isInfoValidateBigSize() {
        Assert.assertFalse(validator.infoValidate(ParametresTest.BIG_SIZE_400));
    }


    @Test
    public void isWaitValidateEmpty() {
        Assert.assertTrue(validator.waitValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isWaitValidateCorrect() {
        Assert.assertTrue(validator.waitValidate(ParametresTest.CORRECT_COUNT));
    }

    @Test
    public void isWaitValidateUncorrect() {
        Assert.assertFalse(validator.waitValidate(ParametresTest.UNCORRECT_COUNT));
    }

    @Test
    public void isWaitValidateNegative() {
        Assert.assertFalse(validator.waitValidate(ParametresTest.NEGATIVE_COUNT));
    }


    @Test
    public void isDataValidateEmpty() {
        Assert.assertFalse(validator.dataValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isDataValidateCorrect() {
        Assert.assertTrue(validator.dataValidate(ParametresTest.CORRECT_BRAND_NAME));
    }

    @Test
    public void isDataValidateUncorrect() {
        Assert.assertFalse(validator.dataValidate(ParametresTest.UNCORRECT_BRAND_NAME));
    }

    @Test
    public void isDataValidateBigSize() {
        Assert.assertFalse(validator.dataValidate(ParametresTest.BIG_SIZE_400));
    }
}
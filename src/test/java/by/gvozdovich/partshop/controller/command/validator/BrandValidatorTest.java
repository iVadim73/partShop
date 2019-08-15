package by.gvozdovich.partshop.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BrandValidatorTest {

    BrandValidator validator = new BrandValidator();

    @Test
    public void isNameValidateEmpty() {
        Assert.assertFalse(validator.nameValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isNameValidateCorrect() {
        Assert.assertTrue(validator.nameValidate(ParametresTest.CORRECT_BRAND_NAME));
    }

    @Test
    public void isNameValidateUncorrect() {
        Assert.assertFalse(validator.nameValidate(ParametresTest.UNCORRECT_BRAND_NAME));
    }

    @Test
    public void isNameValidateBigSize() {
        Assert.assertFalse(validator.nameValidate(ParametresTest.BIG_SIZE_400));
    }



    @Test
    public void isCountryValidateEmpty() {
        Assert.assertFalse(validator.countryValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isCountryValidateCorrect() {
        Assert.assertTrue(validator.countryValidate(ParametresTest.CORRECT_BRAND_NAME));
    }

    @Test
    public void isCountryValidateUncorrect() {
        Assert.assertFalse(validator.countryValidate(ParametresTest.UNCORRECT_BRAND_NAME));
    }

    @Test
    public void isCountryValidateBigSize() {
        Assert.assertFalse(validator.countryValidate(ParametresTest.BIG_SIZE_400));
    }


    @Test
    public void isInfoValidateEmpty() {
        Assert.assertFalse(validator.infoValidate(ParametresTest.EMPTY));
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
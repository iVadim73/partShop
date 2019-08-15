package by.gvozdovich.partshop.controller.command.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FeedbackValidatorTest {
    FeedbackValidator validator = new FeedbackValidator();

    @Test
    public void isCommentValidateEmpty() {
        Assert.assertTrue(validator.commentValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isCommentValidateCorrect() {
        Assert.assertTrue(validator.commentValidate(ParametresTest.CORRECT_BRAND_NAME));
    }

    @Test
    public void isCommentValidateUncorrect() {
        Assert.assertFalse(validator.commentValidate(ParametresTest.UNCORRECT_BRAND_NAME));
    }

    @Test
    public void isCommentValidateBigSize() {
        Assert.assertFalse(validator.commentValidate(ParametresTest.BIG_SIZE_400));
    }


    @Test
    public void isStarValidateEmpty() {
        Assert.assertFalse(validator.starValidate(ParametresTest.EMPTY));
    }

    @Test
    public void isStarValidateCorrect() {
        Assert.assertTrue(validator.starValidate(ParametresTest.CORRECT_STAR));
    }

    @Test
    public void isStarValidateUncorrect() {
        Assert.assertFalse(validator.starValidate(ParametresTest.UNCORRECT_STAR));
    }

    @Test
    public void isStarValidateBigSize() {
        Assert.assertFalse(validator.starValidate(ParametresTest.NEGATIVE_STAR));
    }

}
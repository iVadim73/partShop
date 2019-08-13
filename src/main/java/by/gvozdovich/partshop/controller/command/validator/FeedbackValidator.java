package by.gvozdovich.partshop.controller.command.validator;

/**
 * reg ex validator for FeedBack
 * @author Vadim Gvozdovich
 * @version 1.0
 */
public class FeedbackValidator {
    private static final String STAR_REGEX = "^(10)|\\d$";
    private static final String COMMENT_REGEX = "^.{0,300}$";

    public boolean starValidate(String star) {
        return star.matches(STAR_REGEX);
    }

    public boolean commentValidate(String comment) {
        return comment.matches(COMMENT_REGEX);
    }

    public boolean feedbackValidate(String star, String comment) {
        return starValidate(star)
                && commentValidate(comment);
    }
}

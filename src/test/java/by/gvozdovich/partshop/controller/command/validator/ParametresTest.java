package by.gvozdovich.partshop.controller.command.validator;

public class ParametresTest {

    public static final String EMPTY = "";
    public static final String BIG_SIZE_400 = "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd" +
            "asdasdasdasdasdasdasdasdasdasdasdasdasdasdas@sad.dasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd";

    public static final String CORRECT_COST = "25";
    public static final String UNCORRECT_COST = "12345672345678934567890";
    public static final String NEGATIVE_COST = "-1";

    public static final String CORRECT_COUNT = "25";
    public static final String UNCORRECT_COUNT = "1234";
    public static final String NEGATIVE_COUNT = "-1";

    public static final String CORRECT_STAR = "10";
    public static final String UNCORRECT_STAR = "11";
    public static final String NEGATIVE_STAR = "-1";

    public static final String CORRECT_BRAND_NAME = "sdkfh";
    public static final String UNCORRECT_BRAND_NAME = "sdf!";

    public static final String CORRECT_ORDER_ID = "6";
    public static final String UNCORRECT_ORDER_ID = "5544447";
    public static final String NEGATIVE_ORDER_ID = "-1";

    public static final String CORRECT_CATALOG_NO = "3dkko-6";
    public static final String UNCORRECT_CATALOG_NO = "3";
}

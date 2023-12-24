package com.cafe.java.cafebackend.constants;

public class CafeConstants {

    //UserProfile Constants
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String INVALID_DATA = "Invalid Data.";
    public static final String INVALID_REQUEST_PAYLOAD = "INVALID_REQUEST_PAYLOAD";
    public static final String EMAIL_ALREADY_EXISTS = "Email Already Exists!!";
    public static final String USER_REGISTER_SUCCESSFULLY = "User Registered Successfully";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_STATUS_UPDATED_SUCCESSFULLY= "User status updated successfully";
    public static final long TOKEN_EXPIRATION_GRACE_PERIOD = System.currentTimeMillis() + 1000 * 60 * 60 * 10;
    public static final String INCORRECT_OLD_PASSWORD= "Incorrect Old Password";
    public static final String PASSWORD_UPDATED_SUCCESSFULLY="Password updated successfully";
    public static final String OLD_PASSWORD_CANNOT_BE_SAME_AS_NEW_ONE="Old Password cannot be same as new one";

    public static final String USER_CANNOT_AUTHORIZED_TO_PERFORM_THIS_OPERATION="User cannot authorized to perform this operaton";

    public static final String CHECK_YOUR_MAIL_FOR_CREDENTIALS="Check your mail for credentials";

    public static final String BCRYPT_PATTERN_REGEX = "^\\$2[ayb]\\$.*";


    //Category Constants
    public static final String CATEGORY_ADDED_SUCCESSFULLY="Category added Successfully";
    public static final String CATEGORY_NOT_FOUND="Category not found";

    public static final String CATEGORY_UPDATED_SUCCESSFULLY="Category updated successfully";
    public static final String CATEGORY_DELETED_SUCCESSFULLY="Category deleted successfully";

    public static final String CATEGORY_ALREADY_PRESENT="Category already present";

    //Product Constants

    public static final String PRODUCT_ADDED_SUCCESSFULLY="Product added Successfully";
    public static final String PRODUCT_NOT_FOUND="Product not found";

    public static final String PRODUCT_UPDATED_SUCCESSFULLY="Product updated successfully";
    public static final String PRODUCT_DELETED_SUCCESSFULLY="Product deleted successfully";

    public static final String PRODUCT_ALREADY_PRESENT="Product already present";

    public static final String PRODUCT_NAME_CANNOT_BE_EMPTY="Product name cannot be empty";
    public static final String PRODUCT_DESCRIPTION_CANNOT_BE_EMPTY="Product description cannot be empty";
    public static final String PRODUCT_PRICE_POSITIVE="Product price must be a positive number";
    public static final String PRODUCT_QUANTITY_POSITIVE="Product quantity must be a positive integer";
    public static final String PRODUCT_PIC_CANNOT_BE_EMPTY="Product pic cannot be empty";
    public static final String PRODUCT_STATUS_CANNOT_BE_EMPTY="Product status cannot be empty";
    public static final String CATEGORY_ID_CANNOT_BE_EMPTY="Category id cannot be empty";
    public static final String PRODUCT_AVAILABILITY_CANNOT_BE_EMPTY="Product availability cannot be empty";


    //Bill Constants

    public static final String BILL_GENERATED_SUCCESSFULLY="Bill Generated Successfully";


}

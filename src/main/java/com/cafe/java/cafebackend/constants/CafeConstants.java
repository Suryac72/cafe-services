package com.cafe.java.cafebackend.constants;

public class CafeConstants {
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String INVALID_DATA = "Invalid Data.";
    public static final String INVALID_REQUEST_PAYLOAD = "INVALID_REQUEST_PAYLOAD";
    public static final String EMAIL_ALREADY_EXISTS = "Email Already Exists!!";
    public static final String USER_REGISTER_SUCCESSFULLY = "User Registered Successfully";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_STATUS_UPDATED_SUCCESSFULLY= " User status updated successfully";
    public static final long TOKEN_EXPIRATION_GRACE_PERIOD = System.currentTimeMillis() + 1000 * 60 * 60 * 10;
}

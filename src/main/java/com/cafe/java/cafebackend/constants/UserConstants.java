package com.cafe.java.cafebackend.constants;

public class UserConstants {
    public enum UserRole {
        USER(1),
        ADMIN(2),
        MODERATOR(3);
    
        private final int value;
    
        UserRole(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }
}

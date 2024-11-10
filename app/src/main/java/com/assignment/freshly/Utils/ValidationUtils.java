package com.assignment.freshly.Utils;


public class ValidationUtils {
    public static boolean isUsernameValid(String username) {
        return username!= null;
    }

    public static boolean isPasswordValid(String password) {
        String passwordRegex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}";
        return password.matches(passwordRegex);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^03\\d{2}-\\d{7}$";
        return phoneNumber.matches(regex);
    }

    public static boolean isAddressValid(String address){
        return address!=null && address.length()>10;
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean isGenderValid(int selectedGenderId) {
        return selectedGenderId != -1;
    }


}

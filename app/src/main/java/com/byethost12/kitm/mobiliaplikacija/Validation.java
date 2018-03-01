package com.byethost12.kitm.mobiliaplikacija;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String VALID_CREDENTIALS_REGEX ="^[A-Za-z0-9]{5,13}$";
    private static final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$";
    private static final String VALID_ID_REGEX = "\\d{1,11}$";
    private static final String VALID_SIZE_REGEX = "[0-9.]{1,11}$";
    private static final String VALID_POKEMON_NAME_REGEX = "[A-Za-z]{5,14}$";

    public static boolean isValidCredentials(String credentials){
        Pattern credentialsPattern = Pattern.compile(VALID_CREDENTIALS_REGEX);
        Matcher credentialsMatcher = credentialsPattern.matcher(credentials);
        return credentialsMatcher.find();
    }

    public static boolean isValidPokemonName(String name){
        Pattern pattern = Pattern.compile(VALID_POKEMON_NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public static boolean isValidEmail(String email){
        Pattern emailPattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.find();
    }
    public static boolean isValidId(String id){
        Pattern emailPattern = Pattern.compile(VALID_ID_REGEX);
        Matcher emailMatcher = emailPattern.matcher(id);
        if(id.contains(".")){
            return false;
        }else {
            return emailMatcher.find();
        }
    }
    public static boolean isValidSize(String size){
        Pattern emailPattern = Pattern.compile(VALID_SIZE_REGEX);
        Matcher emailMatcher = emailPattern.matcher(size);
        return emailMatcher.find();
    }
}

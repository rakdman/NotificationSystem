package com.debitnotification.springserver.user;

public class Utility {

    public static boolean isValueExist(Object object) {
        if(object==null) return false;
        object.toString().isBlank();
        return !object.toString().isEmpty();
    }



}

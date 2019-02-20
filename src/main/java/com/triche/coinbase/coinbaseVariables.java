package com.triche.coinbase;

import java.util.List;
import java.util.Map;

public class coinbaseVariables {

    private static String URI = "";
    private static String DOLLAR_TYPE = "";
    private static List<Map<String,String>> PRODUCTS;

    public static void setCURRENCY(String CURRENCY) {
        coinbaseVariables.DOLLAR_TYPE = CURRENCY;
    }

    public static void setURI(String URI) {
        coinbaseVariables.URI = URI;
    }

    public static void setPRODUCTS(List<Map<String, String>> PRODUCTS) {
        coinbaseVariables.PRODUCTS = PRODUCTS;
    }

    public static String getURI() {
        return URI;
    }

    public static String getCURRENCY() {
        return DOLLAR_TYPE;
    }

    public static List<Map<String, String>> getPRODUCTS() {
        return PRODUCTS;
    }
}

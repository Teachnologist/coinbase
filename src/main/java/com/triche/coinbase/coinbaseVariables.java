package com.triche.coinbase;

import java.util.List;
import java.util.Map;

public class coinbaseVariables {

    private static String CB_URI;
    private static String PRO_URI;
    private static String DOLLAR_TYPE;
    private static String PAIRS;
    private static List<Map<String,String>> PRODUCTS;
    private static List BASE_CURRENCIES;
    private static List QUOTE_CURRENCIES;
    private static List PRODUCT_PAIRS;

    public static void setDOLLARTYPE(String CURRENCY) {
        coinbaseVariables.DOLLAR_TYPE = CURRENCY;
    }

    public static void setPROURI(String URI) {
        coinbaseVariables.PRO_URI = URI;
    }

    public static void setCBUri(String cbUri) {
        CB_URI = cbUri;
    }

    public static void setBaseCurrencies(List baseCurrencies) {
        BASE_CURRENCIES = baseCurrencies;
    }

    public static void setQuoteCurrencies(List quoteCurrencies) {
        QUOTE_CURRENCIES = quoteCurrencies;
    }

    public static void setProductPairs(List productPairs) {
        PRODUCT_PAIRS = productPairs;
    }

    public static List getProductPairs() {
        return PRODUCT_PAIRS;
    }

    public static Boolean isProductPair(String pair){
        Boolean truthy = false;

        if(PRODUCT_PAIRS.contains(pair)){
            truthy = true;
        }

        return truthy;
    }

    public static List getBaseCurrencies() {
        return BASE_CURRENCIES;
    }

    public static List getQuoteCurrencies() {
        return QUOTE_CURRENCIES;
    }

    public static void setPAIRS(String PAIRS) {
        coinbaseVariables.PAIRS = PAIRS;
    }

    public static void setPRODUCTS(List<Map<String, String>> PRODUCTS) {
        coinbaseVariables.PRODUCTS = PRODUCTS;
    }

    public static String getPROURI() {
        return PRO_URI;
    }

    public static String getCBUri() {
        return CB_URI;
    }

    public static String getDOLLARTYPE() {
        return DOLLAR_TYPE;
    }

    public static String getPAIRS() {
        return PAIRS;
    }

    public static List<Map<String, String>> getPRODUCTS() {
        return PRODUCTS;
    }
}

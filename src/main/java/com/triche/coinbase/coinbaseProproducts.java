package com.triche.coinbase;

import org.json.JSONArray;
import org.json.JSONObject;
import triche.coinbase.curl.publicAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseProproducts {

    private static String URI = "";
    private static String CURRENCY = "";
    private static List<Map<String,String>> PRODUCTS;

    public coinbaseProproducts(String URL, String currency){
        URI = URL;
        CURRENCY = currency;
    }

    public coinbaseProproducts(){

    }

    public void setCurrency(String currency){
        CURRENCY = currency;
    }

    public String getCurrency(){
        return CURRENCY;
    }

    public void setProductList(){
        List<Map<String,String>> listofobjects = new ArrayList<Map<String,String>>();
        //set endpoint
        publicAPI publicread = new publicAPI(URI,"/products");

        //get json from endpoint - in this case, it returns an array that does not have to be unwrapped
        JSONArray arr = publicread.getprocallAPIArray();


        for(int i=0;i<arr.length();i++){
            JSONObject obj = new JSONObject(arr.get(i).toString());

            if(obj.get("id").toString().contains(CURRENCY) && !obj.get("id").toString().contains("USDC")) {

                Map<String, String> stringobject = new HashMap<String, String>();

                stringobject.put("pair", obj.get("id").toString());
                stringobject.put("id", obj.get("id").toString());

                stringobject.put("base_currency", obj.get("base_currency").toString());
                stringobject.put("quote_currency", obj.get("quote_currency").toString());
                stringobject.put("min_quote", obj.get("base_min_size").toString());
                stringobject.put("max_quote", obj.get("base_max_size").toString());
                listofobjects.add(stringobject);
            }

        }
        PRODUCTS = listofobjects;
    }

    public static List<Map<String,String>> getProductlist(){
        return PRODUCTS;
    }

    public static String getCURRENCY() {
        return CURRENCY;
    }

    public static String getURI() {
        return URI;
    }

    public static List<Map<String,String>> getAllProductsLevelOne(){
        List<Map<String,String>> listofobjects = new ArrayList<Map<String,String>>();

          for(int i=0; i<PRODUCTS.size();i++) {
              String pair = PRODUCTS.get(i).get("pair");
              String min_quote = PRODUCTS.get(i).get("min_quote");
              String max_quote = PRODUCTS.get(i).get("max_quote");
              /*must contain USD; allow user to set preference*/
              if (pair.contains(CURRENCY) && !pair.contains("USDC")) {
                  publicAPI publicread = new publicAPI(URI, "/products/" + pair + "/book");
                  JSONObject obj = publicread.getcallAPIObject();
                  Integer sequence = obj.getInt("sequence");

                  JSONArray bid_array = obj.getJSONArray("bids");

                  JSONArray asks_array = obj.getJSONArray("asks");


                      JSONArray bid = bid_array.getJSONArray(0);
                      JSONArray ask = asks_array.getJSONArray(0);


                      String bid_price = bid.get(0).toString();
                      String bid_size = bid.get(1).toString();
                      String bid_orders = bid.get(2).toString();


                      String ask_price = ask.get(0).toString();
                      String ask_size = ask.get(1).toString();
                      String ask_orders = ask.get(2).toString();

                      Float spread = Float.parseFloat(bid_price) - Float.parseFloat(ask_price);


                      Map<String, String> stringobject = new HashMap<String, String>();
                      stringobject.put("pair", pair);
                      stringobject.put("bid_price", bid_price);
                      stringobject.put("ask_price", ask_price);
                      stringobject.put("spread", spread.toString());
                      stringobject.put("min_quote", min_quote);
                        stringobject.put("max_quote", min_quote);
                      System.out.println("map 1");
                      System.out.print(stringobject);
                      System.out.println("map 2");
                      listofobjects.add(stringobject);
                  /*    try {
                          Thread.sleep(1000);
                      } catch (Exception e) {
                          System.out.println("couldn't sleep");
                      }*/
                  }
              }



        return listofobjects;
    }


}

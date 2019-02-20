package com.triche.coinbase.demo;

import com.triche.coinbase.coinbaseProproducts;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import triche.coinbase.curl.publicAPI;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pro/")
public class sendProProducts {





    @RequestMapping("/products/level1")
    public @ResponseBody String sendMessage(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        try{
            Thread.sleep(2000);
        }catch(Exception e){
            System.out.println(e);
        }

        List PRODUCTS = getAllProductsLevelOne();

        System.out.print(PRODUCTS);

        JSONObject returned = new JSONObject(PRODUCTS);

        return "data: "+returned+"\n\n";


    }

    public List<Map<String,String>> getAllProductsLevelOne(){
        String CURRENCY = coinbaseProproducts.getCURRENCY();
        List<Map<String,String>> PRODUCTS = coinbaseProproducts.getProductlist();
        String URI = coinbaseProproducts.getURI();
        System.out.println("CURRENCY: "+CURRENCY);

        System.out.println("URI: "+URI);
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

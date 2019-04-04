package com.triche.coinbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseTrends {


    private static List<Map<String,Float>> best_order_top;
    private static List last_sell;

    public static void addBestOrder(Float ask,Float bid){
        Map<String,Float> obj = new HashMap<String,Float>();
        obj.put("ask",ask);
        obj.put("bid",bid);
        best_order_top.add(obj);
    }

    public static void clearBestOrders() {
        best_order_top.clear();
    }

    public Map<String,Float> getAverageBestOrder(){
        Float ask = 0.0f;
        Float bid = 0.0f;

        Integer best_order_size = best_order_top.size();

        for(int i=0;i<best_order_size;i++){
            ask += Float.parseFloat(best_order_top.get(i).get("ask").toString());
            bid += Float.parseFloat(best_order_top.get(i).get("bid").toString());
        }

        Float avg_ask = ask/best_order_size;
        Float avg_bid = bid/best_order_size;


        Map<String,Float> obj = new HashMap<String,Float>();
        obj.put("avg_ask",ask);
        obj.put("avg_bid",bid);

        return obj;
    }




}

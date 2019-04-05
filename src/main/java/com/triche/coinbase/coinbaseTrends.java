package com.triche.coinbase;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseTrends {


    private static MutableList<Map> best_order_top  = FastList.newListWith();
    private static List last_sell;
    private static Float best_order_index = 0f;
    private static Float avg_ask = 0.0f;
    private static Float avg_bid = 0.0f;

    public static void addBestOrder(Float ask,Float bid){
        MutableMap<String,Float> map = UnifiedMap.newMap();
        map.put("ask",ask);
        map.put("bid",bid);
        map.put("index",best_order_index);
        best_order_top.add(map);
        best_order_index++;
        System.out.println("best orders top");
        System.out.print(best_order_top.toString());
        System.out.println("best orders top");
        setAverageBestOrder();
    }

    public static void clearBestOrders() {
        best_order_top.clear();
    }

    private static void setAverageBestOrder(){
        Float ask = 0.0f;
        Float bid = 0.0f;

        Integer best_order_size = best_order_top.size();

        for(int i=0;i<best_order_size;i++){
            ask += Float.parseFloat(best_order_top.get(i).get("ask").toString());
            bid += Float.parseFloat(best_order_top.get(i).get("bid").toString());
        }

        avg_ask = ask/best_order_size;
        avg_bid = bid/best_order_size;
    }

    public static Float getAverageBestAsk(){
        return avg_ask;
    }

    public static Float getAverageBestBid(){
        return avg_bid;
    }





}

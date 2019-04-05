package com.triche.sync;

import com.triche.coinbase.coinbaseTrends;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class staticData {

    private static Map<String,Object> orders;
    private JSONObject orders_json;

    public static void setOrders(Map<String, Object> order) {
        System.out.println("****SETTING ORDERS****");
        orders = order;
        System.out.print(orders);
        staticData.converttoJSON();
    }

    public static Map<String, Object> getOrders() {
        return orders;
    }

    public static void converttoJSON() {


        JSONArray ask_array = new JSONArray();
        JSONArray bid_array = new JSONArray();


        Object asks = orders.get("asks");
        Object bids = orders.get("bids");
        Object agg_map = orders.get("agg_map");
        System.out.println("ASKS");
        System.out.print(asks.toString());


        System.out.println("BIDS");
        System.out.print(bids.toString());

        System.out.println("AGG MAP");
        System.out.print(agg_map.toString());
        System.out.println("END");

    }

}

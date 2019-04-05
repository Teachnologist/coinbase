package com.triche.coinbase;

import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.json.JSONObject;

public class tricheTrends {

    private static Float last_ask = 0f;
    private static Float last_bid = 0f;


    public MutableMap<String,String> allTrends(){


        Float ask_trend = coinbaseTrends.getAverageBestAsk() - last_ask;

        Float bid_trend = coinbaseTrends.getAverageBestAsk() - last_bid;

        MutableMap<String,String> map = UnifiedMap.newMap();

        map.put("avg_ask",coinbaseTrends.getAverageBestAsk().toString());
        map.put("avg_bid",coinbaseTrends.getAverageBestBid().toString());

        map.put("ask_trend",ask_trend.toString());
        map.put("bid_trend",bid_trend.toString());


        last_bid = coinbaseTrends.getAverageBestBid();
        last_ask = coinbaseTrends.getAverageBestAsk();

       return map;
    }

    public JSONObject allTrendsJSON(){

        JSONObject map = new JSONObject();

        Float ask_trend = coinbaseTrends.getAverageBestAsk() - last_ask;

        Float bid_trend = coinbaseTrends.getAverageBestAsk() - last_bid;

        map.put("avg_ask",coinbaseTrends.getAverageBestAsk().toString());
        map.put("avg_bid",coinbaseTrends.getAverageBestBid().toString());

        map.put("ask_trend",ask_trend.toString());
        map.put("bid_trend",bid_trend.toString());


        last_bid = coinbaseTrends.getAverageBestBid();
        last_ask = coinbaseTrends.getAverageBestAsk();

        return map;
    }


}

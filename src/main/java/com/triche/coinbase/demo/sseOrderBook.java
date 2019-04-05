package com.triche.coinbase.demo;

import com.triche.coinbase.coinbaseFreedata;
import com.triche.coinbase.coinbaseProproducts;
import com.triche.coinbase.tricheTrends;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/sse/",produces = "text/event-stream;charset=UTF-8")
public class sseOrderBook {


    @RequestMapping(value="/orders",
            method= RequestMethod.GET,
            produces="text/event-stream;charset=UTF-8")
    public @ResponseBody
    String sendOrders(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type","text/event-stream;charset=UTF-8");

        try {
            Thread.sleep(2000);
            coinbaseProproducts proData = new coinbaseProproducts();
         //   JSONObject returned = new JSONObject(freeData.getAllProductConversions().toString());
            System.out.println("sse in");
         //   System.out.print(returned);
            System.out.println("sse out");
            return "data: " + proData.getOrderBookUNPROCESSED(2) + "\n\n";
        }catch(Exception e){
            return "data: " +e.toString();
        }
    }

    @RequestMapping(value="/trades",
            method= RequestMethod.GET,
            produces="text/event-stream;charset=UTF-8")
    public @ResponseBody
    String sendTrades(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type","text/event-stream;charset=UTF-8");

        try {
            Thread.sleep(2500);
            coinbaseProproducts proData = new coinbaseProproducts();
            //   JSONObject returned = new JSONObject(freeData.getAllProductConversions().toString());
            System.out.println("sse in");
            //   System.out.print(returned);
            System.out.println("sse out");
            return "data: " + proData.getTradeBookJSON() + "\n\n";
        }catch(Exception e){
            return "data: " +e.toString();
        }
    }

    @RequestMapping(value="/trends",
            method= RequestMethod.GET,
            produces="text/event-stream;charset=UTF-8")
    public @ResponseBody
    String sendTrends(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type","text/event-stream;charset=UTF-8");

        try {
            Thread.sleep(3000);
            tricheTrends trendData = new tricheTrends();
            //   JSONObject returned = new JSONObject(freeData.getAllProductConversions().toString());
            System.out.println("sse in");
            //   System.out.print(returned);
            System.out.println("sse out");
            return "data: " + trendData.allTrendsJSON() + "\n\n";
        }catch(Exception e){
            return "data: " +e.toString();
        }
    }





}

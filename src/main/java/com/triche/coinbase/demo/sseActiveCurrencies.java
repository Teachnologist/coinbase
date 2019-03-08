package com.triche.coinbase.demo;

import com.triche.coinbase.coinbaseFreedata;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/sse/",produces = "text/event-stream;charset=UTF-8")
public class sseActiveCurrencies {


    @RequestMapping(value="/currencies",
            method= RequestMethod.GET,
            produces="text/event-stream;charset=UTF-8")
    public @ResponseBody
    String sendMessage(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type","text/event-stream;charset=UTF-8");

        try {
            Thread.sleep(3000);
            coinbaseFreedata freeData = new coinbaseFreedata();
         //   JSONObject returned = new JSONObject(freeData.getAllProductConversions().toString());
            System.out.println("sse in");
         //   System.out.print(returned);
            System.out.println("sse out");
            return "data: " + freeData.getAllProductConversions() + "\n\n";
        }catch(Exception e){
            return "data: " +e.toString();
        }
    }





}

package com.triche.coinbase.demo;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import triche.coinbase.curl.publicAPI;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/server/")
public class ServerSent {

    private Integer gain = 0;
    private Double last_amount=0.0;
    private String last_amount_string;

    Integer count1 = 0;
    Integer intcout = 0;

    @RequestMapping(value="/serversent/{includeme}",
            method= RequestMethod.GET,
            produces="application/json")
    public @ResponseBody String sendMessage(HttpServletResponse response, @PathVariable("includeme") String subpath) {
        publicAPI publicread = new publicAPI("https://api.coinbase.com/v2","/prices/BTC-USD/buy");

        Date waiting = new Date();

        //returns entire object
        JSONObject data = null;
        response.setContentType("text/event-stream");
        response.setContentType("application/json;charset=UTF-8");

        try {
            Thread.sleep(3000);
            JSONObject currencies = publicread.getcallAPIObject();

            //returns an array of the data object - th
            //could this be directly mapped to thymeleaf? - no, cannot find those properties so has to be mutated

            data = currencies.getJSONObject("data");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Double new_amount = Double.parseDouble(data.get("amount").toString());

        Double difference = new_amount - last_amount;

        if(difference > 0.0 || difference < 0.0 ) {

            Map<String, String> returning = new HashMap<String, String>();
            returning.put("current", new_amount.toString());
            returning.put("last", last_amount.toString());
            returning.put("change", difference.toString());
            Integer c = intcout + 1;
            returning.put("count", c.toString());

            returning.put("from", subpath);
            last_amount = new_amount;
            intcout = c;

            JSONObject returned = new JSONObject(returning);

        /*the double line end and data have to be present*/
            String toreturn = "data:" + returned + "\n\n";

            System.out.println("TO RETURN " + toreturn);


            Date performed = new Date();

            System.out.println("Wating 1: " + waiting.toString() + " waiting 2: " + performed.toString());


            return toreturn;
        }else{
            return null;
        }
    }
}

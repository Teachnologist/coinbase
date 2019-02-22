package com.triche.coinbase.demo;

import com.triche.coinbase.coinbaseProproducts;
import com.triche.coinbase.coinbaseVariables;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import triche.coinbase.curl.publicAPI;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/pro/",produces = "text/event-stream;charset=UTF-8")
public class sendProProducts{
    /*needs produces statement to work at all*/
    @RequestMapping(value="/products/level1",produces = "text/event-stream;charset=UTF-8")
    @ResponseBody
    public String sendMessage(HttpServletResponse response) {

        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type","text/event-stream;charset=UTF-8");


        System.out.print("Header a\n");
        System.out.print(response.getHeaderNames());
        System.out.print("Header b\n");



        try{
            Thread.sleep(2000);
        }catch(Exception e){
            System.out.println(e);
        }

        coinbaseProproducts pro = new coinbaseProproducts();

        JSONArray data = pro.getAllProductsLevelOneUnprocessed();

        System.out.println("/products/level1 a\n");
        System.out.print(data);
        System.out.print(coinbaseVariables.getPRODUCTS());
        System.out.println("/products/level1 b\n");

        JSONObject obj = new JSONObject();

        obj.put("arrayName",data);


        if(data != null && data.length() > 0) {
            System.out.println("returning data******\n");
            return "data:" + data + "\n\n";
        }else{
            return null;
        }


    }
}

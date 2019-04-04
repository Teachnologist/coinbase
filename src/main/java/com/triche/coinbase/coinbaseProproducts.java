package com.triche.coinbase;

import org.json.JSONArray;
import org.json.JSONObject;
import triche.coinbase.curl.publicAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseProproducts {

    public void setActiveproducts(){
        String URI = coinbaseVariables.getPROURI();
        String CURRENCY = coinbaseVariables.getDOLLARTYPE();
        List<String> base_currencies = new ArrayList<String>();
        List<String> quote_currencies = new ArrayList<String>();
        //set endpoint
        publicAPI publicread = new publicAPI(URI,"/products");

        //get json from endpoint - in this case, it returns an array that does not have to be unwrapped
        JSONArray arr = publicread.getprocallAPIArray();


        for(int i=0;i<arr.length();i++){
            JSONObject obj = new JSONObject(arr.get(i).toString());

            String base = obj.get("base_currency").toString();
            String quote = obj.get("quote_currency").toString();

            if(base_currencies.indexOf(base) < 0){
                base_currencies.add(base);
            }

            if(quote_currencies.indexOf(quote) < 0){
                quote_currencies.add(quote);
            }

        }
        coinbaseVariables.setBaseCurrencies(base_currencies);
        coinbaseVariables.setQuoteCurrencies(quote_currencies);
    }




    public void setProductList(){
        String URI = coinbaseVariables.getPROURI();
        String CURRENCY = coinbaseVariables.getDOLLARTYPE();
        List<Map<String,String>> listofobjects = new ArrayList<Map<String,String>>();
        List<String> listofpairs = new ArrayList<String>();
        //set endpoint
        publicAPI publicread = new publicAPI(URI,"/products");

        //get json from endpoint - in this case, it returns an array that does not have to be unwrapped
        JSONArray arr = publicread.getprocallAPIArray();


        for(int i=0;i<arr.length();i++){
            JSONObject obj = new JSONObject(arr.get(i).toString());


                Map<String, String> stringobject = new HashMap<String, String>();

                stringobject.put("pair", obj.get("id").toString());
                stringobject.put("id", obj.get("id").toString());

                stringobject.put("base_currency", obj.get("base_currency").toString());
                stringobject.put("quote_currency", obj.get("quote_currency").toString());
                stringobject.put("min_quote", obj.get("base_min_size").toString());
                stringobject.put("max_quote", obj.get("base_max_size").toString());

                if(!listofpairs.contains(obj.get("id").toString())) {
                    listofpairs.add(obj.get("id").toString());
                }
                listofobjects.add(stringobject);
        }
        coinbaseVariables.setProductPairs(listofpairs);
        coinbaseVariables.setPRODUCTS(listofobjects);
    }

    public JSONObject getTradeBookJSON(){

        String URI = coinbaseVariables.getPROURI();
        String pair = coinbaseVariables.getPAIRS();

        publicAPI publicread = new publicAPI(URI, "/products/"+pair+"/trades");
        JSONArray json_arr = publicread.getprocallAPIArray();
        System.out.println("TRADE BOOK: ");
        JSONArray sells = new JSONArray();
        JSONArray buys = new JSONArray();

        Integer sell_count = 0;
        Integer buy_count = 0;
        Float total_sell = 0.0f;
        Float total_buy = 0.0f;
        Float sell_size = 0.0f;
        Float buy_size = 0.0f;
        Float avg_sell = 0.0f;
        Float avg_buy = 0.0f;
        Float avg_ssize = 0.0f;
        Float avg_bsize = 0.0f;
        Float last_sell = 0.0f;
        Float last_buy = 0.0f;

        for(int i=0;i<json_arr.length();i++){
            System.out.println(i+": "+json_arr.get(i).toString());
            System.out.println(i+": "+json_arr.getJSONObject(i).get("price"));
            JSONObject obj = json_arr.getJSONObject(i);

            String side = obj.get("side").toString();
            String size = obj.get("size").toString();
            String price = obj.get("price").toString();
            String time = obj.get("time").toString();

            switch(side){
                case "sell":
                    if(last_sell == 0.0) {
                        last_sell = Float.parseFloat(price);
                    }
                    sell_count++;
                    total_sell += Float.parseFloat(price);
                    sell_size += Float.parseFloat(size);
                    JSONObject sell = new JSONObject();
                    sell.put("side",side);
                    sell.put("size",size);
                    sell.put("price",price);
                    sell.put("time",time);
                    sells.put(sell);
                    break;
                case "buy":
                    if(last_buy == 0.0) {
                        last_buy = Float.parseFloat(price);
                    }
                    buy_count++;
                    total_buy += Float.parseFloat(price);
                    buy_size += Float.parseFloat(size);
                    JSONObject buy = new JSONObject();
                    buy.put("side",side);
                    buy.put("size",size);
                    buy.put("price",price);
                    buy.put("time",time);
                    buys.put(buy);
                    break;
                default:
                    break;

            }
        }

        avg_sell = total_sell/sell_count;
        avg_buy = total_buy/buy_count;
        avg_ssize = sell_size/sell_count;
        avg_bsize = buy_size/buy_count;

        Float avg_spread = avg_ssize - avg_bsize;


        JSONObject aggregate = new JSONObject();
        aggregate.put("total_sell",total_sell);
        aggregate.put("total_buy",total_buy);
        aggregate.put("sell_size",sell_size);
        aggregate.put("buy_size",buy_size);
        aggregate.put("buy_count",buy_count);
        aggregate.put("last_buy",last_buy);

        aggregate.put("avg_sell",avg_sell);
        aggregate.put("avg_buy",avg_buy);
        aggregate.put("avg_ssize",avg_ssize);
        aggregate.put("avg_bsize",avg_bsize);
        aggregate.put("sell_count",sell_count);
        aggregate.put("last_sell",last_sell);
        aggregate.put("avg_spread",avg_spread);

        JSONObject trade_map = new JSONObject();
        trade_map.put("sells",sells);
        trade_map.put("buys",buys);
        trade_map.put("aggregate",aggregate);

        return trade_map;
    }

    public Map<String,Object> getTradeBook(){

        String URI = coinbaseVariables.getPROURI();
        String pair = coinbaseVariables.getPAIRS();

        publicAPI publicread = new publicAPI(URI, "/products/"+pair+"/trades");
        JSONArray json_arr = publicread.getprocallAPIArray();
        System.out.println("TRADE BOOK: ");
        List<Map<String,String>> sells = new ArrayList<Map<String,String>>();
        List<Map<String,String>> buys = new ArrayList<Map<String,String>>();

        Integer sell_count = 0;
        Integer buy_count = 0;
        Float total_sell = 0.0f;
        Float total_buy = 0.0f;
        Float sell_size = 0.0f;
        Float buy_size = 0.0f;
        Float avg_sell = 0.0f;
        Float avg_buy = 0.0f;
        Float avg_ssize = 0.0f;
        Float avg_bsize = 0.0f;
        Float last_sell = 0.0f;
        Float last_buy = 0.0f;

        for(int i=0;i<json_arr.length();i++){
            System.out.println(i+": "+json_arr.get(i).toString());
            System.out.println(i+": "+json_arr.getJSONObject(i).get("price"));
            JSONObject obj = json_arr.getJSONObject(i);

            String side = obj.get("side").toString();
            String size = obj.get("size").toString();
            String price = obj.get("price").toString();
            String time = obj.get("time").toString();

            switch(side){
                case "sell":
                    if(last_sell == 0.0) {
                        last_sell = Float.parseFloat(price);
                    }
                    sell_count++;
                    total_sell += Float.parseFloat(price);
                    sell_size += Float.parseFloat(size);
                    Map<String,String> sell = new HashMap<String,String>();
                    sell.put("side",side);
                    sell.put("size",size);
                    sell.put("price",price);
                    sell.put("time",time);
                    sells.add(sell);
                    break;
                case "buy":
                    if(last_buy == 0.0) {
                        last_buy = Float.parseFloat(price);
                    }
                    buy_count++;
                    total_buy += Float.parseFloat(price);
                    buy_size += Float.parseFloat(size);
                    Map<String,String> buy = new HashMap<String,String>();
                    buy.put("side",side);
                    buy.put("size",size);
                    buy.put("price",price);
                    buy.put("time",time);
                    buys.add(buy);
                    break;
                default:
                    break;

            }
        }

        avg_sell = total_sell/sell_count;
        avg_buy = total_buy/buy_count;
        avg_ssize = sell_size/sell_count;
        avg_bsize = buy_size/buy_count;

        Float avg_spread = avg_ssize - avg_bsize;


        Map<String,Object> aggregate = new HashMap<String,Object>();
        aggregate.put("total_sell",total_sell);
        aggregate.put("total_buy",total_buy);
        aggregate.put("sell_size",sell_size);
        aggregate.put("buy_size",buy_size);
        aggregate.put("buy_count",buy_count);
        aggregate.put("last_buy",last_buy);

        aggregate.put("avg_sell",avg_sell);
        aggregate.put("avg_buy",avg_buy);
        aggregate.put("avg_ssize",avg_ssize);
        aggregate.put("avg_bsize",avg_bsize);
        aggregate.put("sell_count",sell_count);
        aggregate.put("last_sell",last_sell);
        aggregate.put("avg_spread",avg_spread);

        Map<String,Object> trade_map = new HashMap<String,Object>();
        trade_map.put("sells",sells);
        trade_map.put("buys",buys);
        trade_map.put("aggregate",aggregate);

        return trade_map;
    }

    public Map<String,Object> getOrderBook(Integer level){
        String URI = coinbaseVariables.getPROURI();
        String pair = coinbaseVariables.getPAIRS();

        publicAPI publicread = new publicAPI(URI, "/products/" + pair + "/book?level="+level);
        JSONObject obj = publicread.getcallAPIObject();
        System.out.println("top of new data for level "+level+"\n");
        System.out.print(obj);
        System.out.println("end of new data for level "+level+"\n");

        List<Map<String,String>> arr = new ArrayList<Map<String,String>>();

        JSONArray asks = obj.getJSONArray("asks");
        JSONArray bids = obj.getJSONArray("bids");

        List<Map<String,String>> ask_array = new ArrayList<Map<String,String>>();
        List<Map<String,String>> bid_array = new ArrayList<Map<String,String>>();

        Float ask_size = 0.0f;
        Float ask_avg = 0.0f;
        Float ask_total = 0.0f;
        Float ask_count = 0.0f;

        Integer i_count = 0;

        Float bid_size = 0.0f;
        Float bid_avg = 0.0f;
        Float bid_total= 0.0f;
        Float bid_count = 0.0f;

        Integer b_count = 0;

        for(int i=0;i<asks.length();i++){
            i_count++;
            JSONArray data = asks.getJSONArray(i);

            Map<String,String> map = new HashMap<String,String>();


            ask_size += Float.parseFloat(data.get(1).toString());
            ask_count += Float.parseFloat(data.get(2).toString());
            Float a = Float.parseFloat(data.get(0).toString());
            Float b = Float.parseFloat(data.get(2).toString());

            Float at = a*b;
            ask_total += at;

            map.put("price",data.get(0).toString());
            map.put("size",data.get(1).toString());
            map.put("count",data.get(2).toString());
            ask_array.add(map);
        }

        ask_avg =  ask_total/ask_count;




        for(int q=0;q<bids.length();q++){
            JSONArray data = bids.getJSONArray(q);

            Map<String,String> map = new HashMap<String,String>();

            bid_size += Float.parseFloat(data.get(1).toString());
            bid_count += Float.parseFloat(data.get(2).toString());

            Float bt = Float.parseFloat(data.get(0).toString()) * Float.parseFloat(data.get(2).toString());
            bid_total += bt;


            map.put("price",data.get(0).toString());
            map.put("size",data.get(1).toString());
            map.put("count",data.get(2).toString());
            bid_array.add(map);

        }

        bid_avg =  bid_total/bid_count;

        Map<String,Object> askmap = new HashMap<String,Object>();
        askmap.put("asks",ask_array);
        askmap.put("bids",bid_array);

        Map<String,Object> agg_map = new HashMap<String,Object>();
        agg_map.put("ask_size",ask_size);
        agg_map.put("ask_count",ask_count);
        agg_map.put("ask_total",ask_total);
        agg_map.put("ask_avg",ask_avg);

        agg_map.put("bid_size",bid_size);
        agg_map.put("bid_count",bid_count);
        agg_map.put("bid_total",bid_total);
        agg_map.put("bid_avg",bid_avg);
        Float ask_percentage = ask_size / (ask_size+bid_size);
        Float ask_percentage_formatted = ask_percentage*100;
        Float bid_percentage_formatted = 100-ask_percentage_formatted;

        String bid_color = "green";

        if(bid_percentage_formatted > 40 && bid_percentage_formatted < 60){
            bid_color = "orange";
        }else if(bid_percentage_formatted < 40){
            bid_color = "red";
        }

        agg_map.put("bid_color",bid_color);
        agg_map.put("ask_percentage",ask_percentage);
        agg_map.put("ask_percentage_formatted",ask_percentage_formatted);
        agg_map.put("bid_percentage_formatted",bid_percentage_formatted);


        askmap.put("agg_map",agg_map);

System.out.print(agg_map);
        return askmap;

    }

    public JSONObject getOrderBookUNPROCESSED(Integer level){
        java.lang.String URI = coinbaseVariables.getPROURI();
        java.lang.String pair = coinbaseVariables.getPAIRS();

        publicAPI publicread = new publicAPI(URI, "/products/" + pair + "/book?level="+level);
        JSONObject obj = publicread.getcallAPIObject();
        System.out.println("top of new data for level "+level+"\n");
        System.out.print(obj);
        System.out.println("end of new data for level "+level+"\n");

        List<Map<java.lang.String, java.lang.String>> arr = new ArrayList<Map<java.lang.String, java.lang.String>>();

        JSONArray asks = obj.getJSONArray("asks");
        JSONArray bids = obj.getJSONArray("bids");

        JSONArray ask_array = new JSONArray();
        JSONArray bid_array = new JSONArray();

        Float ask_size = 0.0f;
        Float ask_avg = 0.0f;
        Float ask_total = 0.0f;
        Float ask_count = 0.0f;

        Integer i_count = 0;

        Float bid_size = 0.0f;
        Float bid_avg = 0.0f;
        Float bid_total= 0.0f;
        Float bid_count = 0.0f;

        Integer b_count = 0;

        for(int i=0;i<asks.length();i++){
            i_count++;
            JSONArray data = asks.getJSONArray(i);

            JSONObject json_object = new JSONObject();


            ask_size += Float.parseFloat(data.get(1).toString());
            ask_count += Float.parseFloat(data.get(2).toString());
            Float a = Float.parseFloat(data.get(0).toString());
            Float b = Float.parseFloat(data.get(2).toString());

            Float at = a*b;
            ask_total += at;

            json_object.put("price",data.get(0).toString());
            json_object.put("size",data.get(1).toString());
            json_object.put("count",data.get(2).toString());
            ask_array.put(json_object);
        }

        ask_avg =  ask_total/ask_count;




        for(int q=0;q<bids.length();q++){
            JSONArray data = bids.getJSONArray(q);

            JSONObject json_object = new JSONObject();

            bid_size += Float.parseFloat(data.get(1).toString());
            bid_count += Float.parseFloat(data.get(2).toString());

            Float bt = Float.parseFloat(data.get(0).toString()) * Float.parseFloat(data.get(2).toString());
            bid_total += bt;


            json_object.put("price",data.get(0).toString());
            json_object.put("size",data.get(1).toString());
            json_object.put("count",data.get(2).toString());
            bid_array.put(json_object);

        }

        bid_avg =  bid_total/bid_count;

        JSONObject askmap = new JSONObject();
        askmap.put("asks",ask_array);
        askmap.put("bids",bid_array);

        JSONObject agg_map = new JSONObject();
        agg_map.put("ask_size",ask_size);
        agg_map.put("ask_count",ask_count);
        agg_map.put("ask_total",ask_total);
        agg_map.put("ask_avg",ask_avg);

        agg_map.put("bid_size",bid_size);
        agg_map.put("bid_count",bid_count);
        agg_map.put("bid_total",bid_total);
        agg_map.put("bid_avg",bid_avg);

        Float ask_percentage = ask_size / (ask_size+bid_size);
        Float ask_percentage_formatted = ask_percentage*100;
        Float bid_percentage_formatted = 100-ask_percentage_formatted;
        String bid_color = "green";

        if(bid_percentage_formatted > 40 && bid_percentage_formatted < 60){
            bid_color = "orange";
        }else if(bid_percentage_formatted < 40){
            bid_color = "red";
        }

        agg_map.put("bid_color",bid_color);
        agg_map.put("ask_percentage",ask_percentage);
        agg_map.put("ask_percentage_formatted",ask_percentage_formatted);
        agg_map.put("bid_percentage_formatted",bid_percentage_formatted);

        askmap.put("agg_map",agg_map);

        System.out.print(agg_map);
        return askmap;

    }



    public JSONArray getAllProductsLevelOneUnprocessed(){
        String URI = coinbaseVariables.getPROURI();
        String curry = coinbaseVariables.getDOLLARTYPE();
        System.out.print("DOLLAR type: "+curry+"\n");
        System.out.print("DOLLAR URI: "+URI+"\n");
        List<Map<String,String>> PRODUCTS = coinbaseVariables.getPRODUCTS();
        System.out.print(PRODUCTS);
        JSONArray listofobjects = new JSONArray();
        System.out.print(PRODUCTS.toString());
        for(int i=0; i<PRODUCTS.size();i++) {
            String pair = PRODUCTS.get(i).get("pair");
            String min_quote = PRODUCTS.get(i).get("min_quote");
            String max_quote = PRODUCTS.get(i).get("max_quote");
              /*must contain USD; allow user to set preference*/
            //       if (pair.contains(CURRENCY) && !pair.contains("USDC")) {
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

            JSONObject stringobject = new JSONObject();

            stringobject.put("pair", pair);
            stringobject.put("bid_price", bid_price);
            stringobject.put("ask_price", ask_price);
            stringobject.put("spread", spread.toString());
            stringobject.put("min_quote", min_quote);
            stringobject.put("max_quote", max_quote);
            System.out.println("map 1");
            System.out.print(stringobject);
            System.out.println("map 2");
            listofobjects.put(stringobject);
        }
        return listofobjects;

    }


    public List<Map<String,String>> getAllProductsLevelOne(){
        String URI = coinbaseVariables.getPROURI();
        String CURRENCY = coinbaseVariables.getDOLLARTYPE();
        System.out.print("DOLLAR type: "+CURRENCY+"\n");
        System.out.print("DOLLAR URI: "+URI+"\n");
        List<Map<String,String>> PRODUCTS = coinbaseVariables.getPRODUCTS();
        System.out.print(PRODUCTS.toString());
        List<Map<String,String>> listofobjects = new ArrayList<Map<String,String>>();

          for(int i=0; i<PRODUCTS.size();i++) {
              String pair = PRODUCTS.get(i).get("pair");
              String min_quote = PRODUCTS.get(i).get("min_quote");
              String max_quote = PRODUCTS.get(i).get("max_quote");
              /*must contain USD; allow user to set preference*/
       //       if (pair.contains(CURRENCY) && !pair.contains("USDC")) {
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
                        stringobject.put("max_quote", max_quote);
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
            //  }



        return listofobjects;
    }


}

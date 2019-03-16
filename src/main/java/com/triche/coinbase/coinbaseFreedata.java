package com.triche.coinbase;

import org.json.JSONArray;
import org.json.JSONObject;
import triche.coinbase.curl.publicAPI;


import java.util.*;

public class coinbaseFreedata {


    public coinbaseFreedata(){

    }

    public JSONArray getAllProductConversions(){
        String URL = coinbaseVariables.getCBUri();

        List base_currencies = coinbaseVariables.getBaseCurrencies();
        List quote_currencies = coinbaseVariables.getQuoteCurrencies();

        Collections.sort(base_currencies);
        Collections.sort(quote_currencies);
        JSONArray arr = new JSONArray();

System.out.print("base_currencies: "+base_currencies.toString()+"\n");
        System.out.print("quote_currencies: "+quote_currencies.toString()+"\n");

        for(int i = 0;i<quote_currencies.size();i++) {
            String b_currency = quote_currencies.get(i).toString().toUpperCase();
            String endpoint = "/exchange-rates?currency="+b_currency;
            publicAPI publicread = new publicAPI(URL, endpoint);
            JSONObject obj = publicread.getcallAPIObject();

            JSONObject data = obj.getJSONObject("data");
            JSONObject rates = data.getJSONObject("rates");

            JSONArray listofobject = new JSONArray();
            JSONObject bigobject = new JSONObject();
            for (int q = 0; q < base_currencies.size(); q++) {
                JSONObject quoteobject = new JSONObject();

                String uppercase_quote = base_currencies.get(q).toString().toUpperCase();

                if(rates.has(uppercase_quote)){
                    String rate = rates.get(uppercase_quote).toString();
                if (rate != null) {

                    quoteobject.put("quote",uppercase_quote);
                    quoteobject.put("rate",rate);
                    String pair = b_currency+"-"+uppercase_quote;
                    quoteobject.put("pair",pair);
                    listofobject.put(quoteobject);
                }
            }
                //[{BTC:{USD:100,EUR:200,...    }]
            }
            bigobject.put("key",b_currency);
            Date date = new Date();
            bigobject.put("date",date.toString());
            bigobject.put("milliseconds",date.getTime());
            bigobject.put("values",listofobject);
            arr.put(bigobject);
        }

        System.out.println("Complete..."+"\n");

        System.out.print("\n"+arr.toString()+"\n");
        System.out.println("\n"+"....Complete"+"\n");
        return arr;
    }

    public List<Map<String,Object>> getAllProductConversionsAsList(){
        String URL = coinbaseVariables.getCBUri();

        List base_currencies = coinbaseVariables.getBaseCurrencies();
        List quote_currencies = coinbaseVariables.getQuoteCurrencies();

        Collections.sort(base_currencies);
        Collections.sort(quote_currencies);
        List<Map<String,Object>> arr = new ArrayList<Map<String,Object>>();

        System.out.print("base_currencies: "+base_currencies.toString()+"\n");
        System.out.print("quote_currencies: "+quote_currencies.toString()+"\n");

        for(int i = 0;i<quote_currencies.size();i++) {
            String b_currency = quote_currencies.get(i).toString().toUpperCase();
            String endpoint = "/exchange-rates?currency="+b_currency;
            publicAPI publicread = new publicAPI(URL, endpoint);
            JSONObject obj = publicread.getcallAPIObject();

            JSONObject data = obj.getJSONObject("data");
            JSONObject rates = data.getJSONObject("rates");

            List<Map<String,String>> listofobject = new ArrayList<Map<String,String>>();
            Map<String,Object> bigobject = new HashMap<String,Object>();
            for (int q = 0; q < base_currencies.size(); q++) {
                Map<String,String> quoteobject = new HashMap<String,String>();

                String uppercase_quote = base_currencies.get(q).toString().toUpperCase();

                if(rates.has(uppercase_quote)){
                    String rate = rates.get(uppercase_quote).toString();
                    if (rate != null) {
                        quoteobject.put("quote",uppercase_quote);
                        quoteobject.put("rate",rate);
                        String pair = b_currency+"-"+uppercase_quote;
                        quoteobject.put("pair",pair);
                        quoteobject.put(uppercase_quote, rate);
                        listofobject.add(quoteobject);
                    }
                }
                //[{BTC:{USD:100,EUR:200,...    }]
            }
            bigobject.put("key",b_currency);
            String date = new Date().toString();
            bigobject.put("date",date);
            bigobject.put("values",listofobject);
            arr.add(bigobject);
        }

        System.out.println("Complete..."+"\n");

        System.out.print("\n"+arr.toString()+"\n");
        System.out.println("\n"+"....Complete"+"\n");
        return arr;
    }


    public List<Map<String,String>> getAllCurrencies(){
        String URL = coinbaseVariables.getCBUri();

        publicAPI publicread = new publicAPI(URL, "/currencies");
        JSONObject obj = publicread.getcallAPIObject();
        JSONArray arr = obj.getJSONArray("data");

        List list = new ArrayList<Map<String,String>>();

        for(int i=0;i<arr.length();i++){
            Map<String,String> map = new HashMap<String,String>();

            String str = arr.get(i).toString();

            JSONObject single = new JSONObject(str);

            map.put("id",single.get("id").toString());
            map.put("name",single.get("name").toString());
            list.add(map);

        }

        return list;
    }



}

package com.triche.coinbase;

import org.json.JSONArray;
import org.json.JSONObject;
import triche.coinbase.curl.publicAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseFreedata {


    public coinbaseFreedata(){

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

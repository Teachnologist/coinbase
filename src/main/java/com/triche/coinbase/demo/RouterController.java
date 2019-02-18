package com.triche.coinbase.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import triche.coinbase.curl.publicAPI;
import triche.coinbase.curl.readAPI;

import java.util.*;

@Controller
public class RouterController {

    @Value("${coinbase.api.CLIENT_ID}")
    String CLIENT_ID;

    @Value("${coinbase.api.REDIRECT_URL}")
    String REDIRECT_URL;

    @Value("${coinbase.api.SCOPE}")
    String SCOPE;


    @Value("${coinbase.api.URL}")
    String URL;

    @Value("${coinbase.api.DEV_KEY}")
    String DEV_KEY;

    @Value("${coinbase.view.links}")
    private String[] LINKS;

    String CODE = null;


    @RequestMapping(value = "/")
    public String index(Model model) {
        System.out.println("\n....................................This should show....................................\n");
        String url = "https://www.coinbase.com/oauth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=code&scope=" + SCOPE;
        model.addAttribute("link", url);
        return "home";
    }

    @RequestMapping(value = "/server")
    public String test(Model model) {
        System.out.println("\n....................................SERVER SENT TEST...................................\n");
        return "serversent";
    }

    @RequestMapping(value = "/coinbase")
    public String coinbase(@RequestParam(value = "code", required = false) String code, Model model) {
        System.out.println("\n....................................This is coinbase code....................................\n");

        if (DEV_KEY != null) {
            CODE = DEV_KEY;
            System.out.print("Acting in development mode\n");
        } else {
            if (code != null) {
                System.out.print("Received Response\n");
                System.out.print("Code is: " + code + "\n");
                CODE = code;
            }
        }
        /*for testing - use CODE whn working in an ssl environment*/
        /*processed as object*/
        publicAPI publicread = new publicAPI(URL,"/prices/BTC-USD/buy");

        //returns entire object
        JSONObject currencies = publicread.getcallAPI();

        //returns an array of the data object - th
        //could this be directly mapped to thymeleaf? - no, cannot find those properties so has to be mutated

        JSONObject data = currencies.getJSONObject("data");

        if(!data.getClass().isArray()){
            System.out.println("DATA IS NOT AN ARRAY - PROCESS AS OBJECT");
            System.out.println(data);
            model.addAttribute("lists", null);
/*move this later - testing for time intervals*/
            Integer count = 0;
            Integer seconds = 3;
            Integer milliseconds = seconds*1000;
         /*   try {
                while (count < 30) {
                    publicread = new publicAPI(URL,"/prices/BTC-USD/buy");

                    //returns entire object
                    currencies = publicread.getcallAPI();

                    //returns an array of the data object - th
                    //could this be directly mapped to thymeleaf? - no, cannot find those properties so has to be mutated

                    data = currencies.getJSONObject("data");
                    Thread.sleep(milliseconds);
                    count++;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        }else {

            System.out.println("DATA MUST BE AN ARRAY");

            JSONArray array = currencies.getJSONArray("data");

            List<Map> listofobjects = new ArrayList<Map>();


            for (int i = 0; i < array.length(); i++) {


                System.out.println("Index: " + i + " Name: " + array.get(i).toString());

                JSONObject obj = new JSONObject(array.get(i).toString());
                System.out.println("The official object text: " + obj.get("name"));

                Map<String, String> stringobject = new HashMap<String, String>();
                stringobject.put("name", obj.get("name").toString());
                stringobject.put("id", obj.get("id").toString());
                listofobjects.add(stringobject);

                //get the actual object


            }
            model.addAttribute("lists", listofobjects);
        }
        model.addAttribute("links", LINKS);

        return "coinbase";
    }

    //test for authenticated methods
    @RequestMapping(value = "/coinbase/user")
    public String coinbaseUser(Model model){
        readAPI read = new readAPI(URL,CODE,"/user");
        model.addAttribute("data",read.getcallAPI());
        return "coinbase/user";
    }

    @RequestMapping(value = "/coinbase/accounts")
    public String coinbaseAccounts(Model model){
        readAPI read = new readAPI(URL,CODE,"/accounts");
        JSONObject api = read.getcallAPI();

        JSONArray array = api.getJSONArray("data");

        List<Map> listofobjects = new ArrayList<Map>();


        for (int i = 0; i < array.length(); i++) {
            System.out.println(array.get(i).toString());

        }
        model.addAttribute("data",read.getcallAPI());
        return "coinbase/accounts";
    }

 /* TODO: Address gdax strategy*/


}

package com.triche.coinbase.demo;

import com.triche.coinbase.coinbaseFreedata;
import com.triche.coinbase.coinbaseProproducts;
import com.triche.coinbase.coinbaseVariables;
import com.triche.coinbase.tricheTrends;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import triche.coinbase.curl.publicAPI;
import triche.coinbase.curl.readAPI;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Value("${coinbase.api.PROURL}")
    String PROURL;

    @Value("${coinbase.api.DEV_KEY}")
    String DEV_KEY;

    @Value("${coinbase.view.links}")
    private String[] LINKS;

    String CODE = null;

    String url = "https://www.coinbase.com/oauth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=code&scope=" + SCOPE;

    @RequestMapping(value = "/")
    public String index(Model model) {
        System.out.println("\n....................................This should show currencies....................................\n");
        coinbaseVariables.setCBUri(URL);
        coinbaseVariables.setPROURI(PROURL);


        coinbaseProproducts cbpropublic = new coinbaseProproducts();
        cbpropublic.setProductList();
        cbpropublic.setActiveproducts();

        coinbaseFreedata mod = new coinbaseFreedata();




        model.addAttribute("client_date",new Date().toString());
        model.addAttribute("data",mod.getAllProductConversionsAsList());


    /*    model.addAttribute("usd","$");
        model.addAttribute("eur","€");
        model.addAttribute("gbp","£");

        model.addAttribute("data",mod.getAllCurrencies());*/

        System.out.println("\n....................................Currencies loaded ....................................\n");
        return "currencies";
    }


    @RequestMapping(value = "/orders/{currency}")
    public String indexwithPath(Model model, @PathVariable(value = "currency", required=false) String currency){
        System.out.println("\n....................................This should show....................................\n");

        String dollar_type = "USD";
        if(currency != null){
            /*compare to part of an enum in app props*/
            dollar_type = currency;
        }
        coinbaseVariables.setDOLLARTYPE(dollar_type);
        coinbaseVariables.setPROURI(PROURL);

        coinbaseProproducts cbpropublic = new coinbaseProproducts();
        cbpropublic.setProductList();


        /*TODO make login button with this attribute*/
        String url = "https://www.coinbase.com/oauth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=code&scope=" + SCOPE;

        List data = cbpropublic.getAllProductsLevelOne();
        System.out.print(data);
        if(data.size() > 0) {
            model.addAttribute("link", url);
            model.addAttribute("data", data);
            return "orders";
        }
            return "redirect:/";
    }

    @RequestMapping(value = "/orders/level1/{pairs}")
    public void orderPairs(Model model, @PathVariable(value = "pairs", required=false) String pairs) {
        System.out.println("\n....................................This should show....................................\n");
        coinbaseVariables.setPAIRS(pairs);

System.out.println("GOT IT!: "+pairs);
    }

    @RequestMapping(value = "/orders/level2/{pairs}")
    public String orderL2Pairs(Model model, @PathVariable(value = "pairs", required=false) String pairs) {
        System.out.println("\n....................................This should show....................................\n");
        coinbaseVariables.setPAIRS(pairs);
        coinbaseVariables.setCBUri(URL);
        coinbaseVariables.setPROURI(PROURL);

        coinbaseProproducts modela = new coinbaseProproducts();
        tricheTrends trends = new tricheTrends();
        System.out.println("Level 2");
        System.out.print(modela.getOrderBook(2));
        model.addAttribute("data",modela.getOrderBook(2));
        model.addAttribute("trades",modela.getTradeBook());
        model.addAttribute("trends",trends.allTrends());

        System.out.println("GOT IT!: "+pairs);
        return "orderlist";
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
        JSONObject currencies = publicread.getcallAPIObject();

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
>>>>>>> ee3bce2e24403752220f3d4267ded5f7f67c3da9
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

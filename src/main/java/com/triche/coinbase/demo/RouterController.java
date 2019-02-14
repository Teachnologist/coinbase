package com.triche.coinbase.demo;

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

    String CODE = null;


    @RequestMapping(value = "/")
    public String index(Model model) {
        System.out.println("\n....................................This should show....................................\n");
        String url = "https://www.coinbase.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URL+"&response_type=code&scope="+SCOPE;
        model.addAttribute("link",url);
        return "home";
    }

    @RequestMapping(value = "/coinbase")

    public String coinbase(@RequestParam(value = "code", required = false) String code, Model model) {
        System.out.println("\n....................................This is coinbase code....................................\n");

        if(code != null){
            System.out.print("Received Response\n");
            System.out.print("Code is: "+code+"\n");
            CODE = code;
        }

        /*for testing - use CODE whn working in an ssl environment*/
        String fakecode = "";
        model.addAttribute("user",useBearer(fakecode,"/user"));
        return "coinbase";
    }

    private String useBearer(String bearer, String endpoint){
        String Url = URL+endpoint;
        RestTemplate restTemplate = new RestTemplate();

        //setting the headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + bearer);
        HttpEntity entity = new HttpEntity(headers);

        //executing the GET call
        HttpEntity<String> response = restTemplate.exchange(Url, HttpMethod.GET, entity, String.class);
        /*HttpEntity<String> response = restTemplate.getForEntity(Url, String.class);*/
        //retrieving the response
        System.out.println("Response"+ response.getBody());
        return response.getBody();
    }
}
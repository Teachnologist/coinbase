package triche.coinbase.curl;

import org.apache.tomcat.jni.OS;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class publicAPI {

    private String ENDPOINT;
    private String API_URL;


    public publicAPI(String api_url, String endpoint){
        ENDPOINT = endpoint;
        API_URL = api_url+endpoint;
    }

    public JSONArray getprocallAPIArray(){
        RestTemplate restTemplate = new RestTemplate();

        //setting the headers
        HttpHeaders headers = new HttpHeaders();
        //   headers.add("cache-control","no-cache");
        // headers.add("pragma","no-cache");
        headers.add("upgrade-insecure-requests","1");
        headers.add("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
        //  headers.add("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");


        headers.add("Content-Type","application/json");
        headers.add("accept","application/json");

        //    headers.add("accept-encoding","gzip, deflate, br");
        headers.add("accept-language","en-US,en;q=0.9");



        HttpEntity entity = new HttpEntity(headers);

        //executing the GET call
        HttpEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);
        /*HttpEntity<String> response = restTemplate.getForEntity(API_URL, String.class);*/
        //retrieving the response
        return new JSONArray(response.getBody());
    }



    public JSONObject getcallAPIObject(){
        RestTemplate restTemplate = new RestTemplate();

        //setting the headers
        HttpHeaders headers = new HttpHeaders();
     //   headers.add("cache-control","no-cache");
       // headers.add("pragma","no-cache");
        headers.add("upgrade-insecure-requests","1");
        headers.add("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
      //  headers.add("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");


        headers.add("Content-Type","application/json");
        headers.add("accept","application/json");

    //    headers.add("accept-encoding","gzip, deflate, br");
        headers.add("accept-language","en-US,en;q=0.9");



        HttpEntity entity = new HttpEntity(headers);

        //executing the GET call
        System.out.println("CALLING: "+API_URL);
        HttpEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);
        /*HttpEntity<String> response = restTemplate.getForEntity(API_URL, String.class);*/
        //retrieving the response
        System.out.println("Response"+ response.getBody());
        return new JSONObject(response.getBody());
    }

    public String postcallAPI(){
        RestTemplate restTemplate = new RestTemplate();

        //setting the headers
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        //executing the GET call
        HttpEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        /*HttpEntity<String> response = restTemplate.getForEntity(Url, String.class);*/
        //retrieving the response
        System.out.println("Response"+ response.getBody());
        return response.getBody();
    }
}

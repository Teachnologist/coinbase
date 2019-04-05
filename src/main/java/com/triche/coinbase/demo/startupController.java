package com.triche.coinbase.demo;

import com.triche.coinbase.coinbaseVariables;
import com.triche.sync.orderBook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class startupController {
    @Value("${coinbase.api.PROURL}")
    String PROURL;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        System.out.println("****STARTUP");
        /**default for starting thread**/
        coinbaseVariables.setPAIRS("BTC-USD");
        coinbaseVariables.setPROURI(PROURL);
        Runnable task = new orderBook();
        Thread thread = new Thread(task);
        thread.start();


        System.out.println("STARTUP****");
    }
}





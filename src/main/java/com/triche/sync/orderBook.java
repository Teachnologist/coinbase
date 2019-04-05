package com.triche.sync;

import com.triche.coinbase.coinbaseProproducts;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class orderBook implements Runnable{

    private static Boolean running = false;

    public void run() {
        while(true) {
            try {
                System.out.println("orderBook thread: " + Thread.currentThread().getName());

                coinbaseProproducts coinbasepro = new coinbaseProproducts();
                Map<String, Object> orderbook_map = coinbasepro.getOrderBook(2);
                staticData.setOrders(orderbook_map);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    public void startRunning(){
        setRunning(true);
    }

    public void stopRunning(){
        setRunning(false);
    }

    private static void setRunning(Boolean r){
        running = r;
    }

    private static Boolean getRunning(){
        return running;
    }
}

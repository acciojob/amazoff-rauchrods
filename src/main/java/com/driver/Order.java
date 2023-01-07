package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        this.id=id;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        int hr=0,min=0;
        for(int i=0;i<=1;i++){
            hr=hr*10+(deliveryTime.charAt(i)-'0');
        }
        for(int i=3;i<=4;i++){
            min=min*10+(deliveryTime.charAt(i)-'0');
        }
        this.deliveryTime= hr*60+min;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime(){
        return deliveryTime;
    }
}

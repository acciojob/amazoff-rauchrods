package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    List<Order> orderList;
    List<DeliveryPartner> deliveryPartnerList;

    Map<String,List<String>> hm;

    public OrderRepository() {
        orderList=new ArrayList<>();
        deliveryPartnerList=new ArrayList<>();
        hm = new HashMap<>();
    }

    public String addOrder(Order order){
        orderList.add(order);
        return "New order added successfully";
    }

    public String addPartner(String partnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        deliveryPartnerList.add(deliveryPartner);
        return "New delivery partner added successfully";
    }


    public String addOrderPartnerPair(String orderId, String partnerId){

        for(DeliveryPartner deliveryPartner:deliveryPartnerList){
            if(deliveryPartner.getId().equals(partnerId)){
                deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
                break;
            }
        }

        if(!hm.containsKey(partnerId)){
            hm.put(partnerId,new ArrayList<>());
        }
        hm.get(partnerId).add(orderId);

        return "New order-partner pair added successfully";
    }

    public Order getOrderById(String orderId){
          for(Order order: orderList){
              if(order.getId().equals(orderId)){
                  return order;
              }
          }
          return null;
    }

    public DeliveryPartner getPartnerById(String partnerId){

        for(DeliveryPartner deliveryPartner: deliveryPartnerList){
            if(deliveryPartner.getId().equals(partnerId)){
                return deliveryPartner;
            }
        }
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId){
         return hm.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        return hm.get(partnerId);
    }

    public List<String> getAllOrders(){
        List<String> li = new ArrayList<>();
        for(Order order: orderList){
           li.add(order.getId());
        }
        return li;
    }

    public Integer getCountOfUnassignedOrders(){
        Set<String> hs  =new HashSet<>();

        for(String keystr: hm.keySet()){
            List<String> li = hm.get(keystr);

            for(String str: li){
                hs.add(str);
            }
        }
        int uo=0;
        for(Order order:orderList){
            if(!hs.contains(order.getId())){
                uo++;
            }
        }
        return uo;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int hr=0,min=0;
        for(int i=0;i<=1;i++){
            hr=hr*10+(time.charAt(i)-'0');
        }
        for(int i=3;i<=4;i++){
            min=min*10+(time.charAt(i)-'0');
        }
        int ti= hr*60+min;
        List<String> li = hm.get(partnerId);
        int count=0;
        for(Order order: orderList){
            if(li.contains(order.getId()) && order.getDeliveryTime()>ti){
                count++;
            }
        }
        return count;
    }

    public String deletePartnerById(String partnerId){
        List<String> li = hm.get(partnerId);
        hm.remove(partnerId);
        for(Order order:orderList){
            if (li.contains(order.getId())){
                li.remove(order);
            }
        }

        for(DeliveryPartner deliveryPartner:deliveryPartnerList){
            if(deliveryPartner.getId().equals(partnerId)){
                deliveryPartnerList.remove(deliveryPartner);
            }
        }
        return " removed successfully";
    }

    public String deleteOrderById(String orderId){
          String patneridtoremove = null;
        for(String keystr: hm.keySet()){
            List<String> li = hm.get(keystr);

            for(String str: li){
                if(str.equals(orderId)){
                    patneridtoremove=keystr;
                }
            }
        }
        for(DeliveryPartner deliveryPartner:deliveryPartnerList){
            if(deliveryPartner.getId().equals(patneridtoremove)){
                deliveryPartnerList.remove(deliveryPartner);
            }
        }

        for(Order order:orderList){
           if(order.getId().equals(orderId)){
               orderList.remove(order);
           }
        }
        return " removed successfully";
    }
}

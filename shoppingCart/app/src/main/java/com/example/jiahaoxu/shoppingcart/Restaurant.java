package com.example.jiahaoxu.shoppingcart;

import java.io.Serializable;

/**
 * Created by wentian on 4/19/2017.
 */

public class Restaurant implements Serializable {
    public String address=null;
    public String phone=null;
    public String name=null;
    public String rating=null;
    public String waiting=null;

    public Restaurant(){

    }

    public Restaurant(String input){
        System.out.println("output is " + input);
        setValue(input.substring(1));
    }

    private void setValue(String input){
        String address_val = getTargetValue(input, "address");
        String phone_val = getTargetValue(input, "phone");
        String name_val = getTargetValue(input, "name");
        String rating_val = getTargetValue(input, "rating");
        String waiting_val = getTargetValue(input, "waiting");
        if(address_val!=null){
            this.address = address_val;
        }else{
            this.address = "no address";
        }
        if(phone_val!=null){
            this.phone = phone_val;
        }else{
            this.phone = "no phone";
        }
        if(name_val!=null){
            this.name = name_val;
        }else{
            this.name = "no name";
        }
        if(rating_val!=null){
            this.rating = rating_val;
        }else{
            this.rating = "0";
        }
        if(waiting_val!=null){
            this.waiting = waiting_val;
        }else{
            this.waiting = "0";
        }
    }

    private String getTargetValue(String input, String target){
        int begin = input.indexOf(target+"=");
        if(begin==-1)
            return null;
        begin += target.length()+1;
        int end = input.substring(begin).indexOf(',');
        if(end!=-1)
            end += begin;
        else
            end = input.substring(begin).indexOf("}")+begin;
        System.out.printf("begin:%d, end:%d\n", begin, end);
        return input.substring(begin, end);
    }

    public String toString(){
        String str ="{ name="+name+" address = "+address+" phone="+phone+" rating:"+rating+" waiting:"+waiting+"}";
        return str;
    }

}

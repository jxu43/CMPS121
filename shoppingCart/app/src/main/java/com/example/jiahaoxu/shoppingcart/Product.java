package com.example.jiahaoxu.shoppingcart;

/**
 * Created by Jiahao Xu on 2017/5/1.
 */

public class Product {
    String productName;
    double productValue;

    Product(String name, double value)
    {
        productName = name;
        productValue = value;
    }

    String getName()
    {
        return productName;
    }

    double getValue()
    {
        return productValue;
    }


}

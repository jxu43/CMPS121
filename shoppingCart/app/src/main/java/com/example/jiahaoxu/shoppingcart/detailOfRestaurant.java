package com.example.jiahaoxu.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class detailOfRestaurant extends AppCompatActivity {


    TextView restName;
    TextView restWaitingTime;
    TextView restAddress;

    Button goToCartView;

    Restaurant restaurant;
    //String message;
    RatingBar ratingBar;

    //create a shopping cart
    static Cart m_cart;

    //display the current amount of money needed
    TextView m_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_of_restaurant);
        setContentView(R.layout.test);

        Intent intent = getIntent();
        //get object from viewItem
        restaurant = (Restaurant) intent.getSerializableExtra(viewItem.EXTRA_MESSAGE);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        System.out.println("restaurant rating is " + restaurant.rating);

        restName = (TextView) findViewById(R.id.restaurantName);
        restWaitingTime = (TextView) findViewById(R.id.restaurantWaitingTime);
        restAddress = (TextView) findViewById(R.id.restaurantAddress);

        restName.setText(restaurant.name);
        restWaitingTime.setText("Current Waiting Time is " + restaurant.waiting);
        restAddress.setText(restaurant.address);

        //display the rating of restaurant
        ratingBar = (RatingBar) findViewById(R.id.restaurantRating);
        ratingBar.setRating( Float.parseFloat(restaurant.rating) );
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        //Go to check the detail of shopping cart
        goToCartView = (Button) findViewById(R.id.goToCartView);
        goToCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCart(v);
            }
        });

        //inmplement code for shopping cart
        m_cart = new Cart();
        m_response = (TextView) findViewById(R.id.totalValueOfProduct);
        LinearLayout list = (LinearLayout) findViewById(R.id.linear);


        Product products[] =
                {
                        new Product("product 1", 15.20),
                        new Product("product 2", 19.30),
                        new Product("product 3", 8.13),
                        new Product("product 4", 55.42),
                        new Product("product 5", 23.99),
                        new Product("product 6", 15.42),
                        new Product("product 7", 99.33)
                };

        for(int i = 0 ; i < products.length ; i++)
        {
            Button button = new Button(this);
            button.setText(products[i].getName() + " --- " + products[i].getValue() + " $");
            button.setTag(products[i]);

            // display
            button.setTextSize(40);

            button.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    Button button = (Button) view;
                    Product product = (Product) button.getTag();

                    m_cart.addToCart(product);
                    m_response.setText("Total cart value = " + String.format("%.2f", m_cart.getValue()) + " $");
                }
            });

            list.addView(button);
        }

    }



    //create an intent to goto detailOfShoppingCart
    public void viewCart(View v)
    {
        Intent intent = new Intent(this, detailOfShoppingCart.class);
        startActivity(intent);
    }
}

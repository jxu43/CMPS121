package com.example.jiahaoxu.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class detailOfShoppingCart extends AppCompatActivity {

    private ArrayList<Product> listOfSelectedProduct;

    private class shoppingCartAdapter extends ArrayAdapter<Product>{
        int resource;
        Context context;

        public shoppingCartAdapter(Context _context, int _resource, List<Product> items) {
            super(_context, _resource, items);
            resource = _resource;
            context = _context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout newView;

            Product w = getItem(position);

            if (convertView == null) {
                newView = new LinearLayout(getContext());
                LayoutInflater vi = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource,  newView, true);
            } else {
                newView = (LinearLayout) convertView;
            }

            TextView productName = (TextView) newView.findViewById(R.id.NameOfProduct);
            TextView productPrice = (TextView) newView.findViewById(R.id.priceOfProduct);

            productName.setText(w.productName);
            productPrice.setText(w.productValue+"");

            return newView;
        }
    }

    private shoppingCartAdapter getShoppingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_shopping_cart);
        //setContentView(R.layout.test);

        //set a new ArrayList to Store the product
        listOfSelectedProduct = new ArrayList<Product>();

        //get the new Adapter to store the value
        getShoppingList = new shoppingCartAdapter(this, R.layout.product_detail, listOfSelectedProduct);
        ListView shoppingCart = (ListView) findViewById(R.id.cart);
        shoppingCart.setAdapter(getShoppingList);
        getShoppingList.notifyDataSetChanged();

        Cart cart = detailOfRestaurant.m_cart;

        Set<Product> products = cart.getProducts();

        Iterator iterator = products.iterator();
        while(iterator.hasNext())
        {
            Product product = (Product) iterator.next();

            listOfSelectedProduct.add(new Product(
                        product.getName(), product.getValue()
                    ));
            getShoppingList.notifyDataSetChanged();
        }
    }

    public void backToRestaurant(View v){
        Intent intent = new Intent(detailOfShoppingCart.this, detailOfRestaurant.class);
        startActivity(intent);
    }

}

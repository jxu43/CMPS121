package com.example.jiahaoxu.shoppingcart;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class viewItem extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_item);
//    }
//}

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class viewItem extends AppCompatActivity {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private int screen_width;
    private ConstraintLayout layout;
    public final static String EXTRA_MESSAGE = "com.example.jiahaoxu.shoppingcart";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        getRestaurantData();
    }

    private void getRestaurantData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("restaurant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //children represents rest1 rest2
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child : children){
                    String value = child.getValue().toString();
                    Restaurant rest = new Restaurant(value);
                    restaurants.add(rest);
                }
                //显示所有的饭店
                displayAllRestaurants();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //显示所有的饭店，以一行一行的形式
    private void displayAllRestaurants(){
        TextView loading = (TextView) findViewById(R.id.loading);
        if(restaurants.size()==0){
            loading.setText("错误：没有到读取任何数据");
            return;
        }

        loading.setVisibility(View.GONE);

        //获取屏幕的界面结构
        layout = (ConstraintLayout)findViewById(R.id.mani_view);

        //获取屏幕的宽
        screen_width = layout.getWidth();

        LinearLayout[] restaurantList = new LinearLayout[restaurants.size()];
        for(int i=0; i<restaurants.size(); i++){
            restaurantList[i] = displayRestaurant(restaurants.get(i));
        }
//
//        Button[] restaurantList = new Button[restaurants.size()];
//        for(int i=0; i<restaurants.size(); i++){
//            restaurantList[i] = new Button(this);
//            restaurantList[i].setText("aaaaaaaaaa");
//        }
//
//        //设置拖动
//        int[] a = {123,544};
////        ArrayAdapter<LinearLayout> adapter = new ArrayAdapter<LinearLayout>(
//        ArrayAdapter<Button> adapter = new ArrayAdapter<Button>(
//                this,           //context for the activity
//                R.layout.activity_main, //显示的页面
////                restaurantList);          //输入的内容
//                restaurantList);
//
//
//        ListView list = (ListView) findViewById(R.id.listMain);
//        list.setAdapter(adapter);
        LinearLayout list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        for(int i=0; i<restaurants.size(); i++){
            list.addView(restaurantList[i]);
        }
        layout.addView(list);
    }

    private LinearLayout displayRestaurant(Restaurant restaurant){
        //设置结构
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        LinearLayout col = new LinearLayout(this);
        col.setOrientation(LinearLayout.HORIZONTAL);

        //添加饭店按钮
        row.addView( setRestaurantButton(restaurant) );
        //添加评价条
        col.addView( setRatingBar(restaurant) );
        //添加等待时间
        col.addView( setWaitingTime(restaurant) );

        row.addView(col);
        return row;
    }

    //设置饭店按钮
    private Button setRestaurantButton(final Restaurant restaurant){
        Button button = new Button(this);
        button.setText(restaurant.name);
        button.setWidth(screen_width);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailOfRestaurant(restaurant);
            }
        });
        return button;
    }

    //设置评价条
    private RatingBar setRatingBar(Restaurant restaurant){
        RatingBar ratingBar = new RatingBar(this);
        ratingBar.setRating( Float.parseFloat(restaurant.rating) );
        //设置为不可改变星级（不可触摸）
        ratingBar.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return true;
            }
        });
        return ratingBar;
    }

    //设置等待时间
    private TextView setWaitingTime(Restaurant restaurant){
        TextView waiting = new TextView(this);
        waiting.setText("waiting time:"+restaurant.waiting);
        return waiting;
    }

    //pass restaurant object to detailOfRestaurant
    private void goToDetailOfRestaurant(Restaurant restaurant){
        Intent intent = new Intent(this, detailOfRestaurant.class);
        // message = restaurant.toString();
        intent.putExtra(EXTRA_MESSAGE, restaurant);
        startActivity(intent);
    }

}
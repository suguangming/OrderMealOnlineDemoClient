package com.example.mingw.restaurant.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mingw.restaurant.FoodCart;
import com.example.mingw.restaurant.R;
import com.example.mingw.restaurant.utils.DatabaseUtil;


public class DetailActivity extends AppCompatActivity {

    public static final String FOOD_IMAGE_URL = "food_image_id";
    public static final String FOOD_NAME = "food_name";
    public static final String FOOD_PRICE = "food_price";
    public static final String FOOD_CONTENT = "food_content";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.tb_detail_toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        // 获取PreferenceManager
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String serverIP = pref.getString("server_url", "");
        String imageServer = "http://" + serverIP + ":8080/food/img/";
        final String foodName = intent.getStringExtra(FOOD_NAME);
        String foodContent = intent.getStringExtra(FOOD_CONTENT);
        final String foodPrice = intent.getStringExtra(FOOD_PRICE);
        final String foodImageUrl = imageServer + intent.getStringExtra(FOOD_IMAGE_URL);
        FloatingActionButton fabAddToCart = findViewById(R.id.fab_detail_add_to_cart);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(
            R.id.ctb_detail_collapsing_toolbar);
        ImageView imageViewFoodImg = findViewById(R.id.iv_detail_food_image);
        TextView textViewFoodContent = findViewById(R.id.tv_detail_food_content_text);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(foodName);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Glide.with(this).load(foodImageUrl).into(imageViewFoodImg);
        textViewFoodContent.setText(foodContent);
        fabAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                FoodCart fc = new FoodCart();
                fc.setFoodname(foodName);
                fc.setFoodnumber(1);
                fc.setImgUrl(foodImageUrl);
                fc.setFoodprice(Double.parseDouble(foodPrice));
                fc.setStatus("未提交");
                DatabaseUtil.addToDatabase(fc);
                Snackbar.make(v, "已经添加到购物车", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

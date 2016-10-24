package com.noni.Orderise;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {


    public final String TAG = OrderDetails.class.getSimpleName();
    private Button editButton;
    private ListView coffeeOrderList;
    private ArrayAdapter<String> coffeeOrderAdapter;
    private ArrayList<String> coffeeOrders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar ab = this.getSupportActionBar();
            if (ab != null) {
                ab.hide();
            }
        }

        setContentView(R.layout.activity_order_details);
        coffeeOrderList = (ListView) findViewById(R.id.order_list);
        editButton = (Button) findViewById(R.id.editButton);

        editButton.setOnClickListener(this);

        Intent orderDetails = getIntent();
        HashMap<String, String> valuesMap = new HashMap<>();
        coffeeOrders = (ArrayList<String>) orderDetails.getSerializableExtra("orderList");
        coffeeOrderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coffeeOrders);
        coffeeOrderList.setAdapter(coffeeOrderAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent backToMainActivity = new Intent(this, Orderise.class);
        backToMainActivity.putExtra("orderList", coffeeOrders);
        startActivity(backToMainActivity);
        finish();
    }
}

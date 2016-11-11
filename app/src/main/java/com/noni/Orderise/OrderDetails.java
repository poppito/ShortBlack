package com.noni.Orderise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {


    public final String TAG = OrderDetails.class.getSimpleName();
    private Button editButton, newOrderButton;
    private ListView coffeeOrderList;
    private CoffeeOrderAdapter coffeeOrderAdapter;
    private ArrayList<CoffeeOrder> coffeeOrders;
    private SharedPreferences mPreferences;

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
        newOrderButton = (Button) findViewById(R.id.newOrderButton);

        editButton.setOnClickListener(this);
        newOrderButton.setOnClickListener(this);

        Intent orderDetails = getIntent();
        coffeeOrders = (ArrayList<CoffeeOrder>) orderDetails.getSerializableExtra("coffeeOrders");
        coffeeOrderAdapter = new CoffeeOrderAdapter(this.getApplicationContext() , coffeeOrders);
        coffeeOrderList.setAdapter(coffeeOrderAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.newOrderButton): {
                Intent backToMainActivity = new Intent(this, Orderise.class);
                startActivity(backToMainActivity);
                finish();
                break;
            }
            case (R.id.editButton): {
                FileOperations fops = new FileOperations();
                fops.writeToFile(this,coffeeOrders, mPreferences);
                break;
            }
        }
    }
}

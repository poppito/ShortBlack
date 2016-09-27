package com.noni.ShortBlack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {


    public final String TAG = OrderDetails.class.getSimpleName();
    private TextView nameGenerated;
    private TextView coffeeOrder;
    private Button editButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        nameGenerated = (TextView) findViewById(R.id.nameGenerated);
        coffeeOrder = (TextView) findViewById(R.id.coffeeOrder);
        editButton = (Button) findViewById(R.id.editButton);

        editButton.setOnClickListener(this);

        Intent orderDetails = getIntent();
        HashMap<String, String> valuesMap = new HashMap<>();
        valuesMap = (HashMap<String,String>) orderDetails.getSerializableExtra("orderDetails");
        nameGenerated.setText("G'day the name for your coffee order is " + orderDetails.getStringExtra("nameGenerated"));
        coffeeOrder.setText("Your coffee order is of a " + valuesMap.get((String) "orderSize") + " " + valuesMap.get((String) "coffeeType") + " with " + valuesMap.get((String) "milkChoice") + " milk and " + valuesMap.get((String) "additiveChoice") + ".");

    }

    @Override
    public void onClick(View v) {
        Intent backToMainActivity = new Intent(this, ShortBlack.class);
        startActivity(backToMainActivity);

    }
}

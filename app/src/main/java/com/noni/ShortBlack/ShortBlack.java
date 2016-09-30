package com.noni.ShortBlack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import static com.noni.ShortBlack.R.id.milkChoicesSpinner;

public class ShortBlack extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {

    private Button submitButton;
    private EditText nameText;
    private String TAG = ShortBlack.class.getSimpleName();
    private HashMap<String, String> valuesMap = new HashMap<>();
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        setContentView(R.layout.activity_short_black);
        submitButton = (Button) findViewById(R.id.submitButton);
        nameText = (EditText) findViewById(R.id.Name);


        Spinner mAdditiveChoicesSpinner = (Spinner) findViewById(R.id.additiveChoicesSpinner);
        Spinner mOrderSizesSpinner = (Spinner) findViewById(R.id.orderSizesSpinner);
        Spinner mCoffeeTypeSpinner = (Spinner) findViewById(R.id.coffeeTypeSpinner);
        Spinner mMilkChoicesSpinner = (Spinner) findViewById(milkChoicesSpinner);


        ArrayAdapter<CharSequence> mAdditiveAdapter = ArrayAdapter.createFromResource(this, R.array.additiveArray, android.R.layout.simple_spinner_item);
        mAdditiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdditiveChoicesSpinner.setAdapter(mAdditiveAdapter);

        ArrayAdapter<CharSequence> mOrderSizeAdapter = ArrayAdapter.createFromResource(this, R.array.orderSizesArray, android.R.layout.simple_spinner_item);
        mOrderSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOrderSizesSpinner.setAdapter(mOrderSizeAdapter);

        ArrayAdapter<CharSequence> mCoffeeTypeAdapter = ArrayAdapter.createFromResource(this, R.array.cofeeTypeArray, android.R.layout.simple_spinner_item);
        mCoffeeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCoffeeTypeSpinner.setAdapter(mCoffeeTypeAdapter);

        ArrayAdapter<CharSequence> mMilkChoicesAdapter = ArrayAdapter.createFromResource(this, R.array.milkChoicesArray, android.R.layout.simple_spinner_item);
        mMilkChoicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMilkChoicesSpinner.setAdapter(mMilkChoicesAdapter);


        //initialise listeners for every single spinner
        mAdditiveChoicesSpinner.setOnItemSelectedListener(this);
        mCoffeeTypeSpinner.setOnItemSelectedListener(this);
        mMilkChoicesSpinner.setOnItemSelectedListener(this);
        mOrderSizesSpinner.setOnItemSelectedListener(this);

        //Initialise submit button
        submitButton.setOnClickListener(this);
        // submitButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_inactive));
        submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
        nameText.addTextChangedListener(this);
        GenerateName g = new GenerateName();
        name = g.GenerateName();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case milkChoicesSpinner: {
                if (position != 0) {
                    valuesMap.put("milkChoice", parent.getItemAtPosition(position).toString());
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                } else {
                    if (valuesMap.containsKey("milkChoice")) {
                        valuesMap.remove("milkChoice");
                    }
                }
                break;
            }

            case R.id.additiveChoicesSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    valuesMap.put("additiveChoice", parent.getItemAtPosition(position).toString());
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                } else {
                    if (valuesMap.containsKey("additiveChoice")) {
                        valuesMap.remove("additiveChoice");
                    }
                }

                break;
            }

            case R.id.coffeeTypeSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    valuesMap.put("coffeeType", parent.getItemAtPosition(position).toString());
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                } else {
                    if (valuesMap.containsKey("coffeeType")) {
                        valuesMap.remove("coffeeType");
                    }
                }
                break;
            }

            case R.id.orderSizesSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    valuesMap.put("orderSize", parent.getItemAtPosition(position).toString());
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                } else {
                    if (valuesMap.containsKey("orderSize")) {
                        valuesMap.remove("orderSize");
                    }
                }
                break;
            }
        }
        enableButton();
    }

    public boolean allValuesValidated() {
        if (valuesMap.containsKey("milkChoice") && (valuesMap.containsKey("additiveChoice")) && (valuesMap.containsKey("coffeeType") && valuesMap.containsKey("orderSize"))) {
            return true;
        }
        return false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Log.v(TAG, "nothing selected");

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() > 0) {
            name = s.toString();
        }
    }

    public void enableButton() {
        if (allValuesValidated()) {

            //submitButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_active));
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_active));
        } else {
            //submitButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_inactive));
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submitButton: {
                if (allValuesValidated()) {
                    Log.v(TAG, "name is " + name);
                    Intent fireUpOrderDetails = new Intent(this, OrderDetails.class);
                    fireUpOrderDetails.putExtra("orderDetails", valuesMap);
                    fireUpOrderDetails.putExtra("nameGenerated", name);
                    startActivity(fireUpOrderDetails);
                } else if (!valuesMap.containsKey("milkChoice")) {
                    Toast.makeText(getApplicationContext(), "Please select a choice for milk type!", Toast.LENGTH_SHORT).show();

                } else if (!valuesMap.containsKey("additiveChoice")) {
                    Toast.makeText(getApplicationContext(), "Please select an additive choice your order!", Toast.LENGTH_SHORT).show();
                } else if (!valuesMap.containsKey("coffeeType")) {
                    Toast.makeText(getApplicationContext(), "Please select type of coffee for your order!", Toast.LENGTH_SHORT).show();
                } else if (!valuesMap.containsKey("orderSize")) {
                    Toast.makeText(getApplicationContext(), "Please select an order size!", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "no order size selected");
                }
            }
            break;
        }
    }

}

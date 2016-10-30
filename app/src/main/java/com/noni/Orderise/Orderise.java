package com.noni.Orderise;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;
import static com.noni.Orderise.R.id.milkChoicesSpinner;

public class Orderise extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher, View.OnFocusChangeListener {

    private Button submitButton, saveButton;
    private EditText nameText, special_orders;
    private String TAG = Orderise.class.getSimpleName();
    private HashMap<String, String> valuesMap = new HashMap<>();
    private String name;
    private TextView statusText, titleText;
    private ImageButton closeKeyboard, close_special_orders;
    private InputMethodManager imm;
    private ArrayList<String> coffeeOrders;
    private Spinner mAdditiveChoicesSpinner, mMilkChoicesSpinner, mOrderSizesSpinner, mCoffeeTypeSpinner, mCoffeeStrengthSpinner;
    private static final String milkChoice = "milkChoice";
    private static final String orderSize = "orderSize";
    private static final String coffeeType = "coffeeType";
    private static final String additiveCoice = "additiveChoice";
    private static final String coffeeStrength = "strength";

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
        saveButton = (Button) findViewById(R.id.saveForLater);
        nameText = (EditText) findViewById(R.id.name);
        special_orders = (EditText) findViewById(R.id.special_instructions);
        titleText = (TextView) findViewById(R.id.title_message);
        statusText = (TextView) findViewById(R.id.status_message);
        close_special_orders = (ImageButton) findViewById(R.id.close_special_orders);
        closeKeyboard = (ImageButton) findViewById(R.id.closeKeyboard);


        mAdditiveChoicesSpinner = (Spinner) findViewById(R.id.additiveChoicesSpinner);
        mOrderSizesSpinner = (Spinner) findViewById(R.id.orderSizesSpinner);
        mCoffeeTypeSpinner = (Spinner) findViewById(R.id.coffeeTypeSpinner);
        mMilkChoicesSpinner = (Spinner) findViewById(R.id.milkChoicesSpinner);
        mCoffeeStrengthSpinner = (Spinner) findViewById(R.id.coffeeStrength);


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

        ArrayAdapter<CharSequence> mCoffeeStrengthAdapter = ArrayAdapter.createFromResource(this, R.array.coffeeStrengthArray, android.R.layout.simple_spinner_item);
        mCoffeeStrengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCoffeeStrengthSpinner.setAdapter(mCoffeeStrengthAdapter);

        //initialise listeners for every single spinner
        mAdditiveChoicesSpinner.setOnItemSelectedListener(this);
        mCoffeeTypeSpinner.setOnItemSelectedListener(this);
        mMilkChoicesSpinner.setOnItemSelectedListener(this);
        mOrderSizesSpinner.setOnItemSelectedListener(this);
        mCoffeeStrengthSpinner.setOnItemSelectedListener(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Initialise submit button
        submitButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
        nameText.addTextChangedListener(this);
        nameText.setOnClickListener(this);
        special_orders.setOnClickListener(this);
        nameText.setOnFocusChangeListener(this);
        special_orders.setOnFocusChangeListener(this);
        closeKeyboard.setVisibility(GONE);
        closeKeyboard.setOnClickListener(this);
        coffeeOrders = new ArrayList<>();
        close_special_orders.setOnClickListener(this);
        GenerateName g = new GenerateName();
        name = g.GenerateName();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case milkChoicesSpinner: {
                if (position != 0) {
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                    valuesMap.put(milkChoice, parent.getItemAtPosition(position).toString());
                }
                break;
            }

            case R.id.additiveChoicesSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                    valuesMap.put(additiveCoice, parent.getItemAtPosition(position).toString());
                }
                break;
            }

            case R.id.coffeeTypeSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                    valuesMap.put(coffeeType, parent.getItemAtPosition(position).toString());

                }
                break;
            }

            case R.id.orderSizesSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                    valuesMap.put(orderSize, parent.getItemAtPosition(position).toString());
                }
                break;
            }
            case R.id.coffeeStrength: {
                if (position != 0) {
                    Log.v(TAG, parent.getItemAtPosition(position).toString());
                    valuesMap.put(coffeeStrength, parent.getItemAtPosition(position).toString());
                }
                break;
            }
        }
        enableSaveButton();
        enableSubmitButton();
    }

    public boolean canSubmitOrder() {
        if (coffeeOrders.size() >= 1 && (!allValuesValidated(valuesMap))) {
            return true;
        } else if (allValuesValidated(valuesMap)) {
            validateGeneratedName();
            coffeeOrders.add(valuesMap.get(orderSize) + " " + (valuesMap.get(coffeeStrength)) + " " + (valuesMap.get(coffeeType)) + " with " + valuesMap.get(milkChoice) + " and " + valuesMap.get(additiveCoice) + " for " + name);
            name = "";
            return true;
        }
        return false;
    }

    public boolean allValuesValidated(HashMap<String, String> map) {
        if ((map.get(coffeeType) != null) && (map.get(orderSize) != null) && (map.get(additiveCoice) != null) && (map.get(milkChoice) != null) && (map.get(coffeeStrength) != null)) {
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


    public void enableSubmitButton() {
        if ((coffeeOrders.size() >= 1)) {
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_active));
        } else if (allValuesValidated(valuesMap)) {
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_active));
            statusText.setText("Order looks A-OK! :)");
        } else {
            //submitButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_inactive));
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
        }
    }

    public void enableSaveButton() {
        if (allValuesValidated(valuesMap)) {
            saveButton.setBackgroundColor(getResources().getColor(R.color.button_active));
            statusText.setText("Order looks A-OK! :)");
        } else {
            //submitButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_inactive));
            saveButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));

        }
    }

    public void resetSpinners() {
        mAdditiveChoicesSpinner.setSelection(0);
        mCoffeeTypeSpinner.setSelection(0);
        mMilkChoicesSpinner.setSelection(0);
        mOrderSizesSpinner.setSelection(0);
        mCoffeeStrengthSpinner.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton: {
                if (canSubmitOrder()) {
                    Intent fireUpOrderDetails = new Intent(this, OrderDetails.class);
                    fireUpOrderDetails.putExtra("orderList", coffeeOrders);
                    startActivity(fireUpOrderDetails);
                    statusText.setText("");
                    finish();
                } else {
                    notifyIncompleteOrder(valuesMap, statusText);
                }
            }
            case R.id.closeKeyboard: {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                closeKeyboard.setVisibility(GONE);
                break;
            }
            case R.id.name: {
                imm.showSoftInput(nameText, InputMethodManager.SHOW_IMPLICIT);
                closeKeyboard.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.saveForLater: {
                if (allValuesValidated(valuesMap)) {
                    refreshListView(valuesMap);
                    statusText.setText("");
                    resetSpinners();
                    valuesMap.clear();
                } else {
                    notifyIncompleteOrder(valuesMap, statusText);
                }
                break;
            }
            case R.id.special_instructions: {
                imm.showSoftInput(special_orders, InputMethodManager.SHOW_IMPLICIT);
                close_special_orders.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.close_special_orders: {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                close_special_orders.setVisibility(GONE);
                break;
            }
        }
    }


    public void notifyIncompleteOrder(HashMap<String, String> map, TextView statusText) {
        if (map.get(milkChoice) == null) {
            statusText.setText("What kinda milk? :)");
            statusText.setTextColor(getResources().getColor(R.color.textColor));
        } else if (map.get(additiveCoice) == null) {
            statusText.setText("Any sweetners? :)");
            statusText.setTextColor(getResources().getColor(R.color.textColor));
        } else if (map.get(coffeeType) == null) {
            statusText.setText("What type of coffee? :)");
            statusText.setTextColor(getResources().getColor(R.color.textColor));
        } else if (map.get(orderSize) == null) {
            statusText.setText("How about an order size? :)");
            statusText.setTextColor(getResources().getColor(R.color.textColor));
        } else if (map.get(coffeeStrength) == null) {
            statusText.setText("How about coffee strength? :)");
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (nameText.hasFocus()) {
            imm.showSoftInput(nameText, InputMethodManager.SHOW_IMPLICIT);
            closeKeyboard.setVisibility(View.VISIBLE);
        } else if (special_orders.hasFocus()) {
            imm.showSoftInput(nameText, InputMethodManager.SHOW_IMPLICIT);
            close_special_orders.setVisibility(View.VISIBLE);
        }
    }

    public void refreshListView(HashMap<String, String> map) {
        validateGeneratedName();
        coffeeOrders.add(valuesMap.get(orderSize) + " " + (valuesMap.get(coffeeStrength)) + " " + (valuesMap.get(coffeeType)) + " with " + valuesMap.get(milkChoice) + " and " + valuesMap.get(additiveCoice) + " for " + name);
        name = "";
        final Snackbar sb = Snackbar.make(findViewById(android.R.id.content), "added a " + coffeeOrders.get(coffeeOrders.size() - 1), Snackbar.LENGTH_LONG);
        sb.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.dismiss();
                coffeeOrders.remove(coffeeOrders.get(coffeeOrders.size() - 1));
            }
        });
        sb.show();
    }

    public void validateGeneratedName() {
        if ((name == null) || (name.equals(""))) {
            GenerateName g = new GenerateName();
            name = g.GenerateName();
        }
    }
}
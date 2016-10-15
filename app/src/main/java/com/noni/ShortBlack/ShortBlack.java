package com.noni.ShortBlack;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;
import static com.noni.ShortBlack.R.id.milkChoicesSpinner;

public class ShortBlack extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher, View.OnFocusChangeListener{

    private Button submitButton, saveButton;
    private EditText nameText;
    private String TAG = ShortBlack.class.getSimpleName();
    private HashMap<String, String> valuesMap = new HashMap<>();
    private String name;
    private TextView statusText, titleText;
    private ImageButton closeKeyboard;
    private InputMethodManager imm;
    private ListView coffeeOrderList;
    private ArrayList<String> coffeeOrders;
    private ArrayAdapter<String> coffeeOrderAdapter;
    private Spinner mAdditiveChoicesSpinner, mMilkChoicesSpinner, mOrderSizesSpinner, mCoffeeTypeSpinner;
    private static final String milkChoice = "milkChoice";
    private static final String orderSize = "orderSize";
    private static final String coffeeType = "coffeeType";
    private static final String additiveCoice = "additiveChoice";
    private OnClickListener mOnClickListener;

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
        titleText = (TextView) findViewById(R.id.title_message);
        statusText = (TextView) findViewById(R.id.status_message);
        coffeeOrderList = (ListView) findViewById(R.id.order_list);


        mAdditiveChoicesSpinner = (Spinner) findViewById(R.id.additiveChoicesSpinner);
        mOrderSizesSpinner = (Spinner) findViewById(R.id.orderSizesSpinner);
        mCoffeeTypeSpinner = (Spinner) findViewById(R.id.coffeeTypeSpinner);
        mMilkChoicesSpinner = (Spinner) findViewById(milkChoicesSpinner);


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
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Initialise submit button
        submitButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
        nameText.addTextChangedListener(this);
        nameText.setOnClickListener(this);
        nameText.setOnFocusChangeListener(this);
        closeKeyboard = (ImageButton) findViewById(R.id.closeKeyboard);
        closeKeyboard.setVisibility(GONE);
        closeKeyboard.setOnClickListener(this);
        GenerateName g = new GenerateName();
        name = g.GenerateName();
        coffeeOrders = new ArrayList<>();
        coffeeOrderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coffeeOrders);
        coffeeOrderList.setAdapter(coffeeOrderAdapter);
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
        }
        enableButtons();
    }

    public boolean allValuesValidated(HashMap<String, String> map) {
        if ((map.get(coffeeType) != null) && (map.get(orderSize) != null) && (map.get(additiveCoice) != null) && (map.get(milkChoice) != null)) {
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


    public void enableButtons() {
        if (allValuesValidated(valuesMap)) {
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_active));
            saveButton.setBackgroundColor(getResources().getColor(R.color.button_active));
            statusText.setText("Order looks A-OK! :)");
        } else {
            //submitButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_inactive));
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
            saveButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));

        }
    }

    public void resetSpinners() {
        mAdditiveChoicesSpinner.setSelection(0);
        mCoffeeTypeSpinner.setSelection(0);
        mMilkChoicesSpinner.setSelection(0);
        mOrderSizesSpinner.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton: {
                if (allValuesValidated(valuesMap)) {
                    Intent fireUpOrderDetails = new Intent(this, OrderDetails.class);
                    fireUpOrderDetails.putExtra("orderDetails", valuesMap);
                    fireUpOrderDetails.putExtra("nameGenerated", name);
                    startActivity(fireUpOrderDetails);
                    statusText.setText("");
                } else {
                    notifyIncompleteOrder(valuesMap, statusText);
                }
            }
            case R.id.closeKeyboard: {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                closeKeyboard.setVisibility(GONE);
                break;
            }
            case R.id.name:  {
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
            }
        }
    }


    public void notifyIncompleteOrder(HashMap<String, String> map, TextView statusText) {
        if (map.get(milkChoice) == null) {
            statusText.setText("What kinda milk? :)");
            statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
        } else if (map.get(additiveCoice) == null) {
            statusText.setText("Any sweetners? :)");
            statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
        } else if (map.get(coffeeType)== null) {
            statusText.setText("What type of coffee? :)");
            statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
        } else if (map.get(orderSize) == null) {
            statusText.setText("How about an order size? :)");
            statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (nameText.hasFocus()) {
            imm.showSoftInput(nameText, InputMethodManager.SHOW_IMPLICIT);
            closeKeyboard.setVisibility(View.VISIBLE);
        }
    }

    public void refreshListView(HashMap <String, String> map) {
        coffeeOrders.add(map.get(orderSize) + "added " + (map.get(coffeeType)) + " with " + map.get(milkChoice) + " and " + map.get(additiveCoice));

        final Snackbar sb = Snackbar.make(findViewById(android.R.id.content), coffeeOrders.get(coffeeOrders.size() - 1), Snackbar.LENGTH_LONG);
        sb.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.dismiss();
            }
        });
        sb.show();
        coffeeOrderAdapter.notifyDataSetChanged();
    }
}

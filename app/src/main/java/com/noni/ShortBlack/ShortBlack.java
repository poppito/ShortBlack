package com.noni.ShortBlack;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

import static android.view.View.GONE;
import static com.noni.ShortBlack.R.id.milkChoicesSpinner;

public class ShortBlack extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher, View.OnFocusChangeListener{

    private Button submitButton, saveButton;
    private EditText nameText;
    private String TAG = ShortBlack.class.getSimpleName();
    private HashMap<String, String> valuesMap = new HashMap<>();
    private String name;
    private TextView statusText, titleText;
    private ScrollView shortBlackMainScrollView;
    private ImageButton closeKeyboard;
    private InputMethodManager imm;


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
        shortBlackMainScrollView = (ScrollView) findViewById(R.id.shortblack_scrollview_main);
        submitButton = (Button) findViewById(R.id.submitButton);
        saveButton = (Button) findViewById(R.id.saveForLater);
        nameText = (EditText) findViewById(R.id.name);
        titleText = (TextView) findViewById(R.id.title_message);
        statusText = (TextView) findViewById(R.id.status_message);

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
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Initialise submit button
        submitButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        shortBlackMainScrollView.setOnClickListener(this);
        submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
        nameText.addTextChangedListener(this);
        nameText.setOnClickListener(this);
        nameText.setOnFocusChangeListener(this);
        closeKeyboard = (ImageButton) findViewById(R.id.closeKeyboard);
        closeKeyboard.setVisibility(GONE);
        closeKeyboard.setOnClickListener(this);
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
        enableButtons();
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

    public void enableButtons() {
        if (allValuesValidated()) {
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_active));
            saveButton.setBackgroundColor(getResources().getColor(R.color.button_active));
            statusText.setText("Order looks A-OK! :)");
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
                    //Toast.makeText(getApplicationContext(), "What kinda milk? :)", Toast.LENGTH_SHORT).show();
                    statusText.setText("What kinda milk? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                } else if (!valuesMap.containsKey("additiveChoice")) {
                    //Toast.makeText(getApplicationContext(), "Please select an additive choice your order!", Toast.LENGTH_SHORT).show();
                    statusText.setText("Any sweetners? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                } else if (!valuesMap.containsKey("coffeeType")) {
                    //Toast.makeText(getApplicationContext(), "Please select type of coffee for your order!", Toast.LENGTH_SHORT).show();
                    statusText.setText("What type of coffee? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                } else if (!valuesMap.containsKey("orderSize")) {
                    //Toast.makeText(getApplicationContext(), "Please select an order size!", Toast.LENGTH_SHORT).show();
                    statusText.setText("How about an order size? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                    Log.v(TAG, "no order size selected");
                }
                break;
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
                if (allValuesValidated()) {
                    Log.v(TAG, "name is " + name);
                    FileOperations fileOps = new FileOperations(getApplicationContext(), valuesMap);
                    fileOps.readFromFile(getApplication());
                } else if (!valuesMap.containsKey("milkChoice")) {
                    //Toast.makeText(getApplicationContext(), "What kinda milk? :)", Toast.LENGTH_SHORT).show();
                    statusText.setText("What kinda milk? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                } else if (!valuesMap.containsKey("additiveChoice")) {
                    //Toast.makeText(getApplicationContext(), "Please select an additive choice your order!", Toast.LENGTH_SHORT).show();
                    statusText.setText("Any sweetners? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                } else if (!valuesMap.containsKey("coffeeType")) {
                    //Toast.makeText(getApplicationContext(), "Please select type of coffee for your order!", Toast.LENGTH_SHORT).show();
                    statusText.setText("What type of coffee? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                } else if (!valuesMap.containsKey("orderSize")) {
                    //Toast.makeText(getApplicationContext(), "Please select an order size!", Toast.LENGTH_SHORT).show();
                    statusText.setText("How about an order size? :)");
                    statusText.setTextColor(getResources().getColor(R.color.warning_text_colour));
                    Log.v(TAG, "no order size selected");
                }
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (nameText.hasFocus()) {
            imm.showSoftInput(nameText, InputMethodManager.SHOW_IMPLICIT);
            closeKeyboard.setVisibility(View.VISIBLE);
        }
    }
}

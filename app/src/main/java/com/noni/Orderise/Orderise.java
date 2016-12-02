package com.noni.Orderise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.Serializable;
import java.util.ArrayList;

import static android.view.View.GONE;
import static com.noni.Orderise.R.id.milkChoicesSpinner;

public class Orderise extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, TextWatcher {

    private Button submitButton, saveButton;
    private EditText nameText, special_orders;
    private String TAG = Orderise.class.getSimpleName();
    private String name;
    private TextView statusText, titleText;
    private ImageButton closeKeyboard, close_special_orders;
    private InputMethodManager imm;
    private ArrayListRefreshable<CoffeeOrder> coffeeOrders = new ArrayListRefreshable<>();
    private Spinner mAdditiveChoicesSpinner, mMilkChoicesSpinner, mOrderSizesSpinner, mCoffeeTypeSpinner, mCoffeeStrengthSpinner;
    public CoffeeOrder currentOrder;

    private ArrayListRefreshable<CoffeeOrder> createFromArrayList(ArrayList<CoffeeOrder> list) {
        ArrayListRefreshable<CoffeeOrder> newList = new ArrayListRefreshable<>();
        for (CoffeeOrder order : list) {
            newList.add(order);
        }
        return newList;
    }

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

        coffeeOrders = new ArrayListRefreshable<>();
        Intent orderDetails = getIntent();
        //check whether we're starting a fresh activity or being called back to add more stuff.
        if (orderDetails.getSerializableExtra("coffeeOrders") == null) {
            promptUserIfPrevSavedOrderExists();
            coffeeOrders = new ArrayListRefreshable<CoffeeOrder>();
        } else {
            coffeeOrders = createFromArrayList((ArrayList<CoffeeOrder>) orderDetails.getSerializableExtra("coffeeOrders"));
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

        currentOrder = new CoffeeOrder();

        //Initialise submit button
        submitButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        submitButton.setBackgroundColor(getResources().getColor(R.color.button_inactive));
        nameText.addTextChangedListener(this);
        special_orders.addTextChangedListener(this);
        nameText.setOnClickListener(this);
        special_orders.setOnClickListener(this);
        nameText.setOnFocusChangeListener(this);
        special_orders.setOnFocusChangeListener(this);
        closeKeyboard.setVisibility(GONE);
        closeKeyboard.setOnClickListener(this);
        close_special_orders.setOnClickListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case milkChoicesSpinner: {
                if (position != 0) {
                    currentOrder.setMilkChoice(parent.getItemAtPosition(position).toString());
                } else {
                    currentOrder.setMilkChoice(null);
                }
                break;
            }

            case R.id.additiveChoicesSpinner: {
                if (position != 0) {
                    currentOrder.setAdditiveChoice(parent.getItemAtPosition(position).toString());
                } else {
                    currentOrder.setAdditiveChoice(null);
                }
                break;
            }

            case R.id.coffeeTypeSpinner: {
                if (position != 0) {
                    currentOrder.setCoffeeType(parent.getItemAtPosition(position).toString());
                } else {
                    currentOrder.setCoffeeType(null);
                }
                break;
            }

            case R.id.orderSizesSpinner: {
                if (position != 0) {
                    currentOrder.setOrderSize(parent.getItemAtPosition(position).toString());
                } else {
                    currentOrder.setOrderSize(null);
                }
                break;
            }
            case R.id.coffeeStrength: {
                if (position != 0) {
                    currentOrder.setCoffeeStrength(parent.getItemAtPosition(position).toString());
                } else {
                    currentOrder.setCoffeeStrength(null);
                }
                break;
            }
        }
        enableSaveButton();
        enableSubmitButton();
        statusText.setText(currentOrder.checkForOrderCompletion(this.getApplicationContext()));
    }

    public boolean canSubmitOrder() {
        if (coffeeOrders.size() >= 1 && (!currentOrder.allValuesValidated())) {
            return true;
        } else if (currentOrder.allValuesValidated()) {
            resetValues();
            return true;
        }
        return false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Log.v(TAG, "nothing selected");

    }


    public void enableSubmitButton() {
        if (coffeeOrders.size() >= 1) {
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_active));
        } else if (currentOrder.allValuesValidated()) {
            submitButton.setBackgroundColor(getResources().getColor(R.color.button_active));
        } else {
            disableButtons(submitButton);
        }
    }

    public void enableSaveButton() {
        if (currentOrder.allValuesValidated()) {
            saveButton.setBackgroundColor(getResources().getColor(R.color.button_active));
        } else {
            disableButtons(saveButton);
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
                    writeOrdersToFile();
                    Intent fireUpOrderDetails = new Intent(this, OrderDetails.class);
                    fireUpOrderDetails.putExtra("coffeeOrders", (Serializable) coffeeOrders);
                    startActivity(fireUpOrderDetails);
                    finish();
                } else {
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
                if (currentOrder.allValuesValidated()) {
                    refreshListView(currentOrder);
                    resetValues();
                    currentOrder = new CoffeeOrder();
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

    public void refreshListView(CoffeeOrder currentOrder) {
        resetValues();
        final Snackbar sb = Snackbar.make(findViewById(android.R.id.content), currentOrder.displayOrder(), Snackbar.LENGTH_LONG);
        sb.show();
    }

    public void validateGeneratedName() {
        if ((name == null) || (name.equals(""))) {
            GenerateName g = new GenerateName();
            name = g.GenerateName();
            currentOrder.setOrderName(name);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.equals(nameText.getEditableText())) {
            if (s.length() > 0) {
                name = s.toString();
                currentOrder.setOrderName(name);
            }
        } else if (s.equals(special_orders.getEditableText())) {
            if (s.length() > 0) {
                currentOrder.setSpecialOrder("Special instructions: " + s.toString());
            }
        }

    }

    public void disableButtons(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.button_inactive));
    }

    public void resetValues() {
        if ((currentOrder.displayOrder() != null) && (!currentOrder.displayOrder().equals(""))) {
            coffeeOrders.add(currentOrder);
        }
        validateGeneratedName();
        currentOrder = new CoffeeOrder();
        name = "";
        resetSpinners();
        special_orders.setText("");
        statusText.setText("");
        nameText.setText("");
    }

    private void promptUserIfPrevSavedOrderExists() {
        SharedPreferences mPreferences = getSharedPreferences("savedPreferences", Context.MODE_PRIVATE);

        int defaultValue = 99999999;

        int orderSize = mPreferences.getInt("orderSize", defaultValue);

        Log.v("SomeTag", String.valueOf(orderSize) + " is ordersize");

        if (orderSize != defaultValue) {
            FileOperations readFiles = new FileOperations();
            OrderiseDialogPrompt prompt = new OrderiseDialogPrompt(getResources().getString(R.string.orderisePromptTitle),
                    getResources().getString(R.string.orderisePromptMessage), getResources().getString(R.string.orderisePromptPositiveButton),
                    getResources().getString(R.string.orderisePromptNegativeButton), this, readFiles.readFromFile(this, orderSize));
            prompt.show(getFragmentManager(), "PromptToLoadExisting");
        }
    }

    private void writeOrdersToFile() {
        FileOperations writeOrders = new FileOperations();
        writeOrders.writeToFile(this,coffeeOrders, this.getApplicationContext().getSharedPreferences("savedPreferences", Context.MODE_PRIVATE));
    }
}
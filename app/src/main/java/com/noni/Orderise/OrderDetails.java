package com.noni.Orderise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import static android.view.View.GONE;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {


    public final String TAG = OrderDetails.class.getSimpleName();
    private Button editButton, newOrderButton, mDeleteButton, mAddMoreButton;
    private ListView coffeeOrderList;
    private CoffeeOrderAdapter coffeeOrderAdapter;
    private ArrayListRefreshable<CoffeeOrder> coffeeOrders;
    public ArrayListRefreshListener mListener, mListenerHere;
    private boolean showCheckBoxes = false;
    private Context context;
    public ArrayListRefreshable<CoffeeOrder> selectedList = new ArrayListRefreshable<CoffeeOrder>();


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
        editButton = (Button) findViewById(R.id.button_edit_order);
        newOrderButton = (Button) findViewById(R.id.button_new_order);
        mDeleteButton = (Button) findViewById(R.id.button_delete_items);
        mAddMoreButton = (Button) findViewById(R.id.button_add_more_items);

        editButton.setOnClickListener(this);
        newOrderButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
        mAddMoreButton.setOnClickListener(this);

        context = this;
        Intent orderDetails = getIntent();
        ArrayList<CoffeeOrder> orderArrayList = (ArrayList<CoffeeOrder>) orderDetails.getSerializableExtra("coffeeOrders");
        coffeeOrders = createFromArrayList(orderArrayList);
        coffeeOrderAdapter = new CoffeeOrderAdapter(context, coffeeOrders, showCheckBoxes, selectedList, mListener);
        coffeeOrderList.setAdapter(coffeeOrderAdapter);
        mListener = new ArrayListRefreshListener() {
            @Override
            public void onRefresh() {
                coffeeOrderAdapter.notifyDataSetChanged();
                selectedList = coffeeOrderAdapter.getSelectionList();
                Log.v("SomeTag", String.valueOf(selectedList.size()));
            }
        };
        selectedList.setArrayListRefreshListener(mListener);
        mListenerHere = new ArrayListRefreshListener() {
            @Override
            public void onRefresh() {
               final Intent backToMainActivity = new Intent(getApplicationContext(), Orderise.class);
                if (coffeeOrders.size() <= 0) {
                    final Snackbar sb = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.nothingToSeeHere), Snackbar.LENGTH_LONG);
                    sb.show();
                    mDeleteButton.setVisibility(GONE);
                    editButton.setVisibility(GONE);
                    mAddMoreButton.setVisibility(GONE);
                    newOrderButton.setVisibility(GONE);
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deleteAllOrders();
                            startActivity(backToMainActivity);
                            finish();
                        }
                    }, 3000);
                }
            }
        };
        coffeeOrders.setArrayListRefreshListener(mListenerHere);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.button_new_order): {

                OrderiseDialogFragment promptForDeleteAll = new OrderiseDialogFragment(context,
                        getResources().getString(R.string.orderisePromptBeforeDeleteAll), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAllOrders();
                        Intent backToMainActivity = new Intent(context, Orderise.class);
                        startActivity(backToMainActivity);
                        finish();
                    }
                });
                promptForDeleteAll.show(getFragmentManager(), "PromptForDeleteAll");

                break;
            }
            case (R.id.button_edit_order): {
                if (!showCheckBoxes) {
                    mDeleteButton.setVisibility(View.VISIBLE);
                    mAddMoreButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(GONE);
                    newOrderButton.setVisibility(GONE);
                    showCheckBoxes = !showCheckBoxes;
                    refreshListView();
                }
                break;
            }
            case (R.id.button_delete_items): {
                if (showCheckBoxes) {
                    if (selectedList != null && selectedList.size() > 0) {
                        OrderiseDialogFragment promptForDeleteSome = new OrderiseDialogFragment(context, getResources().getString(R.string.orderisePromptBeforeDeleteSome, selectedList.size()), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < selectedList.size(); i++) {
                                    CoffeeOrder order = (CoffeeOrder) selectedList.get(i);
                                    if (coffeeOrders.contains((order))) {
                                        coffeeOrders.remove(order);
                                    }
                                }
                                mDeleteButton.setVisibility(GONE);
                                mAddMoreButton.setVisibility(GONE);
                                newOrderButton.setVisibility(View.VISIBLE);
                                editButton.setVisibility(View.VISIBLE);
                                showCheckBoxes = !showCheckBoxes;
                                refreshListView();
                            }
                        });
                        promptForDeleteSome.show(getFragmentManager(), "PromptForDeleteSome");
                    } else {
                        final Snackbar sb = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.nothingToDelete), Snackbar.LENGTH_LONG);
                        sb.show();
                        mDeleteButton.setVisibility(GONE);
                        mAddMoreButton.setVisibility(GONE);
                        newOrderButton.setVisibility(View.VISIBLE);
                        editButton.setVisibility(View.VISIBLE);
                        showCheckBoxes = !showCheckBoxes;
                        refreshListView();
                    }
                }
                break;
            }
            case (R.id.button_add_more_items): {
                Intent backToMainActivity = new Intent(this, Orderise.class);
                backToMainActivity.putExtra("coffeeOrders", (Serializable) coffeeOrders);
                startActivity(backToMainActivity);
                finish();
            }
            break;
        }

    }

    private void refreshListView() {
        writeOrdersToFile();
        selectedList.clear();
        coffeeOrderAdapter = new CoffeeOrderAdapter(context, coffeeOrders, showCheckBoxes, selectedList, mListener);
        coffeeOrderList.setAdapter(coffeeOrderAdapter);
        mListenerHere.onRefresh();
    }

    private void writeOrdersToFile() {
        if (coffeeOrders.size() > 0) {
            FileOperations writeOrders = new FileOperations();
            writeOrders.writeToFile(this, coffeeOrders, this.getApplicationContext().getSharedPreferences("savedPreferences", Context.MODE_PRIVATE));
        } else {

        }
    }

    private ArrayListRefreshable<CoffeeOrder> createFromArrayList(ArrayList<CoffeeOrder> list) {
        ArrayListRefreshable<CoffeeOrder> newList = new ArrayListRefreshable<>();
        for (CoffeeOrder order : list) {
            newList.add(order);
        }
        return newList;
    }

    private void deleteAllOrders() {
        SharedPreferences mPreferences = context.getSharedPreferences("savedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("orderSize", 99999999);
        editor.apply();
    }
}

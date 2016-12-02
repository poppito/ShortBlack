package com.noni.Orderise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;


public class OrderiseDialogPrompt extends DialogFragment {

    private String title, message, positiveButtonText, negativeButtonText;
    private Context context;
    private ArrayListRefreshable<CoffeeOrder> orders;


    public OrderiseDialogPrompt(String title, String message, String positiveButtonText, String negativeButtonText, Context context, ArrayListRefreshable<CoffeeOrder> orders) {
        super();
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.context = context;
        this.orders = orders;
    }


    public OrderiseDialogPrompt(String title, String message, String positiveButtonText, String negativeButtonText, Context context ) {
        super();
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.context = context;
    }

    public OrderiseDialogPrompt() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent fireUpOrderDetails = new Intent(context, OrderDetails.class);
                fireUpOrderDetails.putExtra("coffeeOrders", orders);
                startActivity(fireUpOrderDetails);
            }
        }).setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
}

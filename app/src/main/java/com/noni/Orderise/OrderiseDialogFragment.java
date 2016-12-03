package com.noni.Orderise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class OrderiseDialogFragment extends DialogFragment {

    private Context context;
    private String message;
    private DialogInterface.OnClickListener mListener;

    public OrderiseDialogFragment() {
        super();
    }

    public OrderiseDialogFragment(Context c, String message, DialogInterface.OnClickListener mListener) {
        super();
        this.context = c;
        this.message = message;
        this.mListener = mListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.orderisePromptTitle))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.orderisePromptPositiveButton), mListener);

        builder.setNegativeButton(getResources().getString(R.string.orderisePromptNegativeButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
}

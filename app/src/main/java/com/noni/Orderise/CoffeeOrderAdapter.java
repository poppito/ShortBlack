package com.noni.Orderise;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class CoffeeOrderAdapter extends ArrayAdapter {

    private Context c;
    private TextView coffeeOrderItem;
    private TextView specialInstructionsItem;
    private CheckBox mCheckBox;
    private Boolean showCheckboxes;
    private ArrayListRefreshListener mListener;
    public ArrayListRefreshable<CoffeeOrder> selectionList, coffeeOrders;


    public CoffeeOrderAdapter(Context context, ArrayListRefreshable<CoffeeOrder> coffeeOrders, Boolean showCheckboxes, ArrayListRefreshable<CoffeeOrder> selectionList, ArrayListRefreshListener listener) {
        super(context, 0, coffeeOrders);
        this.c = context;
        this.showCheckboxes = showCheckboxes;
        this.coffeeOrders = coffeeOrders;
        this.selectionList = selectionList;
        this.mListener = listener;
    }

    public ArrayListRefreshable<CoffeeOrder> getSelectionList() {
        return this.selectionList;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.shortblack_coffee_list_item, parent, false);
        }

        CoffeeOrder order = (CoffeeOrder) coffeeOrders.get(position);
        mCheckBox = (CheckBox) convertView.findViewById(R.id.coffee_order_item_checkbox);

        if (showCheckboxes) {
            mCheckBox.setVisibility(View.VISIBLE);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CoffeeOrder selectedOrder = (CoffeeOrder) coffeeOrders.get(position);
                    if (isChecked) {
                        Log.v("SomeTag", selectedOrder.getOrderName() + " is checked");
                        selectionList.add(selectedOrder);
                        mListener.onRefresh();
                    } else {
                        if (selectionList.contains(selectedOrder)) {
                            selectionList.remove(selectedOrder);
                            mListener.onRefresh();
                        }
                    }
                }
            });
        } else {
            mCheckBox.setVisibility(View.INVISIBLE);
        }

        coffeeOrderItem = (TextView) convertView.findViewById(R.id.coffee_order_item);
        specialInstructionsItem = (TextView) convertView.findViewById(R.id.extras_order_item);
        coffeeOrderItem.setText(order.displayOrder());

        if (order.getSpecialOrder() != null) {
            specialInstructionsItem.setVisibility(View.VISIBLE);
            specialInstructionsItem.setText(order.getSpecialOrder());
        } else {
            specialInstructionsItem.setVisibility(View.GONE);
        }


        return convertView;
    }
}

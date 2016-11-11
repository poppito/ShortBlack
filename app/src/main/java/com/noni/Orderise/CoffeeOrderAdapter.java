package com.noni.Orderise;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CoffeeOrderAdapter extends ArrayAdapter {

    private Context c;
    private TextView coffeeOrderItem;
    private TextView specialInstructionsItem;
    private ArrayList<CoffeeOrder> orderList;

    public CoffeeOrderAdapter(Context context , ArrayList<CoffeeOrder> orderList) {
        super(context, 0, orderList);
        this.c = context;
        this.orderList = orderList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.shortblack_coffee_list_item, parent, false);
        }

        CoffeeOrder order = orderList.get(position);

        coffeeOrderItem = (TextView) convertView.findViewById(R.id.coffee_order_item);
        specialInstructionsItem = (TextView) convertView.findViewById(R.id.extras_order_item);
        coffeeOrderItem.setText(order.displayOrder());

        if (order.getSpecialOrder() != null) {
            specialInstructionsItem.setVisibility(View.VISIBLE);
            specialInstructionsItem.setText(order.getSpecialOrder());}
        else {
            specialInstructionsItem.setVisibility(View.GONE);
        }
        return convertView;
    }
}

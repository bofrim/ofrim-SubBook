package com.ofrim.ofrim_subbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by bradofrim on 2018-02-03.
 */

public class SubscriptionRowAdapter extends ArrayAdapter<Subscription> {

    private final Context context;
    private final Subscription[] subscriptions;

    public SubscriptionRowAdapter(Context context, Subscription[] subscriptions) {
        super(context, -1, subscriptions);
        this.context = context;
        this.subscriptions = subscriptions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for the row
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View subscriptionRow = inflater.inflate(R.layout.subscrtiption_row, parent, false);

        // Get TextView objects from the layout
        TextView rowName = subscriptionRow.findViewById(R.id.sub_row_name);
        TextView rowAmount = subscriptionRow.findViewById(R.id.sub_row_amount);
        TextView rowDate = subscriptionRow.findViewById(R.id.sub_row_date);

        // Set the values of the TextViews
        rowName.setText(subscriptions[position].getName());
        rowAmount.setText(subscriptions[position].getMonthlyChargeFormattedString());
        rowDate.setText(subscriptions[position].getStartDateFormattedString());

        return subscriptionRow;
    }
}

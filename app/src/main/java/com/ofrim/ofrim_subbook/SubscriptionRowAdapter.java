package com.ofrim.ofrim_subbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom row adapter for subscription
 */

public class SubscriptionRowAdapter extends ArrayAdapter<Subscription> {

    private final Context context;
    private final ArrayList<Subscription> subscriptions;

    public SubscriptionRowAdapter(Context context, ArrayList<Subscription> subscriptions) {
        super(context, -1, subscriptions);
        this.context = context;
        this.subscriptions = subscriptions;
    }

    /**
     * Return a view for a specific row
     * @param position position in subscription list
     * @param convertView
     * @param parent
     * @return
     */
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
        rowName.setText(subscriptions.get(position).getName());
        rowAmount.setText(subscriptions.get(position).getMonthlyChargeFormattedString());
        rowDate.setText(subscriptions.get(position).getStartDateFormattedString());

        return subscriptionRow;
    }

    /**
     * Get item from the subscription list
     * @param position position in the subscription list
     * @return
     */
    @Override
    public Subscription getItem(int position) {
        return subscriptions.get(position);
    }
}

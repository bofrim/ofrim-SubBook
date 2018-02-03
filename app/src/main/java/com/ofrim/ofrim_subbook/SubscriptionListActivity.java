package com.ofrim.ofrim_subbook;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.List;

public class SubscriptionListActivity extends ListActivity {

    private Subscription[] subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);

        // Associate the subscription data with the interface elements
        SubscriptionRowAdapter subscriptionArrayAdapter = new SubscriptionRowAdapter(this, subscriptions);
        setListAdapter(subscriptionArrayAdapter);
    }
}
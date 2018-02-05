package com.ofrim.ofrim_subbook;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Display a list of subscriptions
 * @Author Brad Ofrim
 */
public class SubscriptionListActivity extends ListActivity {
    private static final String LIST_FILE_NAME = "subscriptions.sav";

    private ArrayList<Subscription> subscriptions;
    SubscriptionRowAdapter subscriptionArrayAdapter;

    /**
     * Initialize the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);
        initializeSubscriptions();
        initializeNewSubscriptionButton();
        initializeClearButton();
        initializeListView();
    }

    /**
     * Re-initialize the activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        loadSubscriptions();
        updateTotalCharge();
        Subscription returningSubscription = (Subscription) getIntent().getSerializableExtra("subscription");
        Boolean deleteSubscription = getIntent().getBooleanExtra("delete", false);
        int positionIndex = getIntent().getIntExtra("index", -2);
        if(deleteSubscription == true && positionIndex >= 0) {
            removeSubscriptionAtIndex(positionIndex);
        }
        else if(returningSubscription != null) {
            updateSubscriptionList(returningSubscription, positionIndex);
        }
    }

    /**
     * Update the list of subscription and save it
     * @param subscription subscription object to be updated to
     * @param index position of subscrription to update
     */
    private void updateSubscriptionList(Subscription subscription, int index) {
        if(index == -2) {
            // Do nothing
        }
        else if(index == -1) {
            subscriptions.add(subscription);
        } else {
            subscriptions.set(index, subscription);
        }
        getIntent().putExtra("index", -2); // Ensure the same intent won't be read again
        this.subscriptionArrayAdapter.notifyDataSetChanged();
        saveSubscriptions();
        updateTotalCharge();
    }

    /**
     * Remove subscription and save list
     * @Param index index of the subscription to remove
     */
    private void removeSubscriptionAtIndex(int index) {
        subscriptions.remove(index);
        getIntent().putExtra("index", -2); // Ensure the same intent won't be read again
        this.subscriptionArrayAdapter.notifyDataSetChanged();
        saveSubscriptions();
        updateTotalCharge();
    }


    /**
     * Load subscription objects from a file
     */
    private void loadSubscriptions() {
        try {
            loadSubscriptionFile();
        } catch(FileNotFoundException e) {
            subscriptions.clear();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Save subscription objects to a file
     */
    private void saveSubscriptions() {
        try {
            saveSubscriptionFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Create subscription list and load subscriptions from memory
     */
    private void initializeSubscriptions() {
        subscriptions = new ArrayList<>();
        loadSubscriptions();
    }

    /**
     * Set the functionality of the button for creating new subscriptions
     */
    private void initializeNewSubscriptionButton() {
        Button newSubscriptionButton = findViewById(R.id.new_sub_button);
        newSubscriptionButton.setOnClickListener(new View.OnClickListener() {
            // Go to detail screen without sending data
            @Override
            public void onClick(View view) {
                Intent newSubIntent = new Intent(view.getContext(), SubscriptionDetailActivity.class);
                startActivity(newSubIntent);
            }
        });
    }

    /**
     * Set the functionality for the clear button
     */
    private void initializeClearButton() {
        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener () {
            // Go to detail screen without sending data
            @Override
            public void onClick(View view) {
                subscriptions.clear();
                saveSubscriptions();
                subscriptionArrayAdapter.notifyDataSetChanged();
                saveSubscriptions();
                updateTotalCharge();
            }
        });
    }

    /**
     * Setup the list view the functionality for what happens when a list view element is clicked
     */
    private void initializeListView() {
        ListView subscriptionListView = this.getListView();
        this.subscriptionArrayAdapter = new SubscriptionRowAdapter(this, subscriptions);
        subscriptionListView.setAdapter(subscriptionArrayAdapter);

        // Setup what happens when a row is clicked
        subscriptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Send data to the subscription detail view
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Subscription subscription = (Subscription) adapter.getItemAtPosition(i);
                Intent newSubIntent = new Intent(view.getContext(), SubscriptionDetailActivity.class);
                newSubIntent.putExtra("subscription", subscription);
                newSubIntent.putExtra("index", i);
                startActivity(newSubIntent);
            }
        });
    }

    /**
     * Attempt to load subscription file from memory
     */
    private void loadSubscriptionFile() throws FileNotFoundException, IOException {
        FileInputStream fis = openFileInput(LIST_FILE_NAME);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
        subscriptions.clear();
        ArrayList<Subscription> savedSubscriptions = gson.fromJson(in, listType);
        subscriptions.addAll(savedSubscriptions);
    }

    /**
     * Attempt to save subscription file to memory
     */
    private void saveSubscriptionFile() throws FileNotFoundException, IOException {
        FileOutputStream fos = openFileOutput(LIST_FILE_NAME, Context.MODE_PRIVATE);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
        Gson gson = new Gson();
        gson.toJson(subscriptions, out);
        out.flush();
    }

    /**
     * Update the total charge
     */
    private void updateTotalCharge() {
        Double total = calculateTotalSubscriptionAmount();
        setTotalText(total);
    }

    /**
     * Sum up the monthly subscription amounts
     */
    private Double calculateTotalSubscriptionAmount() {
        Double total = new Double(0);
        for(Subscription subscription: subscriptions) {
            total += subscription.getMonthlyCharge();
        }
        return total;
    }

    /**
     * Set the total text as a currency value
     */
    private void setTotalText(Double amount) {
        TextView totalLabel = findViewById(R.id.total_text);
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
        totalLabel.setText(moneyFormat.format(amount));
    }
}
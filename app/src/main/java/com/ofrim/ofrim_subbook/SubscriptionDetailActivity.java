package com.ofrim.ofrim_subbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Show details for a subscription
 * @Author Brad Ofrim
 */
public class SubscriptionDetailActivity extends AppCompatActivity {

    private int positionIndex = -1;
    private EditText subscriptionNameField;
    private EditText subscriptionAmountField;
    private EditText subscriptionDateField;
    private EditText subscriptionCommentField;

    /**
     * Initialize the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_detail);
        initializeInterface();
        initializeSaveButton();
        initializeDeleteButton();
        grabDataFromIntent();
    }

    /**
     * Associate interface elements with objects
     */
    private void initializeInterface() {
        subscriptionNameField = findViewById(R.id.title_field);
        subscriptionAmountField = findViewById(R.id.amount_field);
        subscriptionDateField = findViewById(R.id.date_field);
        subscriptionCommentField = findViewById(R.id.comment_field);
    }

    /**
     * Set the functions of the start button
     */
    private void initializeSaveButton() {
        Button saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    resetInterfaceStyle();
                    Subscription subscriptionToSend = extractSubscriptionFromInterface();
                    Intent intentToSend = buildIntentFromSubscription(subscriptionToSend, view.getContext());
                    startActivity(intentToSend);
                }
                catch (InvalidSubscriptionName e) {
                    indicateInvalidSubscriptionName();
                }
                catch (InvalidSubscriptionComment e) {
                    indicateInvalidSubscriptionComment();
                }
                catch (ParseException e) {
                    indicateInvalidSubscriptionDate();
                }
                catch (NegativeChargeException | NumberFormatException e) {
                    indicateInvalidSubscriptionCharge();
                }
            }
        });
    }

    /**
     * Set the functions of the delete button
     */
    private void initializeDeleteButton() {
        Button deleteButton = findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendSubIntent = new Intent(view.getContext(), SubscriptionListActivity.class);
                sendSubIntent.putExtra("index", positionIndex);
                sendSubIntent.putExtra("delete", true);
                startActivity(sendSubIntent);
            }
        });
    }

    /**
     * Grab data sent from intent
     */
    private void grabDataFromIntent() {
        // Grab data from the intent
        Subscription subscription = (Subscription) getIntent().getSerializableExtra("subscription");

        // Add incoming data to the interface
        if(subscription != null) {
            subscriptionNameField.setText(subscription.getName());
            subscriptionDateField.setText(subscription.getStartDateFormattedString());
            subscriptionAmountField.setText(subscription.getMonthlyCharge().toString());
            subscriptionCommentField.setText(subscription.getComment());
            this.positionIndex = (int) getIntent().getIntExtra("index", -1);
        }
    }

    /**
     * Extract the details fof a subscription from the user interface
     */
    private Subscription extractSubscriptionFromInterface()
            throws ParseException,
            InvalidSubscriptionName,
            InvalidSubscriptionComment,
            NegativeChargeException {
        String name = subscriptionNameField.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(subscriptionDateField.getText().toString());
        Double amount = Double.parseDouble(subscriptionAmountField.getText().toString());
        String comment = subscriptionCommentField.getText().toString();
        // Build the subscription object to be sent
        return new Subscription(name, date, amount, comment);
    }

    /**
     * Build an intent representing a subsription to be sent
     */
    private Intent buildIntentFromSubscription(Subscription subscription, Context context) {
        Intent intent = new Intent(context, SubscriptionListActivity.class);
        intent.putExtra("subscription", subscription);
        intent.putExtra("index", positionIndex);
        intent.putExtra("delete", false);
        return intent;
    }

    /**
     * Reset interface element styling
     */
    private void resetInterfaceStyle() {
        setNameColor(Color.BLACK);
        setDateColor(Color.BLACK);
        setChargeColor(Color.BLACK);
        setCommentColor(Color.BLACK);
    }

    /**
     * Indicate invalid subsription name
     */
    private void indicateInvalidSubscriptionName() {
        setNameColor(Color.RED);
    }

    /**
     * Indicate invalid subscription comment
     */
    private void indicateInvalidSubscriptionComment() {
        setCommentColor(Color.RED);
    }

    /**
     * Indicate invalid subscription date
     */
    private void indicateInvalidSubscriptionDate() {
        setDateColor(Color.RED);
    }

    /**
     * Indicate invalid subscription charge
     */
    private void indicateInvalidSubscriptionCharge() {
        setChargeColor(Color.RED);
    }

    /**
     * Set the name field title and text to a color
     * @param color color to set
     */
    private void setNameColor(int color) {
        TextView subscriptionName = findViewById(R.id.subscription_title);
        subscriptionName.setTextColor(color);
        subscriptionNameField.setTextColor(color);
    }

    /**
     * Set the date field title and text to a color
     * @param color color to set
     */
    private void setDateColor(int color) {
        TextView subscriptionDate = findViewById(R.id.subscription_date);
        subscriptionDate.setTextColor(color);
        subscriptionDateField.setTextColor(color);
    }

    /**
     * Set the Charge field title and text to a color
     * @param color color to set
     */
    private void setChargeColor(int color) {
        TextView subscriptionAmount = findViewById(R.id.subscription_amount);
        subscriptionAmount.setTextColor(color);
        subscriptionAmountField.setTextColor(color);
    }

    /**
     * Set the Comment field title and text to a color
     * @param color color to set
     */
    private void setCommentColor(int color) {
        TextView subscriptionComment = findViewById(R.id.subscription_comment);
        subscriptionComment.setTextColor(color);
        subscriptionCommentField.setTextColor(color);
    }
}

package com.ofrim.ofrim_subbook;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Describe a monthly subscription
 * @Author Brad Ofrim
 */

public class Subscription implements Serializable {

    private static final int MAX_NAME_LEN = 20;
    private static final int MAX_COMMENT_LEN = 30;

    private String name;
    private Date startDate;
    private Double monthlyCharge;
    private String  comment;

    Subscription(String name, Date startDate, Double monthlyCharge, String comment)
            throws InvalidSubscriptionName, InvalidSubscriptionComment, NegativeChargeException {
        checkNameIsValid(name);
        checkCommentIsValid(comment);
        checkChargeIsPositive(monthlyCharge);
        this.name = name;
        this.comment = comment;
        this.startDate = startDate;
        this.monthlyCharge = monthlyCharge;
    }

    /**
     * Gets a string representing the subscription name
     * @return the subscription name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name for the subscription
     * @param name subscription name
     * @throws InvalidSubscriptionName
     */
    public void setName(String name) throws InvalidSubscriptionName {
        checkNameIsValid(name);
        this.name = name;
    }

    /**
     * Gets the comment associated with a subsciption
     * @return string representing the subscription's comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Set a comment for a subscription
     * @param comment subscription comment
     * @throws InvalidSubscriptionComment
     */
    public void setComment(String comment) throws InvalidSubscriptionComment {
        checkCommentIsValid(comment);
        this.comment = comment;
    }

    /**
     * Get the start date of a subsription
     * @return a date object representing the subsription's start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Gets a date string formatted to match the yyyy-MM-dd pattern
     * @return string representing the subscription start date
     */
    public String getStartDateFormattedString() {
        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.startDate);
    }

    /**
     * Set a new start date for the subscription
     * @param date a start date
     */
    public void setStartDate(Date date) {
        this.startDate = date;
    }

    /**
     * Gets the monthly charge amount of a subscription
     * @return
     */
    public Double getMonthlyCharge() {
        return this.monthlyCharge;
    }

    /**
     * Gets a string representing the subscription's in a money format
     * @return formatted string to monthly charge as currency
     */
    public String getMonthlyChargeFormattedString() {
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
        return moneyFormat.format(this.monthlyCharge);
    }

    /**
     * Sets the monthly charge for a subscription
     * @param charge the subscription's monthly charge
     */
    public void setMonthlyCharge(Double charge) {
        this.monthlyCharge = charge;
    }

    /**
     * Ensure that the name is a valid name
     * @param name name to test
     * @throws InvalidSubscriptionName
     */
    private void checkNameIsValid(String name) throws InvalidSubscriptionName {
        if( name.length() > MAX_NAME_LEN || name.length() <= 0) {
            throw new InvalidSubscriptionName();
        }
    }

    /**
     * Ensure that the comment is valid
     * @param comment comment to test
     * @throws InvalidSubscriptionComment
     */
    private void checkCommentIsValid(String comment) throws InvalidSubscriptionComment {
        if( comment.length() > MAX_COMMENT_LEN ) {
            throw new InvalidSubscriptionComment();
        }
    }

    /**
     * Ensure that the monthly charge is non negative
     * @param amount amount to check
     * @throws NegativeChargeException
     */
    private void checkChargeIsPositive(Double amount) throws NegativeChargeException {
        if( amount < 0 ) {
            throw new NegativeChargeException();
        }
    }
}

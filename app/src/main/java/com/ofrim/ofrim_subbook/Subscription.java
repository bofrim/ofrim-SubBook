package com.ofrim.ofrim_subbook;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bradofrim on 2018-02-03.
 */

public class Subscription {

    private static final int MAX_NAME_LEN = 20;
    private static final int MAX_COMMENT_LEN = 30;

    private String name;
    private Date startDate;
    private Double monthlyCharge;
    private String  comment;

    Subscription(String name, Date startDate, Double monthlyCharge, String comment) throws NameTooLongException, CommentTooLongException {
        checkNameIsValid(name);
        checkCommentIsValid(comment);
        this.name = name;
        this.comment = comment;
        this.startDate = startDate;
        this.monthlyCharge = monthlyCharge;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws NameTooLongException {
        checkNameIsValid(name);
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) throws CommentTooLongException {
        checkCommentIsValid(comment);
        this.comment = comment;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public String getStartDateFormattedString() {
        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.startDate);
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public Double getMonthlyCharge() {
        return this.monthlyCharge;
    }

    public String getMonthlyChargeFormattedString() {
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
        return moneyFormat.format(this.monthlyCharge);
    }

    public void setMonthlyCharge(Double charge) {
        this.monthlyCharge = charge;
    }

    private void checkNameIsValid(String name) throws NameTooLongException {
        if( name.length() > MAX_NAME_LEN ) {
            throw new NameTooLongException();
        }
    }

    private void checkCommentIsValid(String comment) throws CommentTooLongException{
        if( comment.length() > MAX_COMMENT_LEN ) {
            throw new CommentTooLongException();
        }
    }
}

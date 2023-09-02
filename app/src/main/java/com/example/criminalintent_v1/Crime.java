package com.example.criminalintent_v1;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    private boolean mRequirespolice;

    private String mSuspect;
    public Crime() {
       this (UUID.randomUUID());
    }

    public Crime(UUID uuid)
    {
        mId = uuid;
        mDate = new Date();
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getSuspect()
    {
        return mSuspect;
    }

    public void setRequirespolice(boolean requirespolice)
    {
        mRequirespolice = requirespolice;
    }

    public boolean isRequirespolice()
    {
        return mRequirespolice;
    }
    public UUID getId() {
        return mId; }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}

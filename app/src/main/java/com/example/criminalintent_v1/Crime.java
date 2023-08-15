package com.example.criminalintent_v1;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    private Date mTime;

    private boolean mRequirespolice;
    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
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

    public void setTime(Date time)
    {
        mTime = time;
    }

    public Date getTime()
    {
        return mTime;
    }
}

package com.example.criminalintent_v1;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class DatePickerHostActivity extends SingleFragmentActivity{

    public static final String EXTRA_DATE ="crime date";

    public static Intent newIntent(Context packageContext, Date date)
    {
        Intent intent = new Intent(packageContext,DatePickerHostActivity.class);
        intent.putExtra(EXTRA_DATE,date);
        return intent;
    }
    public Fragment createFragment()
    {
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_DATE);
        return DatePickerFragment.newInstance(date);
    }
}

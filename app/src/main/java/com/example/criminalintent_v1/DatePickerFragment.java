package com.example.criminalintent_v1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {


    public static final String ARG_DATE ="date";
    public static final String EXTRA_DATE = "Criminal Intent";
    private DatePicker mDatePicker;
    private Button oKButton;

    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dialog_date,container,false);

        Date date = (Date)getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year,month,day,null);

        oKButton = (Button) v.findViewById(R.id.OK_Button);
        oKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year= mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();

                Date date = new GregorianCalendar(year,month,day).getTime();
                sendResult(Activity.RESULT_OK,date);

                if(getTargetFragment()!=null)
                    dismiss();
                else
                    getActivity().finish();
            }
        });

        return v;

    }

   private void sendResult(int ResultCode,Date date)
   {
       Intent intent = new Intent();
       intent.putExtra(EXTRA_DATE,date);

       if(getTargetFragment()!=null)
           getTargetFragment().onActivityResult(getTargetRequestCode(),ResultCode,intent);

       else
           getActivity().setResult(Activity.RESULT_OK, intent);



   }
}

package com.example.criminalintent_v1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "Criminal Intent";
    private TimePicker mTimePicker;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
       View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);

       mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);

       return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title)
               .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       int hour = mTimePicker.getHour();
                       int minutes = mTimePicker.getMinute();
                       Calendar calendar = Calendar.getInstance();
                       calendar.set(Calendar.HOUR_OF_DAY,hour);
                       calendar.set(Calendar.MINUTE,minutes);
                       Date time = calendar.getTime();
                       sendResult(CrimePagerActivity.RESULT_OK,time);

                   }
               }).create();
    }

    private void sendResult(int resultCode,Date time)
    {
        if(getTargetFragment()==null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME,time);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}

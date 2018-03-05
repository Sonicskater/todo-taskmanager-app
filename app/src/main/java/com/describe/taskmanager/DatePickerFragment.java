package com.describe.taskmanager;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by devon on 3/4/2018.
 */

public class DatePickerFragment extends DialogFragment {
    Button cancel;
    DatePicker picker;
    Button confirm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedState){
        View view = inflater.inflate(R.layout.fragment_date_picker,viewGroup,false);
        cancel = view.findViewById(R.id.cancel);
        confirm = view.findViewById(R.id.confirm);
        picker = view.findViewById(R.id.datePicker);
        final Date time = new Date();
        time.setTime(getArguments().getLong("time"));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar tempCal = Calendar.getInstance();
                tempCal.setTimeInMillis(time.getTime());
                //getCurrent____ was depreacted in API 23 a d replaced with get____ so in order to maintain our API 21 Minmimum we need to check for API version.
                if (Build.VERSION.SDK_INT>=23){


                }
                else
                {

                }

                ownerOjbect.passDateTime(tempCal.getTime());
                dismiss();
            }
        });

        return view;
    }
}

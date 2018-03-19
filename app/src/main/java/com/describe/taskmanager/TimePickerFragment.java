package com.describe.taskmanager;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;



public class TimePickerFragment extends DialogFragment {
    Button cancel;
    DateTimeInterface ownerOjbect;
    Button confirm;
    TimePicker picker;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedState){
        View view = inflater.inflate(R.layout.fragment_time_picker,viewGroup,false);
        cancel = view.findViewById(R.id.cancel);
        confirm = view.findViewById(R.id.confirm);
        picker = view.findViewById(R.id.fragPicker);
        final Date time = new Date();
        if (getArguments()!=null) {
            time.setTime(getArguments().getLong("time"));
        }
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

                    tempCal.set(Calendar.HOUR_OF_DAY,picker.getHour());
                    tempCal.set(Calendar.MINUTE,picker.getMinute());
                }
                else
                {
                    tempCal.set(Calendar.HOUR_OF_DAY,picker.getCurrentHour());
                    tempCal.set(Calendar.MINUTE,picker.getCurrentMinute());
                }

                ownerOjbect.passDateTime(tempCal.getTime());
                dismiss();
            }
        });

        return view;
    }

    public static TimePickerFragment newInstance(Date date,DateTimeInterface DTInterface){
        TimePickerFragment frag = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putLong("time",date.getTime());
        frag.ownerOjbect = DTInterface;
        frag.setArguments(args);
        return frag;
    }

}

package com.describe.taskmanager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;




public class DatePickerFragment extends DialogFragment {
    Button cancel;
    DatePicker picker;
    Button confirm;
    DateTimeInterface ownerObject;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedState){
        View view = inflater.inflate(R.layout.fragment_date_picker,viewGroup,false);
        cancel = view.findViewById(R.id.cancel);
        confirm = view.findViewById(R.id.confirm);
        picker = view.findViewById(R.id.datePicker);
        final Date time = new Date();
        if (getArguments() != null) {
            time.setTime(getArguments().getLong("date"));
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

                    tempCal.set(Calendar.DAY_OF_MONTH,picker.getDayOfMonth());
                    tempCal.set(Calendar.MONTH,picker.getMonth());


                ownerObject.passDateTime(tempCal.getTime());
                dismiss();
            }
        });

        return view;
    }
    public static DatePickerFragment newInstance(Date date,DateTimeInterface DTInterface){
        DatePickerFragment frag = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putLong("date",date.getTime());
        frag.ownerObject = DTInterface;
        frag.setArguments(args);
        return frag;
    }
}

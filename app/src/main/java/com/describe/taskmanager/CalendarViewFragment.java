package com.describe.taskmanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;


public class CalendarViewFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener
{
    CalendarView calendarView;
    FirestoreAgent fsAgent;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedState)
    {
        View view = inflater.inflate(R.layout.fragment_caelndar_view,viewGroup,false);

        this.fsAgent = FirestoreAgent.getInstance();
        calendarView = view.findViewById(R.id.CalendarView);

        //Listen to any clicks on the calendar.
         calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
         {
             @Override
             //Takes you to the daily view of the date selected.
             public void onSelectedDayChange(@NonNull CalendarView newCalendarView, int year, int month, int day)
             {
                 Calendar calendar = Calendar.getInstance();
                 calendar.set(year, month, day);

                 //Opens the daily view.
                 if(getActivity()!=null) {
                     Intent intent = new Intent(getActivity().getApplicationContext(), DailyViewActivity.class);
                     SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM dd, yyyy", Locale.ENGLISH);
                     intent.putExtra("date", sdf.format(calendar.getTime()));
                     startActivity(intent);
                 }

             }
         });

        return view;
    }
    @Override
    public void onRefresh()
    {
        refreshLayout.setRefreshing(true);
    }

    public static CalendarViewFragment newInstance()
    {
        return new CalendarViewFragment();
    }
}

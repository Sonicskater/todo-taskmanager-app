package com.describe.taskmanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by camila on 2018-03-21.
 */

public class CalendarViewFragment extends Fragment implements UIInterface, SwipeRefreshLayout.OnRefreshListener
{
    CalendarView calendarView;
    FirestoreAgent fsAgent;
    SwipeRefreshLayout refreshLayout;
    private static final String TAG = "CalendarView";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedState)
    {
        View view = inflater.inflate(R.layout.fragment_caelndar_view,viewGroup,false);

        this.fsAgent = FirestoreAgent.getInstance();
        calendarView = view.findViewById(R.id.CalendarView);

         calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
         {
             @Override
             public void onSelectedDayChange(CalendarView newCalendarView, int year, int month, int day)
             {
                 Calendar calendar = Calendar.getInstance();
                 calendar.set(year, month, day);
                 int week = calendar.get(Calendar.DAY_OF_WEEK);

                 String monthString = null;
                 switch (month)
                 {
                     case 0:
                         monthString = "January";
                         break;
                     case 1:
                         monthString = "February";
                         break;
                     case 2:
                         monthString = "March";
                         break;
                     case 3:
                         monthString = "April";
                         break;
                     case 4:
                         monthString = "May";
                         break;
                     case 5:
                         monthString = "June";
                         break;
                     case 6:
                         monthString = "July";
                         break;
                     case 7:
                         monthString = "August";
                         break;
                     case 8:
                         monthString = "September";
                         break;
                     case 9:
                         monthString = "October";
                         break;
                     case 10:
                         monthString = "November";
                         break;
                     case 11:
                         monthString = "December";
                         break;
                 }

                 String weekString = "";
                 switch (week)
                 {
                     case 1:
                         weekString = "Sunday";
                         break;
                     case 2:
                         weekString = "Monday";
                         break;
                     case 3:
                         weekString = "Tuesday";
                         break;
                     case 4:
                         weekString = "Wednesday";
                         break;
                     case 5:
                         weekString = "Thursday";
                         break;
                     case 6:
                         weekString = "Friday";
                         break;
                     case 7:
                         weekString = "Saturday";
                         break;
                 }

                 String date = weekString + "  " + monthString + " " + day + ", " + year;
                 Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy" + date);

                 Intent intent = new Intent(getActivity().getApplicationContext(), DailyViewActivity.class);
                 intent.putExtra("date", date);
                 startActivity(intent);

             }
         });

        return view;
    }



    @Override
    public void updateObject (String fieldName, Object requestedObj){

    }

    @Override
    public void updateTaskCollection (String collectionName, ArrayList < TaskEvent > collectionContent){

    }

    @Override
    public void updateCategoryCollection (String collectionName, ArrayList < Category > collectionContent){

    }

    @Override
    public void firebaseSuccess (String message_title, String message_content)
    {
    }

    @Override
    public void firebaseFailure (String error_code, String message_title, String extra_content)
    {
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

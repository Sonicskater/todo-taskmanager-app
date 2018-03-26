package com.describe.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;




public class TaskEventDetails extends DialogFragment {
    View view;
    final TaskEventDetails self = this;
    TaskEvent taskEvent;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedState) {
        view = inflater.inflate(R.layout.fragment_task_details_layout, viewGroup, false);


        if (getArguments()!=null) {
            final TaskEvent task = (TaskEvent) getArguments().getSerializable("task");
            taskEvent = task;
            final String category = getArguments().getString("category");
            if(task!=null){
                ((TextView)view.findViewById(R.id.title)).setText(task.getTitle());
                ((TextView)view.findViewById(R.id.description)).setText(task.getDescription());
                view.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getActivity()!=null) {
                            Intent i = new Intent(getActivity().getApplicationContext(), TaskEventView.class);
                            i.putExtra("taskEvent", task);
                            i.putExtra("category", category);


                            getActivity().getApplicationContext().startActivity(i);

                            getActivity().getSupportFragmentManager().beginTransaction().remove(self).commit();

                        }
                    }
                });

                if (task.hasAlarm()&&getActivity()!=null) {
                    View alarmView = inflater.inflate(R.layout.fragment_alarm_details, viewGroup, false);
                    ((LinearLayout) view).addView(alarmView);
                    ((TextView) alarmView.findViewById(R.id.date)).setText(task.getAlarmDate().toString());

                }

            }
        }
        return view;
    }
    public static TaskEventDetails newInstance(TaskEvent task,String category){
        TaskEventDetails frag = new TaskEventDetails();


        Bundle args = new Bundle();
        args.putSerializable("task",task);
        args.putString("category", category);
        frag.setArguments(args);
        return frag;
    }

}

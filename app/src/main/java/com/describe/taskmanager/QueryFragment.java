package com.describe.taskmanager;

import android.support.v4.app.Fragment;

//generic fragment for future advanced search ui
public abstract class QueryFragment extends Fragment {
    // ensures that there is a fragment to display inside the advanced search dialog.
    public abstract QueryTerm generateTerm();
}

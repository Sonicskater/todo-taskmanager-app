package com.describe.taskmanager;

import android.support.v4.app.Fragment;


public abstract class QueryFragment extends Fragment {
    // ensures that there is a fragment to display inside the advanced search dialog.
    public abstract QueryTerm generateTerm();
}

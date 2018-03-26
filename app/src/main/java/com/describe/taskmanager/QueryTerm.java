package com.describe.taskmanager;

public abstract class QueryTerm {
    public abstract boolean compare(TaskEvent event);
    public abstract String toString();
    public abstract String toTag();
    public abstract QueryFragment getFragment();
}

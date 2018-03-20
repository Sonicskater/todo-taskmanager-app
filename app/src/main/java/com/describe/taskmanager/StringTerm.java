package com.describe.taskmanager;

import android.app.Fragment;

//Example search term object, for strings in titles and/or descriptions.
public class StringTerm extends QueryTerm {
    private boolean searchTitle = false;
    private boolean searchDescription = false;
    private String searchTerm =null;
    public StringTerm(String term,boolean title,boolean description){
        this.searchTitle = title;
        this.searchDescription = description;
        this.searchTerm = term;
    }
    @Override
    public boolean compare(TaskEvent event) {
        return ((event.getDescription().contains(searchTerm) && this.searchDescription) || (event.getTitle().contains(searchTerm) && this.searchTitle));
    }

    @Override
    public String toString() {
        String out = "Search the";
        if(searchTitle){
            out+=" title";
            if(searchDescription){
                out+=" & description";
            }
        }
        else if(searchDescription){
            out+=" description";
        }
        else{
            out+=" nothing";
        }
        out+="for the term \""+searchTerm+"\".";
        return out;
    }

    @Override
    public String toTag() {
        return "S: "+searchTerm;
    }

    @Override
    public QueryFragment getFragment() {

        return new StringFragment();
    }
    public static class StringFragment extends QueryFragment{
        private boolean searchTitle = false;
        private boolean searchDescription = false;
        private String searchTerm = "";
        @Override
        public QueryTerm addTerm() {
            return new StringTerm(searchTerm,searchTitle,searchDescription);
        }
    }
}

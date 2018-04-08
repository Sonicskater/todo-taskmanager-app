package com.describe.taskmanager;

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
        if (event!=null&&event.getDescription()!=null&&event.getTitle()!=null) {
            return ((event.getDescription().contains(searchTerm) && this.searchDescription) || (event.getTitle().contains(searchTerm) && this.searchTitle));
        }
        return false;
    }

    @Override
    public String toString() {
        String out = "Search ";
        if(searchTitle){
            out+=" the title";
            if(searchDescription){
                out+=" & description";
            }
        }
        else if(searchDescription){
            out+=" the description";
        }
        else{
            out+=" nothing";
        }
        out+="for the term \""+searchTerm+"\".";
        return out;
    }

    @Override
    public String toTag() {
        return "S:"+searchTerm;
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
        public QueryTerm generateTerm() {
            return new StringTerm(searchTerm,searchTitle,searchDescription);
        }
    }
}

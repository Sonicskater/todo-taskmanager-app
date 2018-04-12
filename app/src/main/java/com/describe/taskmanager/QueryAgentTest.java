package com.describe.taskmanager;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.describe.taskmanager.QueryResult.searchPriority.DEFAULT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryAgentTest {
    @Test
    public void QueryTest(){
        QueryAgent agent = new QueryAgent();
        HashMap<Category,ArrayList<TaskEvent>> localCopy = new HashMap<>();
        ArrayList<TaskEvent> templist = new ArrayList<>();
        Category testCat1 = new Category(5,"Test Category");
        Category testCat2 = new Category(57,"Its Me, a category");

        templist.add(new TaskEvent("Test task 1","test desc",new Date(3400000L),"0:01",false));
        templist.add(new TaskEvent("Test task 1126","test desc2",new Date(3400234600L),"0:07",false));
        templist.add(new TaskEvent("Test task 1166","test desc3",new Date(345000L),"0:01",false));
        templist.add(new TaskEvent("Test task 112","test desc4",new Date(100000L),"0:03",false));
        templist.add(new TaskEvent("Test task 176","test desc5",new Date(34002340L),"0:05",false));

        localCopy.put(testCat1,templist);


        ArrayList<TaskEvent> templist2 = new ArrayList<>();
        templist2.add(new TaskEvent("Look","at",new Date(3400000L),"0:01",false));
        templist2.add(new TaskEvent("me","i'm",new Date(3400000L),"0:06",false));
        templist2.add(new TaskEvent("a","unit",new Date(3400000L),"0:012",false));
        templist2.add(new TaskEvent("test","test desc",new Date(3400000L),"0:011243",false));
        templist2.add(new TaskEvent("Test task 11","test desc",new Date(3400000L),"0:012",false));

        localCopy.put(testCat2,templist2);

        agent.setLocalCopy(localCopy);
        ArrayList<QueryTerm> terms = new ArrayList<QueryTerm>();
        terms.add(new StringTerm("Test task 11",true,true));

        HashMap<Category,ArrayList<TaskEvent>> out = agent.Query(terms).getResultWithCategories(DEFAULT);

        HashMap<Category,ArrayList<TaskEvent>> testAgainst = new HashMap<>();
        ArrayList<TaskEvent> templist3 = new ArrayList<>();


        templist.add(new TaskEvent("Test task 1126","test desc2",new Date(3400234600L),"0:07",false));
        templist.add(new TaskEvent("Test task 1166","test desc3",new Date(345000L),"0:01",false));
        templist.add(new TaskEvent("Test task 112","test desc4",new Date(100000L),"0:03",false));

        testAgainst.put(testCat1,templist);


        ArrayList<TaskEvent> templist4 = new ArrayList<>();
        templist2.add(new TaskEvent("Test task 11","test desc",new Date(3400000L),"0:012",false));

        testAgainst.put(testCat2,templist2);

        assertTrue(testAgainst.keySet().size()==out.keySet().size());
        assertTrue(out.get(testCat1).size()==testAgainst.get(testCat1).size());
        assertTrue(out.get(testCat2).size()==testAgainst.get(testCat2).size());

    }
}

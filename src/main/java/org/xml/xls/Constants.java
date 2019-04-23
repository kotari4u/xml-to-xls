package org.xml.xls;

import java.util.Arrays;
import java.util.List;

public class Constants {
    // Column name, column width
    public static List<String[]> COLUMN_LIST = Arrays.asList(new String[]{"Person ID","7"},
            new String[]{"Full Name","7"},
            new String[]{"First Name", "7"},
            new String[]{"Last Name","7"},
            new String[]{"Address1","7"},
            new String[]{"Address2","7"},
            new String[]{"Address3","7"},
            new String[]{"Address4","7"},
            new String[]{"Phone","7"},
            new String[]{"Institution Name","7"},
            new String[]{"Department Name","7"},
            new String[]{"Division Name","7"},
            new String[]{"Job Title","7"},
            new String[]{"Faculty Type", "7"},
            new String[]{"Publication Source","7"},
            new String[]{"PM ID","7"},
            new String[]{"URL","20"},
            new String[]{"Publication Reference","30"},
            new String[]{"Title","20"},
            new String[]{"Authors","20"},
            new String[]{"Journal","7"},
            new String[] {"IssueInfo","10"},
            new String[]{"Date","7"});

    public static String SHEET_NAME = "PubList";

    public static int MAX_PUBLICATIONS = 10; // Represents how many publications should be recorded

    public static String FILE_NAME = "Publications.xlsx";

    public static int NUM_PERSONS_TO_FETCH = 4000;
}

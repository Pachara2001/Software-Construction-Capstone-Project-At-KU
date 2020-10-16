package condo.models;

import java.util.Comparator;

public class SortByDateAndTime implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        SortTime s1 = (SortTime)o1;
        SortTime s2 = (SortTime)o2;
      return s2.getTime().compareTo(s1.getTime());
    }
}

package condo.models;

import java.util.Comparator;

public class SortByDateAndTime implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Sort s1 = (Sort)o1;
        Sort s2 = (Sort)o2;
      return s2.getTime().compareTo(s1.getTime());
    }
}

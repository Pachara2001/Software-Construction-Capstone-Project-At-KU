package condoapp;

import java.util.Comparator;

public class SortByDataAndTime implements Comparator<StaffAccount> {
    @Override
    public int compare(StaffAccount o1, StaffAccount o2) {
        return o2.getDateAndTime().compareTo(o1.getDateAndTime());
    }
}

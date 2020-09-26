package condoapp.models;

import java.util.Comparator;

public class SortByDateAndTime implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if(o1.getClass().getSimpleName().equals("StaffAccount")) return ((StaffAccount)o2).getDateAndTime().compareTo(((StaffAccount)o1).getDateAndTime());
        return((Item)o2).getDateAndTime().compareTo(((Item)o1).getDateAndTime());
    }
}

package pl.javastart.library.model.comparator;

import pl.javastart.library.model.Publication;

import java.util.Comparator;

public class DataComparator implements Comparator<Publication> {
    @Override
    public int compare(Publication p1, Publication p2) {
        if (p1 == null && p2 == null){
            return 0;
        } else if (p1 == null) {
            return 1;
        } else if (p2 == null) {
            return -1;
        }
        Integer y1 = p1.getYear();
        Integer y2 = p2.getYear();
        return y1.compareTo(y2);
    }
}

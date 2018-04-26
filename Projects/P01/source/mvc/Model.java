package mvc;

import java.util.List;

public interface Model {

    public List<View> get_views();

    public default void add(List<View> views, View view) {

        views.add(view);
    }

    public default void remove(List<View> views, View view) {

        views.remove(view);
    }
}

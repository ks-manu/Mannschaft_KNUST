package mannschaft_knust.classrep;

import java.util.List;

import retrofit2.Callback;

interface CustomCallback<T> extends Callback<T> {
    boolean hasBeenCalled();
    boolean hasNewData();
    T getNewData();
}

package mannschaft_knust.classrep;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class CourseListViewModel extends AndroidViewModel {
    private LiveData<List<Course>> courseList;


    public CourseListViewModel(Application application){
        super(application);
        Database database = Database.getDatabase(application);
        DatabaseDao databaseDao = database.databaseDao();
        courseList = databaseDao.getCourseList();
    }

    public LiveData<List<Course>> getCourseList(){
        return courseList;
    }

}

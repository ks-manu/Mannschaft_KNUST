package mannschaft_knust.classrep;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {
    DatabaseDao databaseDao;
    private LiveData<List<Course>> courseList;
    private LiveData<List<CourseSession>> courseSessions;
    private LiveData<List<CoursePost>> coursePosts;


    public DatabaseViewModel(Application application){
        super(application);
        Database database = Database.getDatabase(application);
        databaseDao = database.databaseDao();
        courseList = databaseDao.getCourseList();
        coursePosts = databaseDao.getCoursePosts();
        courseSessions = databaseDao.getCourseSessions();
    }

    public LiveData<List<Course>> getCourseList(){
        return courseList;
    }

    public LiveData<List<CoursePost>> getCoursePosts(){
        return coursePosts;
    }

    public LiveData<List<CourseSession>> getCourseSessions(){return courseSessions;}
}

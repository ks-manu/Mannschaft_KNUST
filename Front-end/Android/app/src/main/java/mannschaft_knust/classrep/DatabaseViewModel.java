package mannschaft_knust.classrep;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {
    private Database database;
    private DatabaseDao databaseDao;
    private LiveData<List<CourseLecturer>> courseList;
    private LiveData<List<Post>> posts;

    public DatabaseViewModel(Application application){
        super(application);
        database = new Database() {
            @Override
            public DatabaseDao databaseDao() {
                return null;
            }

            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }
        };

        databaseDao = database.databaseDao();
        courseList = databaseDao.getCoursesList();
        posts = databaseDao.getPosts();
    }

    public LiveData<List<CourseLecturer>> getCourseList(){
        return courseList;
    }

    public LiveData<List<Post>> getPosts(){
        return posts;
    }

    public void insertPost(Post post){
        databaseDao.insertPost(post);
    }

    public void insetCourseSession(CourseSession courseSession){
        databaseDao.insertCourseSession(courseSession);
    }

    public void updateCourseSession(CourseSession courseSession){
        databaseDao.updateCourseSession(courseSession);
    }

    public void deletCourseSession(CourseSession courseSession){
        databaseDao.deleteCourseSession(courseSession);
    }
}

package mannschaft_knust.classrep;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {
    private DatabaseDao databaseDao;
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

    //course list operations
    public LiveData<List<Course>> getCourseList(){
        return courseList;
    }

    //course post operations
    public LiveData<List<CoursePost>> getCoursePosts(){
        return coursePosts;
    }
    public void insertPost(CoursePost post){new insertPostAsyncTask(databaseDao).execute(post);}

    //course session operations
    public LiveData<List<CourseSession>> getCourseSessions(){return courseSessions;}
    public void insertCourseSession(CourseSession courseSession){
        databaseDao.insertCourseSession(courseSession);}
    public void updateCourseSession(CourseSession courseSession){
        databaseDao.updateCourseSession(courseSession);
    }
    public void deleteCourseSession(CourseSession courseSession){
        databaseDao.deleteCourseSession(courseSession);
    }

    //on sign out
    public void deleteAll(){new deleteAllAsyncTask(databaseDao);}

    private static class insertPostAsyncTask extends AsyncTask<CoursePost, Void, Void> {

        private DatabaseDao databaseDao;

        insertPostAsyncTask(DatabaseDao databaseDao) {
            this.databaseDao = databaseDao;
        }

        @Override
        protected Void doInBackground(final CoursePost... params) {
            databaseDao.insertCoursePost(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private DatabaseDao databaseDao;

        deleteAllAsyncTask(DatabaseDao databaseDao) {
            this.databaseDao = databaseDao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            databaseDao.deleteAllCoursePosts();
            databaseDao.deleteAllCourseSessions();
            return null;
        }
    }
}

package mannschaft_knust.classrep;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;


public class CoursePostsViewModel extends AndroidViewModel {
    DatabaseDao databaseDao;
    private LiveData<List<CoursePost>> coursePosts;


    public CoursePostsViewModel(Application application){
        super(application);
        Database database = Database.getDatabase(application);
        databaseDao = database.databaseDao();
        coursePosts = databaseDao.getPost("%_%");
    }

    public LiveData<List<CoursePost>> getCoursePosts(){
        return coursePosts;
    }

    public void changePostSet(String courseAndCode){
        coursePosts = databaseDao.getPost('%'+courseAndCode+'%');
    }
}

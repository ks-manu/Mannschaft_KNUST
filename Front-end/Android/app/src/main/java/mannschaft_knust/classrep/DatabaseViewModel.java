package mannschaft_knust.classrep;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {

    private DataRepository dataRepository;

    public DatabaseViewModel(Application application){
        super(application);
        dataRepository = new DataRepository(application);
    }

    //get user data
    public User getUser(){return dataRepository.getUser();}

    //course list operations
    public LiveData<List<Course>> getCourseList(){
        return dataRepository.getCourseList();
    }

    //course post operations
    public LiveData<List<CoursePost>> getCoursePosts(){
        return dataRepository.getCoursePosts();
    }
    public void insertPost(CoursePost post){ dataRepository.insertPost(post);}
    public void voteOnPost(CoursePost post, CoursePost.UserVote userVote){
        dataRepository.voteOnPost(post, userVote);
    }

    //course session operations
    public LiveData<List<CourseSession>> getCourseSessions(){return dataRepository.getCourseSessions();}
    public void insertCourseSession(CourseSession courseSession){
        dataRepository.insertCourseSession(courseSession);
    }

    //on sign out delete all
    public void deleteAll(){
        dataRepository.deleteAll();}
}

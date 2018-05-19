package mannschaft_knust.classrep;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


class DataRepository {

    private User user = new User();
    private DatabaseDao databaseDao;
    private LiveData<List<Course>> courseList;
    private LiveData<List<CourseSession>> courseSessions;
    private LiveData<List<CoursePost>> coursePosts;
    private Application application;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://classrep.com")
            .addConverterFactory(GsonConverterFactory
                    .create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                            .create())
            )
            .build();
    private DataWebService dataWebService = retrofit.create(DataWebService.class);



    DataRepository(Application application){
        Database database = Database.getDatabase(application);
        databaseDao = database.databaseDao();
        courseList = databaseDao.getCourseList();
        coursePosts = databaseDao.getCoursePosts();
        courseSessions = databaseDao.getCourseSessions();
        this.application = application;

        //instantiating user object
        SharedPreferences sharedPreferences = application
                .getSharedPreferences("mannschaft_knust.classrep.USER_PREF", MODE_PRIVATE);
        if (sharedPreferences.getString("user type","").equals("Instructor")){
            user = new UserInstructor();
            user.userType = "Instructor";
            ((UserInstructor) user)
                    .techMail = sharedPreferences.getString("userID","");
            ((UserInstructor) user)
                    .title = sharedPreferences.getString("title", "");
        }
        else if (sharedPreferences.getString("user type","").equals("Student")){
            user = new UserStudent();
            user.userType = "Student";
            ((UserStudent) user)
                    .indexNumber = Integer.parseInt(sharedPreferences.getString("userID","0"));
            ((UserStudent) user)
                    .programmeAndYear = sharedPreferences.getString("programme(year)", "");
        }
        user.password = sharedPreferences.getString("password", "");
        user.firstName = sharedPreferences.getString("first name", "");
        user.lastName = sharedPreferences.getString("last name", "");
        user.token = sharedPreferences.getString("token", "");

    }

    //get user object
    public User getUser(){return user;}

    //course list operations
    public LiveData<List<Course>> getCourseList(){
        updateCourseSession();
        return courseList;
    }

    //course post operations
    public LiveData<List<CoursePost>> getCoursePosts(){
        updateCoursePosts();
        return coursePosts;
    }
    public void insertPost(CoursePost post){
        new insertPostAsyncTask(databaseDao).execute(post);
        sendCoursePost(post);
    }
    public void voteOnPost(CoursePost post, CoursePost.UserVote userVote){
        post.userVote = userVote;
        sendVote(post);
    }

    //course session operations
    public LiveData<List<CourseSession>> getCourseSessions(){
        updateCourseSession();
        return courseSessions;
    }
    public void insertCourseSession(final CourseSession courseSession){
        sendCourseSession(courseSession);
    }

    //delete all for sign out
    public void deleteAll(){new deleteAllAsyncTask(databaseDao);}

    //web services
    private void updateCourseSession(){
        //pulling course sessions
        Call<List<CourseSession>> call;
        if (user.userType.equals("Student")){
            call = dataWebService
                    .getCourseSessions(user.token,((UserStudent)user).programmeAndYear);
        }
        else call = dataWebService
                .getCourseSessions(user.token, ((UserInstructor) user).techMail);
        call.enqueue(new Callback<List<CourseSession>>(){

            public void onResponse(@NonNull Call<List<CourseSession>> call,@NonNull Response<List<CourseSession>> response){
                if(response.isSuccessful() && response.body() != null) {
                    databaseDao.deleteAllCourseSessions();
                    for (CourseSession courseSession : response.body()){

                        if(user.userType.equals("Instructor"))
                            courseSession.participants = courseSession.programmeAndYear;
                        else courseSession.participants = courseSession.techMail;

                        databaseDao.insertCourseSession(courseSession);
                    }
                }
            }

            public void onFailure(@NonNull Call<List<CourseSession>> call,@NonNull Throwable t){
                Toast.makeText(application,"No Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateCoursePosts(){
        //pulling course post
        Call<List<CoursePost>> call = dataWebService
                .getCoursePosts(user.token, "sf", null);
        call.enqueue(new Callback<List<CoursePost>>(){

            public void onResponse(@NonNull Call<List<CoursePost>> call,@NonNull Response<List<CoursePost>> response){
                if(response.isSuccessful() && response.body() != null) {
                    for (CoursePost coursePost : response.body())
                        databaseDao.insertCoursePost(coursePost);
                }
            }

            public void onFailure(@NonNull Call<List<CoursePost>> call,@NonNull Throwable t){
                Toast.makeText(application,"No Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendCoursePost(CoursePost post){
        //push course post
        Call<CoursePost> call = dataWebService.sendCoursePost(user.token,post);
        call.enqueue(new Callback<CoursePost>(){

            public void onResponse(@NonNull Call<CoursePost> call, @NonNull Response<CoursePost> response){
                if(response.isSuccessful()) {
                    updateCoursePosts();
                }
                else call.clone().enqueue(this);
            }

            public void onFailure(@NonNull Call<CoursePost> call,@NonNull Throwable t){
                call.clone().enqueue(this);
            }
        });
    }
    private void sendCourseSession(CourseSession courseSession){
        //push course sessions
        Call<CourseSession> call = dataWebService.sendCourseSession(user.token,courseSession);
        call.enqueue(new Callback<CourseSession>(){

            public void onResponse(@NonNull Call<CourseSession> call,@NonNull Response<CourseSession> response){
                if(response.isSuccessful()) {
                    updateCourseSession();
                }
            }

            public void onFailure(@NonNull Call<CourseSession> call,@NonNull Throwable t){
                Toast.makeText(application,"No Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendVote(final CoursePost post){
        UserVote userVote = new UserVote();
        userVote.indexNumber = ((UserStudent) user).indexNumber;
        userVote.postID = post.postID;

        if(post.userVote == CoursePost.UserVote.FOR)
            userVote.vote = "YES";
        else userVote.vote = "NO";

        Call<UserVote> call = dataWebService.sendVote(user.token, userVote);
        call.enqueue(new Callback<UserVote>(){

            public void onResponse(@NonNull Call<UserVote> call, @NonNull Response<UserVote> response){
                if(response.isSuccessful()) {
                    new insertPostAsyncTask(databaseDao).execute(post);
                    updateCoursePosts();
                }
            }

            public void onFailure(@NonNull Call<UserVote> call,@NonNull Throwable t){
                Toast.makeText(application,"No Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //async task for database insertion and deletion
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

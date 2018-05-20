package mannschaft_knust.classrep;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


class DataRepository {

    MutableLiveData<Boolean> updateRequestCalled = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private DatabaseDao databaseDao;
    private LiveData<List<Course>> courseList;
    private LiveData<List<CourseSession>> courseSessions;
    private LiveData<List<CoursePost>> coursePosts;
    private Context context;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.42.0.1:5555/")
            .addConverterFactory(GsonConverterFactory
                    .create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                            .registerTypeAdapter(Timestamp.class, new TimestampSerializer())
                            .registerTypeAdapter(Time.class, new TimeSerializer())
                            .registerTypeAdapter(Timestamp.class, new TimestampDeserializer())
                            .registerTypeAdapter(Time.class, new TimeDeserializer())
                            .create())
            )
            .build();
    private DataWebService dataWebService = retrofit.create(DataWebService.class);

    private class TimestampSerializer implements JsonSerializer<Timestamp>{
        public JsonElement serialize(Timestamp timestamp, Type type, JsonSerializationContext context){
            return new JsonPrimitive(timestamp.toString());
        }
    }
    private class TimeSerializer implements JsonSerializer<Time>{
        public JsonElement serialize(Time time, Type type, JsonSerializationContext context){
            return new JsonPrimitive(time.toString());
        }
    }
    private class TimestampDeserializer implements JsonDeserializer<Timestamp>{
        public Timestamp deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context){
            return Timestamp.valueOf(jsonElement.getAsJsonPrimitive().getAsString());
        }
    }
    private class TimeDeserializer implements JsonDeserializer<Time>{
        public Time deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context){
            return Time.valueOf(jsonElement.getAsJsonPrimitive().getAsString());
        }
    }


    DataRepository(Context context){
        updateRequestCalled.setValue(Boolean.TRUE);

        Database database = Database.getDatabase(context);
        databaseDao = database.databaseDao();
        courseList = databaseDao.getCourseList();
        coursePosts = databaseDao.getCoursePosts();
        courseSessions = databaseDao.getCourseSessions();
        this.context = context;

        //instantiating user object
        SharedPreferences sharedPreferences = context
                .getSharedPreferences("mannschaft_knust.classrep.USER_PREF", MODE_PRIVATE);
        User user = new User();
        if (sharedPreferences.getString("user type","").equals("Lecturer")){
            user.userType = "Lecturer";
            user.techMail = sharedPreferences.getString("userID","");
            user.title = sharedPreferences.getString("title", "");
        }
        else if (sharedPreferences.getString("user type","").equals("Student")){
            user.userType = "Student";
            user.indexNumber = Integer.parseInt(sharedPreferences.getString("userID","0"));
            user.programmeAndYear = sharedPreferences.getString("programme(year)", "");
            user.college = sharedPreferences.getString("college", "");
        }
        user.firstName = sharedPreferences.getString("first name", "");
        user.lastName = sharedPreferences.getString("last name", "");
        user.token = sharedPreferences.getString("token", "");
        this.user.setValue(user);
    }

    //offline operations
    public MutableLiveData<User> getUser(){return user;}

    public LiveData<List<Course>> getCourseList(){ return courseList;}

    public LiveData<List<CoursePost>> getCoursePosts(){return coursePosts; }
    public void insertPost(CoursePost post){
        new insertPostAsyncTask(databaseDao).execute(post);
        sendCoursePost(post);
    }
    public void voteOnPost(CoursePost post, CoursePost.UserVote userVote){
        post.userVote = userVote;
        sendVote(post);
    }
    public LiveData<List<CourseSession>> getCourseSessions(){ return courseSessions; }

    public void insertCourseSession(final CourseSession courseSession){
        sendCourseSession(courseSession);
    }
    public void deleteAll(){new deleteAllAsyncTask(databaseDao);}

    //web services
    public void signIn(final String userType, final String userID, String password){
        Call<ResponseBody> call  = dataWebService.signIn(userType,userID,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    User nUser = new User();
                    try{
                        nUser.token = response.body().string();
                    }
                    catch (IOException e){
                        Toast.makeText(context,"error IO", Toast.LENGTH_SHORT).show();
                    }
                    user.setValue(nUser);
                    getBioData(nUser.token, userType, userID);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getBioData(final String token,final String userType, final String userID){
        Call<User> call  = dataWebService.getBioData(token,userType,userID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    SharedPreferences userPref = context
                            .getSharedPreferences("mannschaft_knust.classrep.USER_PREF"
                                    , MODE_PRIVATE);

                    if(userType.equals("Lecturer")){
                        userPref.edit().putString("user type", userType)
                                .putString("token", token)
                                .putString("userID", userID)
                                .putString("first name", user.firstName)
                                .putString("last name", user.lastName)
                                .putString("title", user.title)
                                .apply();
                    }
                    else
                        userPref.edit().putString("user type", userType)
                                .putString("token", token)
                                .putString("userID", userID)
                                .putString("first name", user.firstName)
                                .putString("last name", user.lastName)
                                .putString("programme(year)", user.programmeAndYear)
                                .putString("college", user.college)
                                .apply();
                    context.startActivity(new Intent(context,MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean signOut(String userType, String token){
        try{
            return dataWebService.signOut(userType,token,token).execute().isSuccessful();
        }
        catch(IOException e){
            Toast.makeText(context,"Server error", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void updateCourseSession(){
        updateRequestCalled.setValue(Boolean.FALSE);
        //pulling course sessions
        Call<List<CourseSession>> call;
        if (user.getValue().userType.equals("Student")){
            call = dataWebService
                    .getCourseSessions(user.getValue().token,user.getValue().programmeAndYear);
        }
        else call = dataWebService
                .getCourseSessions(user.getValue().token, user.getValue().techMail);
        call.enqueue(new Callback<List<CourseSession>>(){

            public void onResponse(@NonNull Call<List<CourseSession>> call,@NonNull Response<List<CourseSession>> response){
                updateRequestCalled.setValue(Boolean.TRUE);
                if(response.isSuccessful() && response.body() != null) {
                    databaseDao.deleteAllCourseSessions();
                    for (CourseSession courseSession : response.body()){

                        if(user.getValue().userType.equals("Lecturer"))
                            courseSession.participants = courseSession.programmeAndYear;
                        else courseSession.participants = courseSession.techMail;

                        databaseDao.insertCourseSession(courseSession);
                    }
                }
            }

            public void onFailure(@NonNull Call<List<CourseSession>> call,@NonNull Throwable t){
                updateRequestCalled.setValue(Boolean.TRUE);
                Toast.makeText(context,"No Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateCoursePosts(){
        updateRequestCalled.setValue(Boolean.FALSE);
        ArrayList<Object> callAndCallback= new ArrayList<>();
        //pulling course post
        Call<List<CoursePost>> call = dataWebService
                .getCoursePosts(user.getValue().token, "sf", null);
        CustomCallback<List<CoursePost>> callback = new CustomCallback<List<CoursePost>>(){
            boolean newData = false;
            boolean calledBack = false;
            List<CoursePost> newCoursePosts;

            public void onResponse(@NonNull Call<List<CoursePost>> call,@NonNull Response<List<CoursePost>> response){
                updateRequestCalled.setValue(Boolean.TRUE);
                if(response.isSuccessful() && response.body() != null) {
                    newData = true;
                    calledBack = true;
                    for (CoursePost coursePost : response.body()){
                        newCoursePosts.add(coursePost);
                        databaseDao.insertCoursePost(coursePost);
                    }
                }
            }

            public void onFailure(@NonNull Call<List<CoursePost>> call,@NonNull Throwable t){
                updateRequestCalled.setValue(Boolean.TRUE);
                calledBack = true;
                Toast.makeText(context,"No Network", Toast.LENGTH_SHORT).show();
            }

            public boolean hasBeenCalled(){return calledBack;}
            public boolean hasNewData(){return newData;}
            public List<CoursePost> getNewData(){
                return newCoursePosts;
            }
        };
        call.enqueue(callback);

        new PostNotificationAsyncTask(callback).execute();
    }
    private void sendCoursePost(CoursePost post){
        //push course post
        Call<CoursePost> call = dataWebService.sendCoursePost(user.getValue().token,post);
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
        Call<CourseSession> call = dataWebService.sendCourseSession(user.getValue().token,courseSession);
        call.enqueue(new Callback<CourseSession>(){

            public void onResponse(@NonNull Call<CourseSession> call,@NonNull Response<CourseSession> response){
                if(response.isSuccessful()) {
                    Toast.makeText(context,"Changes applied successfully"
                            ,Toast.LENGTH_SHORT).show();
                    updateCourseSession();
                }
            }

            public void onFailure(@NonNull Call<CourseSession> call,@NonNull Throwable t){
                Toast.makeText(context,"No Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendVote(final CoursePost post){
        UserVote userVote = new UserVote();
        userVote.indexNumber = user.getValue().indexNumber;
        userVote.postID = post.postID;

        if(post.userVote == CoursePost.UserVote.FOR)
            userVote.vote = "YES";
        else userVote.vote = "NO";

        Call<UserVote> call = dataWebService.sendVote(user.getValue().token, userVote);
        call.enqueue(new Callback<UserVote>(){

            public void onResponse(@NonNull Call<UserVote> call, @NonNull Response<UserVote> response){
                if(response.isSuccessful()) {
                    new insertPostAsyncTask(databaseDao).execute(post);
                    updateCoursePosts();
                }
            }

            public void onFailure(@NonNull Call<UserVote> call,@NonNull Throwable t){
                Toast.makeText(context,"No Network", Toast.LENGTH_SHORT).show();
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

    //async task for notifications
    private static class PostNotificationAsyncTask extends AsyncTask<Void, Void, Void> {

        List<CoursePost> newCoursePosts;
        CustomCallback<List<CoursePost>> customCallback;

        PostNotificationAsyncTask(CustomCallback<List<CoursePost>> customCallback){
            this.customCallback = customCallback;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if(customCallback.hasBeenCalled()){
                if(customCallback.hasNewData()){
                    newCoursePosts =  customCallback.getNewData();
                }
                else return null;
            }
            else
                new PostNotificationAsyncTask(customCallback).execute();
            return null;
        }
    }
}

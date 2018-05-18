package mannschaft_knust.classrep;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DataWebService {
   //SignIn
    @POST("users/authlib/{userType}/reqID=sign_in")
    Call<User> signIn(String userType,@Body User user);

    //SignOut
    @POST("users/deauthlib/{userType}/reqID={token}")
    Call<User> signOut(String userType, String token, @Body User user);

    //signUp
    @POST("user/{userType}/reqID=sign_up")
    Call<User> signUp(String userType, @Body User user);

    // Post Requests
    // Sending a post
    @POST("data/systlab/post/reqID={token}")
    Call<CoursePost> sendCoursePost(@Path("token") String token, @Body CoursePost coursePost);
    // Send courseSession
    @POST("data/systlab/course/session/reqID={token}")
    Call<CourseSession> sendCourseSession(@Path("token") String token, @Body CourseSession courseSession);
    @POST("data/systlab/poll/reqID={token}")
    Call<UserVote> sendVote(@Path("token") String token, @Body UserVote userVote);

    //Get Requests
    //Get CoursePost
    @GET("data/post/reqID={token}/post/{messageID}/{time}")
    Call<List<CoursePost>> getCoursePosts(@Path("token") String token,@Path("messageID") String messageID, @Path("time") String time);
    //Get CourseSessions
    @GET("data/course/session/reqID={token}/share/{techmail}")
    Call<List<CourseSession>> getCourseSessions(@Path("token") String token,@Path("techmail") String techMail);
    //Get PollResults
    @GET("data/share/reqID={token}/poll/{messageID}")
    Call<List<UserVote>> getPollResult(String token, String messageID);
    //get BioData
    @GET("data/users/{userType}/share/reqID={token}")
    Call<User> getBioData(String userType, String token);

}

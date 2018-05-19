package mannschaft_knust.classrep;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebService {
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
    Call<CoursePost> sendCoursePost(String token, @Body CoursePost coursePost);
    // Send courseSession
    @POST("data/systlab/course/session/reqID={token}")
    Call<CourseSession> sendCourseSession(String token, @Body CourseSession courseSession);

    //Get Requests
    //Get CoursePost
    @GET("data/post/reqID={token}/post/{messageID}/{time}")
    Call<List<CoursePost>> getCoursePosts(String token, String messageID, String time);
    //Get PollResults
    @GET("data/share/reqID={token}/poll/{messageID}")
    Call<String> getPostRequest(String token, String messageID);
    //Get CourseSessions
    @GET("data/course/session/reqID={token}/share/{techmail}")
    Call<List<CourseSession>> getCourseSessions(String token, String techMail);
    //get BioData
    @GET("data/users/{userType}/share/reqID={token}")
    Call<User> getBioData(String userType, String token);

}

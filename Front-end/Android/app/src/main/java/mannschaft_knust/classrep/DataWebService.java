package mannschaft_knust.classrep;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DataWebService {
   //SignIn
    @FormUrlEncoded
    @POST("users/authlib/{userType}/reqID=sign_in")
    Call<ResponseBody> signIn(@Path("userType") String userType
            , @Field("UserID") String userID, @Field("Password") String password);

    //SignOut
    @POST("users/deauthlib/{userType}/reqID={token}")
    Call<User> signOut(@Path("userType") String userType, @Path("token") String token, @Body String bodyToken);

    //signUp
    @POST("user/{userType}/reqID=sign_up")
    Call<User> signUp(@Path("token") String userType, @Body User user);

    // Post Requests
    // Sending a post
    @POST("data/systlab/Post/reqID={token}")
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
    @GET("data/course/session/reqID={token}/share/{techmailOrProgrammeAndYear}")
    Call<List<CourseSession>> getCourseSessions(@Path("token") String token
            ,@Path("techmailOrProgrammeAndYear") String techMailOrProgramme);
    //Get PollResults
    @GET("data/share/reqID={token}/poll/{messageID}")
    Call<List<UserVote>> getPollResult(@Path("token") String token, @Path("token") String messageID);
    //get BioData
    @GET("data/users/share/reqID={token}/{userType}/{userID}")
    Call<User> getBioData(@Path("token") String token
            , @Path("userType") String userType,@Path("userID") String userID);
}

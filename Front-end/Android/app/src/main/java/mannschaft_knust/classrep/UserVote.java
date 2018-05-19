package mannschaft_knust.classrep;

import com.google.gson.annotations.Expose;

public class UserVote {
    @Expose int  indexNumber;
    @Expose String postID, vote;

    UserVote(){}
}

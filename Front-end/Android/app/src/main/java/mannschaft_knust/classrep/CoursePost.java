package mannschaft_knust.classrep;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.sql.Timestamp;

@Entity
class CoursePost {
    @PrimaryKey
    @NonNull
    String postID = "null";
    String message;
    @Nullable
    Timestamp timeSent;
    String sentBy;
    boolean hasAttachment;
    boolean voteable;
    boolean voteStatus;
    UserVote userVote;
    int totalVotes;

    enum UserVote{
        FOR,
        AGAINST,
        UNDECIDED
    }


    CoursePost(@NonNull String postID, String message, @Nullable Timestamp timeSent, String sentBy,
                      boolean hasAttachment, boolean voteable, boolean voteStatus,
                      UserVote userVote, int totalVotes){
        this.postID=postID;
        this.message=message;
        this.timeSent=timeSent;
        this.sentBy=sentBy;
        this.hasAttachment=hasAttachment;
        this.voteable=voteable;
        this.voteStatus=voteStatus;
        this.userVote=userVote;
        this.totalVotes=totalVotes;
    }

    CoursePost(){}
}

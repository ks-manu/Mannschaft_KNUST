package mannschaft_knust.classrep;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

@Entity
class CoursePost {
    @PrimaryKey
    @NonNull
    @Expose String postID = "null";
    @Expose String message;
    @Nullable
    @Expose Timestamp timeSent;
    @Expose String sentBy;
    @Expose @SerializedName("Attachment") boolean hasAttachment;
    @Expose boolean voteable;
    boolean voteStatus;
    UserVote userVote;
    int totalVotes;

    enum UserVote{
        FOR,
        AGAINST,
        UNDECIDED
    }

    @Ignore
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

    @Ignore
    CoursePost(@NonNull String postID, String message, @Nullable Timestamp timeSent, String sentBy,
               boolean hasAttachment, boolean voteable){
        this.postID=postID;
        this.message=message;
        this.timeSent=timeSent;
        this.sentBy=sentBy;
        this.hasAttachment=hasAttachment;
        this.voteable=voteable;
        this.voteStatus=voteable;
        this.userVote=UserVote.UNDECIDED;
        this.totalVotes=0;
    }

    CoursePost(){}
}

package mannschaft_knust.classrep;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
<<<<<<< HEAD
=======
import android.support.annotation.Nullable;
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2

import java.sql.Timestamp;

@Entity
class CoursePost {
    @PrimaryKey
    @NonNull
<<<<<<< HEAD
    String postID;
    String message;
=======
    String postID = "null";
    String message;
    @Nullable
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
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

    @Ignore
<<<<<<< HEAD
    CoursePost(String postID, String message, Timestamp timeSent, String sentBy,
=======
    CoursePost(@NonNull String postID, String message, @Nullable Timestamp timeSent, String sentBy,
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
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

<<<<<<< HEAD
=======
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

>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
    CoursePost(){}
}

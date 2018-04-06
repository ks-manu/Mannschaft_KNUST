package mannschaft_knust.classrep;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity
class Post {
    @PrimaryKey
    @NonNull
    String postID;
    String message;
    String timeSent;
    String sentBy;
    boolean hasAttachment;
    boolean voteable;
    boolean voteStatus;
    boolean userVote;
    int totalVotes;
}

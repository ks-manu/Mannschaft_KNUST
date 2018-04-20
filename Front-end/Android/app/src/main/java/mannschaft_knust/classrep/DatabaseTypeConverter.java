package mannschaft_knust.classrep;

import android.arch.persistence.room.TypeConverter;

import java.sql.Time;
import java.sql.Timestamp;

class DatabaseTypeConverter {
    //time <-> string
    @TypeConverter
    public static String stringFromTime(Time time){
        if(time != null)
            return time.toString();
        else
            return null;
    }
    @TypeConverter
    public static Time timeFromString(String string){
        if(string != null)
            return Time.valueOf(string);
        else
            return null;
    }

    //timestamp <-> string
    @TypeConverter
    public static String stringFromTimestamp(Timestamp timestamp){
        if(timestamp != null)
            return timestamp.toString();
        else
            return null;
    }
    @TypeConverter
    public static Timestamp timestampFromString(String string){
        if(string != null)
            return Timestamp.valueOf(string);
        else
            return null;
    }

    //CoursePost.UserVote <-> short
    @TypeConverter
    public static short shortFromUserVote(CoursePost.UserVote userVote){
        switch (userVote){
            case FOR:
                return 1;
            case AGAINST:
                return -1;
            case UNDECIDED:
                default:
                return 0;
        }
    }
    @TypeConverter
    public static CoursePost.UserVote userVoteFromShort(short userVote){
        switch (userVote){
            case 1:
                return CoursePost.UserVote.FOR;
            case -1:
                return CoursePost.UserVote.AGAINST;
            case 0:
            default:
                return CoursePost.UserVote.UNDECIDED;
        }
    }


}

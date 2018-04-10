package mannschaft_knust.classrep;

import android.arch.persistence.room.TypeConverter;

import java.sql.Time;
import java.sql.Timestamp;

class DatabaseTypeConverter {
    //time <-> string
    @TypeConverter
    public static String stringFromTime(Time time){
        return time.toString();
    }
    @TypeConverter
    public static Time timeFromString(String string){
        return Time.valueOf(string);
    }

    //date <-> string
    @TypeConverter
    public static String stringFromTimestamp(Timestamp timestamp){
        return timestamp.toString();
    }
    @TypeConverter
    public static Timestamp timestampFromString(String string){
        return Timestamp.valueOf(string);
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

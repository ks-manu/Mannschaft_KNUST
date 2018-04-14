package mannschaft_knust.classrep;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;

@Entity
class CourseSession extends Course {
    @PrimaryKey
    int courseSessionID;
    String day;
    Time startingTime;
    Time endingTime;
    String venue;

    @Ignore
    CourseSession (String courseAndCode, String programmeAndYear
            ,int courseSessionID,String day, Time startingTime, Time endingTime, String venue ){
        super(courseAndCode,programmeAndYear);
        this.courseSessionID=courseSessionID;
        this.day=day;
        this.startingTime=startingTime;
        this.endingTime=endingTime;
        this.venue=venue;
    }

    CourseSession(){
        super();
    }
}

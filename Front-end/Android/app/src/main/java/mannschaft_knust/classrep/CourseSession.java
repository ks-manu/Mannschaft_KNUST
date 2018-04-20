package mannschaft_knust.classrep;

import android.arch.persistence.room.Entity;
<<<<<<< HEAD
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
class CourseSession extends CourseLecturer{
    @PrimaryKey
    int courseSessionID;
    String day;
    String startingTime;
    String endingTime;
    String venue;
    String programmeAndYear;
=======
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
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
}

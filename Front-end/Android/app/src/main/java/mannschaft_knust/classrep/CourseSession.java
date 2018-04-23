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

<<<<<<< HEAD
    CourseSession (String courseAndCode, String techmail, String programmeAndYear
            ,int courseSessionID,String day, Time startingTime, Time endingTime, String venue ){
        super(courseAndCode,techmail,programmeAndYear);
=======
    @Ignore
    CourseSession (String courseAndCode, String programmeAndYear
            ,int courseSessionID,String day, Time startingTime, Time endingTime, String venue ){
        super(courseAndCode,programmeAndYear);
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
        this.courseSessionID=courseSessionID;
        this.day=day;
        this.startingTime=startingTime;
        this.endingTime=endingTime;
        this.venue=venue;
    }
<<<<<<< HEAD
=======

    CourseSession(){
        super();
    }
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
}

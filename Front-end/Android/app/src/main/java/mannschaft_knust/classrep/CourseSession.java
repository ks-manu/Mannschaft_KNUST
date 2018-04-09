package mannschaft_knust.classrep;

import android.arch.persistence.room.Entity;
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
}

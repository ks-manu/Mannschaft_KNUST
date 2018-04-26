package mannschaft_knust.classrep;

import android.arch.persistence.room.Ignore;

public class Course {
    public String courseAndCode;
    public String participants;

    @Ignore
    Course(String courseAndCode, String participants){
        this.courseAndCode = courseAndCode;
        this.participants = participants;
    }

    Course(){}
}

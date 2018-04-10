package mannschaft_knust.classrep;

import android.arch.persistence.room.Ignore;

public class Course {
    public String courseAndCode;
    public String techmail;
    public String programmeAndYear;

    @Ignore
    Course(String courseAndCode, String techmail, String programmeAndYear){
        this.courseAndCode = courseAndCode;
        this.techmail = techmail;
        this.programmeAndYear = programmeAndYear;
    }

    Course(){}
}

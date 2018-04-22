package mannschaft_knust.classrep;

import android.arch.persistence.room.Ignore;

public class Course {
    public String courseAndCode;
<<<<<<< HEAD
    public String techmail;
    public String programmeAndYear;

    @Ignore
    Course(String courseAndCode, String techmail, String programmeAndYear){
        this.courseAndCode = courseAndCode;
        this.techmail = techmail;
        this.programmeAndYear = programmeAndYear;
=======
    public String participants;

    @Ignore
    Course(String courseAndCode, String participants){
        this.courseAndCode = courseAndCode;
        this.participants = participants;
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
    }

    Course(){}
}

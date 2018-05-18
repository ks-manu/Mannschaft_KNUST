package mannschaft_knust.classrep;

import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Course {
    @Expose @SerializedName("Course(Code)")  String courseAndCode;
    @Ignore @Expose String programmeAndYear, techMail;
    String participants;

    @Ignore
    Course(String courseAndCode,String participants){
        this.courseAndCode = courseAndCode;
        this.participants = participants;
    }

    Course(){}
}

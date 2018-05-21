package mannschaft_knust.classrep;

import com.google.gson.annotations.Expose;

class User {
    String token;
    String userType;
    @Expose String firstName, lastName;

    //lecturer
    @Expose String techMail, title;

    //student
    @Expose int indexNumber;
    @Expose String programmeAndYear, college;
}

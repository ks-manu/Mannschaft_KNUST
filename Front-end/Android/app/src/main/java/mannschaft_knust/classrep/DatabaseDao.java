package mannschaft_knust.classrep;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
<<<<<<< HEAD
=======
import android.arch.persistence.room.RoomWarnings;
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Insert
<<<<<<< HEAD
    void insertPost(Post post);
=======
    void insertCoursePost(CoursePost coursePost);
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec

    @Insert
    void insertCourseSession(CourseSession courseSession);
    @Update
    void updateCourseSession(CourseSession courseSession);
    @Delete
    void deleteCourseSession(CourseSession courseSession);

<<<<<<< HEAD
    @Query("SELECT DISTINCT courseAndCode,techmail FROM CourseSession")
    LiveData<List<CourseLecturer>> getCoursesList();

    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getPosts();
=======
    @Query("DELETE FROM coursesession")
    void deleteAllCourseSessions();
    @Query("DELETE FROM coursepost")
    void deleteAllCoursePosts();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM CourseSession")
    LiveData<List<Course>> getCourseList();

    @Query("SELECT * FROM CourseSession")
    LiveData<List<CourseSession>> getCourseSessions();

    @Query("SELECT * FROM CoursePost")
    LiveData<List<CoursePost>> getCoursePosts();
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
}

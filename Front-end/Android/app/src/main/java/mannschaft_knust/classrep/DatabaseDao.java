package mannschaft_knust.classrep;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Insert
    void insertPost(CoursePost coursePost);

    @Insert
    void insertCourseSession(CourseSession courseSession);
    @Update
    void updateCourseSession(CourseSession courseSession);
    @Delete
    void deleteCourseSession(CourseSession courseSession);
    @Query("DELETE FROM coursesession")
    void deleteAllCourseSessions();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM CourseSession")
    LiveData<List<Course>> getCourseList();

    @Query("SELECT * FROM CoursePost WHERE postID LIKE :courseAndCode")
    LiveData<List<CoursePost>> getPost(String courseAndCode);
}

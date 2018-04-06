package mannschaft_knust.classrep;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Insert
    void insertPost(Post post);

    @Insert
    void insertCourseSession(CourseSession courseSession);
    @Update
    void updateCourseSession(CourseSession courseSession);
    @Delete
    void deleteCourseSession(CourseSession courseSession);

    @Query("SELECT DISTINCT courseAndCode,techmail FROM CourseSession")
    LiveData<List<CourseLecturer>> getCoursesList();

    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getPosts();
}

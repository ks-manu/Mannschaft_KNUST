package mannschaft_knust.classrep;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.sql.Time;
import java.sql.Timestamp;

@android.arch.persistence.room.Database(entities = {CourseSession.class,CoursePost.class},
        version = 1, exportSchema = false)
@TypeConverters({DatabaseTypeConverter.class})
public abstract class Database extends RoomDatabase {

    public abstract DatabaseDao databaseDao();

    private static Database INSTANCE;

    public static Database getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DatabaseDao databaseDao;

        PopulateDbAsync(Database db) {
            databaseDao = db.databaseDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            databaseDao.deleteAllCourseSessions();
            databaseDao.insertCourseSession(new CourseSession("Autotronics_COE265"
                    , "Computer_3",1
                    , "Monday",new Time((long)2.88e+7),new Time((long)3.6e+7), "PB012"));

            databaseDao.insertCourseSession(new CourseSession("Software_COE265"
                    , "Computer_3",2
                    , "Tuesday",new Time((long)2.88e+7),new Time((long)3.6e+7), "PB012"));

            databaseDao.deleteAllCoursePosts();
            databaseDao.insertCoursePost(new CoursePost("Autotronics_COE265","a message in autotronics",
                   null,"yankee@techmail.com",
                            true,true,true, CoursePost.UserVote.UNDECIDED,15));
            return null;
        }
    }
}

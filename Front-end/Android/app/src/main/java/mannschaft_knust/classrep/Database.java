package mannschaft_knust.classrep;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = {CourseSession.class,Post.class},
        version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract DatabaseDao databaseDao();

    private static Database INSTANCE;

    public static Database getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "database").build();
                }
            }
        }
        return INSTANCE;
    }
}

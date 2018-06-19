package codefathers.tripalert.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = (TrackingModel.class),version = 1 )
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static AppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"tripalert_db")
                    .build();
        }
        return  INSTANCE;
    }
    public  abstract TrackingModelDao trackingModel();
}

package com.example.ewidencja;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = WorkDay.class, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WorkDayDatabase extends RoomDatabase {

    private static WorkDayDatabase instance;
   private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
       @Override
       public void onCreate(@NonNull SupportSQLiteDatabase db) {
           super.onCreate(db);
           new PopulateDbAsyncTask(instance).execute();
       }
   };

    public static synchronized WorkDayDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorkDayDatabase.class, "workday_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract WorkDayDao workDayDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private WorkDayDao workDayDao;

        private PopulateDbAsyncTask(WorkDayDatabase db){workDayDao = db.workDayDao();}

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

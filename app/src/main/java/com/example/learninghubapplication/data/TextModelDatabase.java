package com.example.learninghubapplication.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.learninghubapplication.model.TextModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities={TextModel.class}, version =1, exportSchema = false)
public abstract class TextModelDatabase extends RoomDatabase {

    public abstract TextModelDao textModelDao();

    private static TextModelDatabase instance;


    static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TextModelDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (TextModelDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                                TextModelDatabase.class, "DATABASE")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }
}

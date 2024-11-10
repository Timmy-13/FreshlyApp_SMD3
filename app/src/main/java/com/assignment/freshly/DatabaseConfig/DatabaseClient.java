package com.assignment.freshly.DatabaseConfig;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    public FreshlyDatabase freshlyDatabase;
    private Context context;
    public static DatabaseClient databaseClient;

    private DatabaseClient(Context context){
        this.context = context;
        freshlyDatabase = Room.databaseBuilder(context, FreshlyDatabase.class, "Freshly-Database")
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if(databaseClient == null){
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }
}

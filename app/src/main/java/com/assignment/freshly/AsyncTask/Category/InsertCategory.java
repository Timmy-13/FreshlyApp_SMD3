package com.assignment.freshly.AsyncTask.Category;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Category;

public class InsertCategory extends AsyncTask<Category, Void, Long> {
    private Context context;

    public InsertCategory(Context context){
        this.context = context;
    }


    @Override
    protected Long doInBackground(Category... categories) {
        return DatabaseClient.getInstance(context).freshlyDatabase.categoryDao().insertCategory(categories[0]);
    }
}

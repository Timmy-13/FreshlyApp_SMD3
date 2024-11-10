package com.assignment.freshly.AsyncTask.Category;

import android.content.Context;
import android.os.AsyncTask;

import com.assignment.freshly.Dao.CategoryDao;
import com.assignment.freshly.DatabaseConfig.DatabaseClient;
import com.assignment.freshly.Entity.Category;

public class GetCategory extends AsyncTask<String, Void, Category> {
    private Context context;
    private GetCategoryListener getCategoryListener;

    public GetCategory(Context context, GetCategoryListener getCategoryListener){
        this.context = context;
        this.getCategoryListener = getCategoryListener;
    }

    @Override
    protected Category doInBackground(String... strings) {
        CategoryDao categoryDao = DatabaseClient.getInstance(context).freshlyDatabase.categoryDao();
        return categoryDao.getCategoryByName(strings[0]);
    }

    @Override
    protected void onPostExecute(Category category){
        if(category == null){
            getCategoryListener.onGetCategoryFailure();
        }
        else{
            getCategoryListener.onGetCategorySuccess(category.getCa_id());
        }
    }

    public interface GetCategoryListener{
        void onGetCategorySuccess(int id);
        void onGetCategoryFailure();
    }
}

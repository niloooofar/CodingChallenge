package cirtual.com.cirtualcodingchallenge.managers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import cirtual.com.cirtualcodingchallenge.HelperClasses.OnTransactionListener;
import cirtual.com.cirtualcodingchallenge.models.Person;

public class FetchTableManager {

    private static final String TAG = "FetchTableManager";
    private Context context;

    public FetchTableManager(Context context) {
        this.context = context;
    }

    public void fetchTableData(final OnTransactionListener<ArrayList<Person>> callback) {

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String jsonStr = loadJSONFromAsset();

                if (jsonStr != null) {
                    ArrayList<Person> newPeople = extractPersons(jsonStr);
                    callback.onSuccess(newPeople);
                } else {
                    callback.onFailure();
                }
            }
        }, 1500);
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data/friends.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private ArrayList<Person> extractPersons(String jsonStr) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> persons = new Gson().fromJson(jsonStr, listType);
        return persons;
    }
}


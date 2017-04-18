package de.cimdata.myamazingtraveldiary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.cimdata.myamazingtraveldiary.R;
import de.cimdata.myamazingtraveldiary.model.UserLocations;

/**
 * @author Max Weiss
 */
public class LocationsDAO extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DB_NAME = "UserLocations.db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_USERLOCATIONS = "CREATE TABLE locations " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "location VARCHAR(150) ," +
            "description VARCHAR(150) ," +
            "image VARCHAR(150), " +
            "weather VARCHAR(50), " +
            "temperature VARCHAR(50));";



    Context con;

    public LocationsDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        con=context;
    }

    public ArrayList<UserLocations> getAllLocations(){
        ArrayList<UserLocations> resultList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM locations";
        Cursor c = db.rawQuery(count, null);
        c.moveToFirst();
        int testcount = c.getInt(0);
        if(testcount>0) {

            Cursor res = db.rawQuery("SELECT * FROM locations", null);
            res.moveToFirst();


            int loc = res.getColumnIndexOrThrow("location");
            int desc = res.getColumnIndexOrThrow("description");
            int img = res.getColumnIndexOrThrow("image");
            int w = res.getColumnIndexOrThrow("weather");
            int t = res.getColumnIndexOrThrow("temperature");
            if (res.getString(loc) != null) {
                do {
                    UserLocations location = new UserLocations(res.getString(loc), res.getString(desc),
                            res.getString(img), res.getString(w), res.getString(t));
                    resultList.add(location);
                } while (res.moveToNext());
            }
            res.close();
        }else{
            UserLocations dummyLocation = new UserLocations("Berlin", "dummy", "dummy", "Weather", "Degree");
            resultList.add(dummyLocation);
        }

        return resultList;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERLOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE locations");
        onCreate(db);
    }

    public void insert (UserLocations userLocations){
        long rowId = -1;
        db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("location", userLocations.getLocation());
            values.put("description", userLocations.getText());
            values.put("image", userLocations.getImage());
            values.put("weather", userLocations.getWeather());
            values.put("temperature", userLocations.getTemperature());
        rowId = db.insert("locations", null, values);
    }
}

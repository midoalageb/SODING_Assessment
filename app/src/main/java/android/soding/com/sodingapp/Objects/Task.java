package android.soding.com.sodingapp.Objects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.soding.com.sodingapp.Helpers.Constants;
import android.soding.com.sodingapp.Helpers.TaskDbHelper;
import android.soding.com.sodingapp.Helpers.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mohamed Abd-Allah on 9/29/2017.
 *
 * Class representing a task object
 */

public class Task implements Parcelable{
    public int mID;
    public String mName, mDescription;
    public Calendar mDateCreated, mDateUpdated;

    public Task(int ID, String name, String description, Calendar dateCreated, Calendar dateUpdated){
        this.mID = ID;
        this.mName = name;
        this.mDescription = description;
        this.mDateCreated = Calendar.getInstance();
        this.mDateCreated.setTime(dateCreated.getTime());
        this.mDateUpdated = Calendar.getInstance();
        this.mDateUpdated.setTime(dateUpdated.getTime());
    }

    protected Task(Parcel in) {
        mID = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
        mDateCreated = Calendar.getInstance();
        mDateCreated.setTimeInMillis(in.readLong());
        mDateUpdated = Calendar.getInstance();
        mDateUpdated.setTimeInMillis(in.readLong());
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    /**
     * Adds this new task to DB
     * @param mDbHelper
     * @return row ID (integer)
     */
    public long add_entry(TaskDbHelper mDbHelper){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, this.mName);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION, this.mDescription);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATECREATED,
                Utils.get_String_from_Date(this.mDateCreated.getTime()));
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATEUPDATED,
                Utils.get_String_from_Date(this.mDateUpdated.getTime()));

        return db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
    }

    /**
     * Update current task in DB
     * @param mDbHelper
     */
    public void update_entry(TaskDbHelper mDbHelper){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_NAME, this.mName);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION, this.mDescription);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATEUPDATED,
                Utils.get_String_from_Date(Calendar.getInstance().getTime()));


        String selection = TaskContract.TaskEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(this.mID) };

        db.update(
                TaskContract.TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    /**
     * Deletes current task from DB
     * @param mDbHelper
     */
    public void delete(TaskDbHelper mDbHelper){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selection = TaskContract.TaskEntry._ID + " LIKE ?";

        String[] selectionArgs = { String.valueOf(this.mID) };

        db.delete(TaskContract.TaskEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets All tasks from DB
     * @param mDbHelper
     * @return ArrayList of Tasks
     */
    public static final ArrayList<Task> get_tasks(TaskDbHelper mDbHelper){
        ArrayList<Task> result = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_NAME_NAME,
                TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION,
                TaskContract.TaskEntry.COLUMN_NAME_DATECREATED,
                TaskContract.TaskEntry.COLUMN_NAME_DATEUPDATED
        };

        Cursor cursor = db.query(
                TaskContract.TaskEntry.TABLE_NAME,
                projection,                      // The columns to return
                null,                            // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                            // group by
                null,                            // filter by
                null                             // The sort order
        );

        while(cursor.moveToNext()) {
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(TaskContract.TaskEntry._ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_DESCRIPTION));
            String date_created = cursor.getString(
                    cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_DATECREATED));
            String date_updated = cursor.getString(
                    cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_DATEUPDATED));
            Calendar dateCreated = Calendar.getInstance();
            Calendar dateUpdated = Calendar.getInstance();

            try {
                dateCreated.setTime(Utils.get_Date_from_String(date_created));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                dateUpdated.setTime(Utils.get_Date_from_String(date_updated));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            result.add(new Task(id, name, description, dateCreated, dateUpdated));
        }
        cursor.close();
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeLong(mDateCreated.getTimeInMillis());
        parcel.writeLong(mDateUpdated.getTimeInMillis());
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(!(obj instanceof Task))  return false;
        Task object = (Task) obj;
        result = object.mID == this.mID &&
                object.mName.equals(this.mName) &&
                object.mDescription.equals(this.mDescription) &&
                object.mDateCreated.equals(this.mDateCreated) &&
                object.mDateUpdated.equals(this.mDateUpdated) ;
        return result;
    }
}


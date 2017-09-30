package android.soding.com.sodingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;
import android.soding.com.sodingapp.Helpers.Constants;
import android.soding.com.sodingapp.Helpers.TaskDbHelper;
import android.soding.com.sodingapp.Helpers.Utils;
import android.soding.com.sodingapp.Objects.Task;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.Service;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TaskInstrumentedTest {
    public static final String TEST_NAME = "NAME";
    public static final String TEST_NAME_2 = "NAME2";
    public static final String TEST_DESCRIPTION = "DESCRIPTION";
    public static final String TEST_DESCRIPTION_2 = "DESCRIPTION2";
    public static final String TEST_DATE_CREATED = "1980-01-25 23:00:00";
    public static final String TEST_DATE_CREATED_2 = "1981-01-25 23:00:00";
    public static final String TEST_DATE_UPDATED = "1980-01-26 23:00:00";
    public static final String TEST_DATE_UPDATED_2 = "1981-01-26 23:00:00";

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(Constants.DB_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    @Test
    public void testAddTask() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(Constants.DB_NAME);
        TaskDbHelper mDbHelper = new TaskDbHelper(appContext);
        assertNotNull(mDbHelper);

        Calendar date_created = Calendar.getInstance();
        date_created.setTime(Utils.get_Date_from_String(TEST_DATE_CREATED));

        Calendar date_updated = Calendar.getInstance();
        date_updated.setTime(Utils.get_Date_from_String(TEST_DATE_UPDATED));


        Task task = new Task(0, TEST_NAME, TEST_DESCRIPTION, date_created, date_updated);
        task.add_entry(mDbHelper);

        ArrayList<Task> tasks = Task.get_tasks(mDbHelper);
        assertEquals(tasks.size(), 1);

        assertEquals(tasks.get(0).mName, TEST_NAME);
        assertEquals(tasks.get(0).mDescription, TEST_DESCRIPTION);
        assertEquals(tasks.get(0).mDateCreated, date_created);
        assertEquals(tasks.get(0).mDateUpdated, date_updated);

        mDbHelper.close();
    }

    @Test
    public void testUpdateTask() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(Constants.DB_NAME);
        TaskDbHelper mDbHelper = new TaskDbHelper(appContext);
        assertNotNull(mDbHelper);

        Calendar date_created = Calendar.getInstance();
        date_created.setTime(Utils.get_Date_from_String(TEST_DATE_CREATED));

        Calendar date_updated = Calendar.getInstance();
        date_updated.setTime(Utils.get_Date_from_String(TEST_DATE_UPDATED));

        Task task = new Task(0, TEST_NAME, TEST_DESCRIPTION, date_created, date_updated);
        task.add_entry(mDbHelper);

        ArrayList<Task> tasks = Task.get_tasks(mDbHelper);
        assertEquals(tasks.size(), 1);

        assertEquals(tasks.get(0).mName, TEST_NAME);
        assertEquals(tasks.get(0).mDescription, TEST_DESCRIPTION);
        assertEquals(tasks.get(0).mDateCreated, date_created);
        assertEquals(tasks.get(0).mDateUpdated, date_updated);

        Task task1 = tasks.get(0);

        task1.mName = TEST_NAME_2;
        date_updated.setTime(Calendar.getInstance().getTime());
        task1.update_entry(mDbHelper);

        tasks = Task.get_tasks(mDbHelper);
        assertEquals(tasks.size(), 1);

        assertEquals(tasks.get(0).mName, TEST_NAME_2);
        assertEquals(tasks.get(0).mDescription, TEST_DESCRIPTION);
        assertEquals(tasks.get(0).mDateCreated, date_created);
        assertTrue(tasks.get(0).mDateUpdated.getTimeInMillis()
                - date_updated.getTimeInMillis() < 5000);

        task1.mName = TEST_NAME;
        task1.mDescription = TEST_DESCRIPTION_2;
        task1.update_entry(mDbHelper);

        tasks = Task.get_tasks(mDbHelper);
        assertEquals(tasks.size(), 1);

        assertEquals(tasks.get(0).mName, TEST_NAME);
        assertEquals(tasks.get(0).mDescription, TEST_DESCRIPTION_2);
        assertEquals(tasks.get(0).mDateCreated, date_created);
        assertTrue(tasks.get(0).mDateUpdated.getTimeInMillis()
                - date_updated.getTimeInMillis() < 5000);

        task1.mName = TEST_NAME;
        task1.mDescription = TEST_DESCRIPTION;
        task1.update_entry(mDbHelper);

        mDbHelper.close();
    }

    @Test
    public void testDeleteTask() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(Constants.DB_NAME);
        TaskDbHelper mDbHelper = new TaskDbHelper(appContext);
        assertNotNull(mDbHelper);

        Calendar date_created = Calendar.getInstance();
        date_created.setTime(Utils.get_Date_from_String(TEST_DATE_CREATED));

        Calendar date_updated = Calendar.getInstance();
        date_updated.setTime(Utils.get_Date_from_String(TEST_DATE_UPDATED));

        Task task = new Task(0, TEST_NAME, TEST_DESCRIPTION, date_created, date_updated);
        task.add_entry(mDbHelper);

        ArrayList<Task> tasks = Task.get_tasks(mDbHelper);
        assertEquals(tasks.size(), 1);

        assertEquals(tasks.get(0).mName, TEST_NAME);
        assertEquals(tasks.get(0).mDescription, TEST_DESCRIPTION);
        assertEquals(tasks.get(0).mDateCreated, date_created);
        assertEquals(tasks.get(0).mDateUpdated, date_updated);

        Task task1 = tasks.get(0);
        task1.delete(mDbHelper);


        tasks = Task.get_tasks(mDbHelper);
        assertEquals(tasks.size(), 0);

        mDbHelper.close();
    }

    @Test
    public void testParcelable() throws Exception {
        // Context of the app under test.
        Calendar date_created = Calendar.getInstance();
        date_created.setTime(Utils.get_Date_from_String(TEST_DATE_CREATED));

        Calendar date_updated = Calendar.getInstance();
        date_updated.setTime(Utils.get_Date_from_String(TEST_DATE_UPDATED));


        Task task = new Task(0, TEST_NAME, TEST_DESCRIPTION, date_created, date_updated);
        // Obtain a Parcel object and write the parcelable object to it:
        Parcel parcel = Parcel.obtain();
        task.writeToParcel(parcel, 0);

        // After you're done with writing, you need to reset the parcel for reading:
        parcel.setDataPosition(0);

        // Reconstruct object from parcel and asserts:
        Task createdFromParcel = Task.CREATOR.createFromParcel(parcel);
        assertEquals(task, createdFromParcel);

        task.mName="";
        assertNotEquals(task, createdFromParcel);
    }
}

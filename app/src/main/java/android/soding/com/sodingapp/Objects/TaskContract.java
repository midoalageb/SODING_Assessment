package android.soding.com.sodingapp.Objects;

import android.provider.BaseColumns;

/**
 * Created by Mohamed Abd-Allah on 9/29/2017.
 */

public final class TaskContract {
    private TaskContract() {}

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DATECREATED = "dateCreated";
        public static final String COLUMN_NAME_DATEUPDATED = "dateUpdated";
    }
}

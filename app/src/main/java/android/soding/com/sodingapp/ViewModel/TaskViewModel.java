package android.soding.com.sodingapp.ViewModel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.soding.com.sodingapp.Adapters.TaskAdapter;
import android.soding.com.sodingapp.Helpers.TaskDbHelper;
import android.soding.com.sodingapp.Objects.Task;

import java.util.ArrayList;

/**
 * Created by Mohamed Abd-Allah on 12/11/2017.
 */

public class TaskViewModel implements ViewModel {

    private Context context;
    private final ObservableArrayList<Task> data = new ObservableArrayList<Task>();
//    private ArrayList<Task> tasks;
    private TaskDbHelper mDbHelper;
    public TaskAdapter adapter;

    public TaskViewModel(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
        mDbHelper = new TaskDbHelper(context);
//        tasks = Task.get_tasks(mDbHelper);
        data.addAll(Task.get_tasks(mDbHelper));
        adapter = new TaskAdapter(data, context);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mDbHelper.close();
    }

    public void get_all_Tasks(){

    }

    public void add_Task(Task task, boolean update) {
        if(update){
            Task task1 = task.update_entry(mDbHelper);
            data.set(data.indexOf(task), task1);
        }else {
            task.add_entry(mDbHelper);
            data.add(task);
        }
        adapter.notifyDataSetChanged();
    }

    public void delete_Task(Task task) {
        task.delete(mDbHelper);
        data.remove(task);
        adapter.notifyDataSetChanged();
    }
}

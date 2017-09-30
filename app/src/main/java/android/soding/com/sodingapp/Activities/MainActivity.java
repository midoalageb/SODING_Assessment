package android.soding.com.sodingapp.Activities;

import android.os.Bundle;
import android.soding.com.sodingapp.Adapters.TaskAdapter;
import android.soding.com.sodingapp.Fragments.AddUpdateTaskFragment;
import android.soding.com.sodingapp.Helpers.SetupActivity;
import android.soding.com.sodingapp.Helpers.TaskDbHelper;
import android.soding.com.sodingapp.Objects.Task;
import android.soding.com.sodingapp.R;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskAdapterInterface,
        AddUpdateTaskFragment.OnFragmentInteractionListener, SetupActivity{

    TaskDbHelper mDbHelper;
    Toolbar toolbar;
    FloatingActionButton fab;
    RecyclerView rv_tasks;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets TaskDbHelper in onCreate and close it in onDestroy
        mDbHelper = new TaskDbHelper(getApplicationContext());
        setup_view_variables();
        setSupportActionBar(toolbar);
        setup_clickListeners();
        get_all_tasks();
    }

    /**
     * Gets all tasks from DB and list them in RecyclerView rv_tasks
     */
    private void get_all_tasks() {
        ArrayList<Task> tasks = Task.get_tasks(mDbHelper);
        TaskAdapter adapter = new TaskAdapter(tasks, this);
        rv_tasks.setAdapter(adapter);
    }

    /**
     * Shows AddUpdateTaskFragment dialog to either add a new task or edit existing task
     * @param task Task to edit or null to create a new one
     */
    private void add_Task(Task task) {
        DialogFragment add_update_fragment = AddUpdateTaskFragment.newInstance(
                task);
        add_update_fragment.show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Interface implementation for handling edit from RecyclerView TaskAdapter
     * @param task Task to edit
     */
    @Override
    public void TaskAdapterOnClickEdit(Task task) {
        add_Task(task);
    }

    /**
     * Interface implementation for handling edit from RecyclerView TaskAdapter
     * @param task Task to delete
     */
    @Override
    public void TaskAdapterOnClickDelete(Task task) {
        task.delete(mDbHelper);
        get_all_tasks();
    }

    /**
     * Callback from AddUpdateTaskFragment to update or add task
     * @param task Task to add or update
     * @param update Whether to update or add new task
     */
    @Override
    public void onFragmentInteraction(Task task, boolean update) {
        if(update){
            task.update_entry(mDbHelper);
        }else {
            task.add_entry(mDbHelper);
        }
        get_all_tasks();
    }

    @Override
    public void setup_view_variables() {
        toolbar = (Toolbar) findViewById(R.id.Toolbar_main);
        fab = (FloatingActionButton) findViewById(R.id.Fab_main);
        rv_tasks = (RecyclerView) findViewById(R.id.RV_main_contents);
        rv_tasks.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_tasks.setLayoutManager(mLayoutManager);
        // Add divider between RecyclerView items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_tasks.getContext(),
                mLayoutManager.getOrientation());
        rv_tasks.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void setup_clickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_Task(null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}

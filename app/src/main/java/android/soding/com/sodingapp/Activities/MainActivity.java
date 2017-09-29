package android.soding.com.sodingapp.Activities;

import android.os.Bundle;
import android.soding.com.sodingapp.Adapters.TaskAdapter;
import android.soding.com.sodingapp.Objects.Task;
import android.soding.com.sodingapp.R;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskAdapterInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar_main);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void TaskAdapterOnClickEdit(Task task) {

    }

    @Override
    public void TaskAdapterOnClickDelete(Task task) {

    }
}

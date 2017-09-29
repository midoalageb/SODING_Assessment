package android.soding.com.sodingapp.Adapters;

import android.content.Context;
import android.soding.com.sodingapp.Helpers.Constants;
import android.soding.com.sodingapp.Helpers.Utils;
import android.soding.com.sodingapp.Objects.Task;
import android.soding.com.sodingapp.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mohamed Abd-Allah on 9/30/2017.
 */


public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Task> mDataset;
    private TaskAdapterInterface mlistener;
    private LayoutInflater layoutInflater;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ib_edit, ib_delete;
        public TextView tv_name, tv_description, tv_created, tv_updated;
        private TaskAdapterInterface listener;

        public ViewHolder(View v, TaskAdapterInterface listener) {
            super(v);
            tv_name = (TextView) v.findViewById(R.id.TV_item_name);
            tv_description = (TextView) v.findViewById(R.id.TV_item_description);
            tv_created = (TextView) v.findViewById(R.id.TV_item_created);
            tv_updated = (TextView) v.findViewById(R.id.TV_item_updated);
            ib_edit = (ImageButton) v.findViewById(R.id.IB_item_update);
            ib_delete = (ImageButton) v.findViewById(R.id.IB_item_delete);
            this.listener = listener;
        }
    }

    public TaskAdapter(ArrayList<Task> myDataset, Context context) {
        if(context==null) return;
        mDataset = myDataset;
        this.context = context;
        try {
            this.mlistener = (TaskAdapterInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement TaskAdapterInterface");
        }
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = layoutInflater
                .inflate(R.layout.item_task, parent, false);
        ViewHolder vh = new ViewHolder(v, mlistener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderr, final int position) {
        final ViewHolder holder = (ViewHolder) holderr;
        holder.tv_name.setText(mDataset.get(position).mName);
        holder.tv_description.setText(mDataset.get(position).mDescription);
        holder.tv_created.setText(Utils.get_String_from_Date(mDataset.get(position).mDateCreated.getTime()));
        holder.tv_updated.setText(Utils.get_String_from_Date(mDataset.get(position).mDateUpdated.getTime()));
        holder.ib_edit.setOnClickListener(new MyClickListener(mDataset.get(position), Constants.ACTION_UPDATE));
        holder.ib_delete.setOnClickListener(new MyClickListener(mDataset.get(position), Constants.ACTION_DELETE));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface TaskAdapterInterface{
        public void TaskAdapterOnClickEdit(Task task);
        public void TaskAdapterOnClickDelete(Task task);
    }

    public class MyClickListener implements View.OnClickListener{
        Task task;
        String action;
        public MyClickListener(Task task, String action){
            this.task = task;
            this.action = action;
        }
        @Override
        public void onClick(View view) {
            if(action.equals(Constants.ACTION_UPDATE))
                mlistener.TaskAdapterOnClickEdit(task);
            else if(action.equals(Constants.ACTION_DELETE))
                mlistener.TaskAdapterOnClickDelete(task);
        }
    }
}

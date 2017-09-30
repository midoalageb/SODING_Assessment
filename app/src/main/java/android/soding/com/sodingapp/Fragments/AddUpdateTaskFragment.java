package android.soding.com.sodingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.soding.com.sodingapp.Helpers.SetupActivity;
import android.soding.com.sodingapp.Helpers.Utils;
import android.soding.com.sodingapp.Objects.Task;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.soding.com.sodingapp.R;
import android.widget.Button;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddUpdateTaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddUpdateTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUpdateTaskFragment extends DialogFragment implements SetupActivity, View.OnClickListener{
    private static final String ARG_PARAM1 = "TASK";

    View root_view;
    private Task task;

    TextInputLayout til_name;
    TextInputLayout til_description;

    Button btn_save;

    private OnFragmentInteractionListener mListener;

    public AddUpdateTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param task Parameter 1.
     * @return A new instance of fragment AddUpdateTaskFragment.
     */
    public static AddUpdateTaskFragment newInstance(Task task) {
        AddUpdateTaskFragment fragment = new AddUpdateTaskFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_add_update_task, container, false);
        setup_view_variables();
        set_values();
        setup_clickListeners();
        return root_view;
    }

    /**
     * If Task is not new (update), display task value in dialog
     */
    private void set_values() {
        if(task!=null){
            til_name.getEditText().setText(task.mName);
            til_description.getEditText().setText(task.mDescription);
        }
    }

    /**
     * Callback to MainActivity to add or update Task
     * @param task Task to add or update
     * @param update update task or add new
     */
    public void onButtonPressed(Task task, boolean update) {
        if (mListener != null) {
            mListener.onFragmentInteraction(task, update);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setup_view_variables() {
        til_name = (TextInputLayout) root_view.findViewById(R.id.TIL_Dialog_Add_update_name);
        til_description = (TextInputLayout) root_view.findViewById(R.id.TIL_Dialog_Add_update_description);
        btn_save = (Button) root_view.findViewById(R.id.Btn_Dialog_Add_update_save);
    }

    @Override
    public void setup_clickListeners() {
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Btn_Dialog_Add_update_save:
                if(check_inputs()){
                    if(task!=null){
                        task.mName = til_name.getEditText().getText().toString();
                        task.mDescription = til_description.getEditText().getText().toString();
                        onButtonPressed(task, true);
                    }else {
                        Calendar date_created = Calendar.getInstance();

                        Task mtask= new Task(0, til_name.getEditText().getText().toString(),
                                til_description.getEditText().getText().toString(), date_created,
                                date_created);
                        onButtonPressed(mtask, false);
                    }
                    dismiss();
                }
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Task task, boolean update);
    }

    /**
     * Checks if all fields are not empty and sets TextInputLayout error messages accordingly
     * @return True if all fields are not empty
     */
    private boolean check_inputs() {
        String name = til_name.getEditText().getText().toString();
        String description = til_description.getEditText().getText().toString();

        String name_empty_validation = Utils.validateEmpty(name);
        String description_empty_validation = Utils.validateEmpty(description);

        til_name.setErrorEnabled(false);
        til_description.setErrorEnabled(false);

        if (!name_empty_validation.equals("OK")) {
            til_name.setError(name_empty_validation);
            return false;
        }else if (!description_empty_validation.equals("OK")) {
            til_description.setError(description_empty_validation);
            return false;
        }
        return true;
    }

}

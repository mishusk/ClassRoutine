package fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.SubjectAdapter;
import tdd.classroutine.R;
import db_helper.SubjectDataSource;
import models.Model_Subject;

/**
 * Created by mishu on 8/11/2016.
 */
public class Fragment_conf_sub extends Fragment {
    private EditText subjectNameEt;
    private ImageButton addSubjectBtn;
    private ListView subjectNameListView;

    private ArrayList<Model_Subject> subjectNameArray;
    private Model_Subject modelSubject;
    private SubjectAdapter adapter;
    private SubjectDataSource dataSource;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_conf_sub, container, false);

        subjectNameEt = (EditText) view.findViewById(R.id.subjectNameEt_fragAddSub);
        addSubjectBtn = (ImageButton) view.findViewById(R.id.addSubjectAddNameBtn);
        subjectNameListView = (ListView) view.findViewById(R.id.subjectNameListView);

        dataSource = new SubjectDataSource(getActivity());
        subjectNameArray = new ArrayList<>();

        subjectNameArray = dataSource.getAllSubject();
        adapter = new SubjectAdapter(view.getContext(), subjectNameArray);
        subjectNameListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSubjectName(view);
            }
        });
        return view;
    }

    public void saveSubjectName(View view) {
        String subjectName = subjectNameEt.getText().toString();
        if (subjectNameEt.getText().toString().trim().isEmpty()) {
            subjectNameEt.setError("Enter Subject Name.");
        } else {
            modelSubject = new Model_Subject(subjectName);
            subjectNameEt.setError(null);

            boolean saveStatus = dataSource.saveSubject(modelSubject);
            subjectNameArray = dataSource.getAllSubject();
            adapter = new SubjectAdapter(view.getContext(), subjectNameArray);
            subjectNameListView.setAdapter(adapter);

            subjectNameEt.setText("");
            if (saveStatus) {
                Toast.makeText(view.getContext(), "successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
            InputMethodManager inputManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

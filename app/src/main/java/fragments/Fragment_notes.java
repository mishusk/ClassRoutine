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

import adapter.NoteAdapter;
import tdd.classroutine.R;
import db_helper.NoteDataSource;
import models.Model_Note;

/**
 * Created by mishu on 1/2/2017.
 */

public class Fragment_notes extends Fragment {
    private EditText etNote;
    private ImageButton addNoteBtn;
    private ListView noteListView;

    private Model_Note modelNote;
    private ArrayList<Model_Note> noteArray;
    private NoteAdapter adapter;
    private NoteDataSource dataSource;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_conf_note, container, false);

        etNote = (EditText) view.findViewById(R.id.etNoteText);
        addNoteBtn = (ImageButton) view.findViewById(R.id.iv_saveNote);
        noteListView = (ListView) view.findViewById(R.id.listViewNotes);

        dataSource = new NoteDataSource(getActivity());
        noteArray = new ArrayList<>();

        noteArray = dataSource.getAllNotes();
        adapter = new NoteAdapter(view.getContext(), noteArray);
        noteListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        for (int i = 0; i<noteArray.size(); i++){
            System.out.println(noteArray.get(i));
        }
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote(view);
            }
        });
        return view;
    }

    private void saveNote(View view) {
        String noteText = etNote.getText().toString();
        if (etNote.getText().toString().trim().isEmpty()) {
            etNote.setError("Write something");
        } else {
            modelNote = new Model_Note(noteText);
            etNote.setError(null);

            boolean saveStatus = dataSource.insertNote(modelNote);
            noteArray = dataSource.getAllNotes();
            adapter = new NoteAdapter(view.getContext(), noteArray);
            noteListView.setAdapter(adapter);

            etNote.setText("");
            if (saveStatus) {
                Toast.makeText(view.getContext(), "saved", Toast.LENGTH_SHORT).show();
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

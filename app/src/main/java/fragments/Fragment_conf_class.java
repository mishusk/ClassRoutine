package fragments;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import adapter.RoutineAdapter;
import tdd.classroutine.R;
import db_helper.RoutineDataSource;
import db_helper.SubjectDataSource;
import models.Model_Routine;

/**
 * Created by mishu on 8/11/2016.
 * validation check if a routine conflict in same day same time
 */
public class Fragment_conf_class extends Fragment {
    private Spinner choseSubjectSpnr;
    private Spinner choseDayNameSpnr;
    private EditText classRoomEt;
    private TextView clsStartTimeTV;
    private EditText classDurationTimePickerTV;
    private ListView classesListView;

    private ArrayList<Model_Routine> classesArrayList;
    private Model_Routine modelRoutine;
    private RoutineAdapter adapter;
    private RoutineDataSource dataSource;
    private String subjectName, startTime;
    private int dayName;

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_conf_class, container, false);
        initializeView();

        ImageButton saveClassBtn = (ImageButton) view.findViewById(R.id.btnAddRutn_fragClassAdd);
//set adapter to the list view
        dataSource = new RoutineDataSource(view.getContext());
        classesArrayList = dataSource.getAllClassRoutine();
        adapter = new RoutineAdapter(view.getContext(), classesArrayList);
        classesListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        onclickPicker();

        saveClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassToRoutine(view);
            }
        });

        return view;    }

    private void onclickPicker() {
        //get subject name from user
        SubjectDataSource subjectDataSource = new SubjectDataSource(view.getContext());
        ArrayList<String> subjectArray = new ArrayList<String>();
        subjectArray = subjectDataSource.allSubjectStringArray();
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_row_spinner, subjectArray);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choseSubjectSpnr.setAdapter(subjectAdapter);
        choseSubjectSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectName = choseSubjectSpnr.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //get day name from user
        String[] dayNameArray = getResources().getStringArray(R.array.day_name_array);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_row_spinner, dayNameArray);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choseDayNameSpnr.setAdapter(dayAdapter);
        choseDayNameSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (choseDayNameSpnr.getSelectedItem().toString().equals("SAT")){
                    dayName = 7;
                }else {
                dayName = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //get class start time
        clsStartTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar timePick = Calendar.getInstance();
                int hour = timePick.get(Calendar.HOUR_OF_DAY);
                int minute = timePick.get(Calendar.MINUTE);
                TimePickerDialog mPicker = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                                int hourSet = hourOfDay;
                                int minuteSet = minuteOfDay;
                                startTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minuteOfDay);
                                clsStartTimeTV.setText(startTime);
                            }
                        }, hour, minute, true);
                mPicker.setTitle("Select class start time");
                mPicker.show();
            }
        });

    }
    public void addClassToRoutine(View view) {


        subjectName = choseSubjectSpnr.getSelectedItem().toString();
//        dayName = choseDayNameSpnr.getSelectedItem().toString();

        //get class room
        String classRoom = classRoomEt.getText().toString();
        //time value and duration value
        String clasStraTime = clsStartTimeTV.getText().toString();

        if (classRoom.isEmpty()) {
            classRoomEt.setError("Room not set");
        } else if (clasStraTime.isEmpty()) {
            clsStartTimeTV.setError("Class start time not set");
        } else if (classDurationTimePickerTV.getText().toString().isEmpty()) {
            classDurationTimePickerTV.setError("Class duration not set");
        } else {
            classRoomEt.setError(null);
            clsStartTimeTV.setError(null);
            classDurationTimePickerTV.setError(null);

            int classDurationHour = Integer.parseInt(classDurationTimePickerTV.getText().toString());
            modelRoutine = new Model_Routine(subjectName,
                    dayName, classRoom, clasStraTime, classDurationHour);
            boolean savedStatus = dataSource.saveClassToRoutine(modelRoutine);
            if (savedStatus) {
                Toast.makeText(view.getContext(), "Class added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Class not added", Toast.LENGTH_SHORT).show();
            }

//set adapter to the list view
            classesArrayList = dataSource.getAllClassRoutine();
            adapter = new RoutineAdapter(view.getContext(), classesArrayList);
            classesListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        InputMethodManager inputManager = (InputMethodManager)
                view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void initializeView() {
        choseSubjectSpnr = (Spinner) view.findViewById(R.id.subjectName_spnr_rtn);
        choseDayNameSpnr = (Spinner) view.findViewById(R.id.dayName_spnr_rtn);
        classRoomEt = (EditText) view.findViewById(R.id.room_Et_rtn);
        clsStartTimeTV = (TextView) view.findViewById(R.id.time_textClock_rtn);
        classDurationTimePickerTV = (EditText) view.findViewById(R.id.classHour_Tv_rtn);
        classesListView = (ListView) view.findViewById(R.id.fullRoutine_ListView_rtn);

        classesArrayList = new ArrayList<>();
        modelRoutine = new Model_Routine();
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

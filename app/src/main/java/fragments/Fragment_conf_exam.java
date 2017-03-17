package fragments;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.ExamAdapter;
import tdd.classroutine.R;
import db_helper.ExamDataSource;
import db_helper.SubjectDataSource;
import models.Model_Exam;

/**
 * Created by mishu on 1/2/2017.
 */

public class Fragment_conf_exam extends Fragment {

    private Spinner spnrSubjectName;
    private TextView tvDate, tvTime;
    private EditText etDuration;
    private ImageButton ivSave;
    private ListView listViewExam;

    private ExamDataSource dbSource;
    private ArrayList<Model_Exam> examArrayList;
    private ExamAdapter adapter;
    private Model_Exam modelExam;

    private int selectedDay, selectedMonth, selectedYearf;
    private long examDateTimeInMillis;
    private String sExamTime, sExamDate, sExamDuration, sSubjectName, subjectName;

    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_conf_exam, container, false);
        initializeView();
        return view;
    }

    private void initializeView() {
        spnrSubjectName = (Spinner) view.findViewById(R.id.spSubNameConfExam);
        tvTime = (TextView) view.findViewById(R.id.tvTimeConfExam);
        tvDate = (TextView) view.findViewById(R.id.tvDateConfExam);
        etDuration = (EditText) view.findViewById(R.id.etDurationConfExam);
        listViewExam = (ListView) view.findViewById(R.id.lvConfExam);
        ivSave = (ImageButton) view.findViewById(R.id.ivSaveConfExam);

        dbSource = new ExamDataSource(view.getContext());
        examArrayList = new ArrayList<>();
        modelExam = new Model_Exam();

        setUpAdapter();
        setUpSpinner();
        setExamDate();
        setExamTime();
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExam();
            }
        });
    }

    private void setExamTime() {
        //get exam start time
        tvTime.setOnClickListener(new View.OnClickListener() {
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
                                sExamTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minuteOfDay);
                                tvTime.setText(sExamTime);
                            }
                        }, hour, minute, true);
                mPicker.setTitle("Select class start time");
                mPicker.show();
            }
        });
    }

    private void setExamDate() {
        //get exam start date
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(view.getContext(), d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            dateTime.set(Calendar.YEAR, year);
//            dateTime.set(Calendar.MONTH, monthOfYear);
//            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedYearf = year;
            selectedMonth = monthOfYear + 1;
            selectedDay = dayOfMonth;
            sExamDate = selectedDay + "/" + selectedMonth + "/" + selectedYearf;
            tvDate.setText(sExamDate);
        }
    };


    private void setUpSpinner() {
        //get subject name from user
        SubjectDataSource subjectDataSource = new SubjectDataSource(view.getContext());
        ArrayList<String> subjectArray = new ArrayList<String>();
        subjectArray = subjectDataSource.allSubjectStringArray();
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_row_spinner, subjectArray);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSubjectName.setAdapter(subjectAdapter);
        spnrSubjectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectName = spnrSubjectName.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveExam() {
        sExamDuration = etDuration.getText().toString();
        if (etDuration.getText().toString().trim().isEmpty()) {
            etDuration.setError("Enter duration");
        } else if (tvDate.getText().toString().trim().isEmpty()) {
            tvDate.setError("Select date");
        } else if (tvTime.getText().toString().trim().isEmpty()) {
            tvTime.setError("Select time");
        } else {
            etDuration.setError(null);
            tvDate.setError(null);
            tvTime.setError(null);

            modelExam = new Model_Exam(subjectName, sExamDate, sExamTime, sExamDuration);

            boolean saveStatus = dbSource.insertExam(modelExam);
            setUpAdapter();

            etDuration.setText("");
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

    private void setUpAdapter() {
        examArrayList = dbSource.getAllExam();
        adapter = new ExamAdapter(view.getContext(), examArrayList);
        listViewExam.setAdapter(adapter);
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

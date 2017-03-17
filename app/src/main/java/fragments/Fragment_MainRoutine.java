package fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adapter.MainClassRtnAdapter;
import tdd.classroutine.R;
import db_helper.RoutineDataSource;
import models.Model_Routine;

/**
 * Created by mishu on 8/11/2016.
 */
public class Fragment_MainRoutine extends Fragment {

    private TextView todayTv;
    private ListView mainRoutineListView;
    private int dayNameInt;
    private ImageButton previousDay, nextDay;

    private ArrayList<Model_Routine> modelRoutineArrayList;
    private MainClassRtnAdapter adapter;
    private RoutineDataSource dataSource;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_routine, container, false);


        previousDay = (ImageButton) view.findViewById(R.id.previousDayBtn);
        nextDay = (ImageButton) view.findViewById(R.id.nextDayBtn);
        todayTv = (TextView) view.findViewById(R.id.todayDayNameTV);
        mainRoutineListView = (ListView) view.findViewById(R.id.mainClassRoutineListView);
        dataSource = new RoutineDataSource(view.getContext());
        modelRoutineArrayList = new ArrayList<>();

        Calendar dayName = Calendar.getInstance();
        Date today = dayName.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String todayName = sdf.format(today).toUpperCase();
//        Toast.makeText(getActivity(), "today name from calendar executed  "+todayName, Toast.LENGTH_SHORT).show();

        if (todayName.toUpperCase().equals("SATURDAY")) {
            dayNameInt=7;
            todayTv.setText("SATURDAY");
//            Toast.makeText(getActivity(), "today name "+todayTv.getText().toString()+" \n" +
//                    " and calender name "+todayName, Toast.LENGTH_SHORT).show();
            modelRoutineArrayList = dataSource.findSubjectByDay(7);
        } else if (todayName.toUpperCase().equals("SUNDAY")) {
            dayNameInt=1;
            todayTv.setText("SUNDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(1);
        } else if (todayName.toUpperCase().equals("MONDAY")) {
            todayTv.setText("MONDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(2);
            dayNameInt=2;
        } else if (todayName.toUpperCase().equals("TUESDAY")) {
            todayTv.setText("TUESDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(3);
            dayNameInt=3;
        } else if (todayName.toUpperCase().equals("WEDNESDAY")) {
            todayTv.setText("WEDNESDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(4);
            dayNameInt=4;
        } else if (todayName.toUpperCase().equals("THURSDAY")) {
            todayTv.setText("THURSDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(5);
            dayNameInt=5;
        } else if (todayName.toUpperCase().equals("FRIDAY")) {
            todayTv.setText("FRIDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(6);
            dayNameInt=6;
        }

        adapter = new MainClassRtnAdapter(view.getContext(), modelRoutineArrayList);
        mainRoutineListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //change class listener
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPreviousDay(view);
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNextDay(view);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void goNextDay(View view) {
        dayNameInt += 1;
        if (dayNameInt == 7) {
            nextDay.setEnabled(false);
            nextDay.getBackground().setAlpha(80);
            todayTv.setText("SATURDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(7);
        } else if (dayNameInt == 1) {
            todayTv.setText("SUNDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(1);
        } else if (dayNameInt == 2) {
            previousDay.setEnabled(true);
            previousDay.getBackground().setAlpha(255);
            todayTv.setText("MONDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(2);
        } else if (dayNameInt == 3) {
            todayTv.setText("TUESDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(3);
        } else if (dayNameInt == 4) {
            todayTv.setText("WEDNESDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(4);
        } else if (dayNameInt == 5) {
            todayTv.setText("THURSDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(5);
        } else if (dayNameInt == 6) {
            previousDay.setEnabled(true);
            previousDay.getBackground().setAlpha(255);
            todayTv.setText("FRIDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(6);
        }
        adapter = new MainClassRtnAdapter(view.getContext(), modelRoutineArrayList);
        mainRoutineListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void goPreviousDay(View view) {
        dayNameInt -= 1;
        if (dayNameInt == 7) {
            todayTv.setText("SATURDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(7);
        } else if (dayNameInt == 1) {
            dayNameInt = 1;
            previousDay.setEnabled(false);
            previousDay.getBackground().setAlpha(80);
            todayTv.setText("SUNDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(1);
        } else if (dayNameInt == 2) {
            previousDay.setEnabled(true);
            nextDay.getBackground().setAlpha(255);
            todayTv.setText("MONDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(2);
        } else if (dayNameInt == 3) {
            todayTv.setText("TUESDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(3);
        } else if (dayNameInt == 4) {
            todayTv.setText("WEDNESDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(4);
        } else if (dayNameInt == 5) {
            todayTv.setText("THURSDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(5);
        } else if (dayNameInt == 6) {
            nextDay.setEnabled(true);
            nextDay.getBackground().setAlpha(255);
            todayTv.setText("FRIDAY");
            modelRoutineArrayList = dataSource.findSubjectByDay(6);
        }
        adapter = new MainClassRtnAdapter(view.getContext(), modelRoutineArrayList);
        mainRoutineListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
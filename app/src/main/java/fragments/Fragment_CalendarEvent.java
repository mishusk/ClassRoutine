package fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.Calendar;

import tdd.classroutine.R;

public class Fragment_CalendarEvent extends Fragment {

    private View view;
    private CalendarView calendarView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calendar_event, container, false);
        initView();
        return view;
    }

    private void initView() {
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setDate(Calendar.getInstance().getTimeInMillis());

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

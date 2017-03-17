package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import tdd.classroutine.R;
import models.Model_Routine;

/**
 * Created by mishu on 8/5/2016.
 */
public class MainClassRtnAdapter extends ArrayAdapter<Model_Routine> {
    private Context context;
    private ArrayList<Model_Routine> mainRoutineArrayList;
    private int currentHour;
    private int currentMinute;
    private TextView classStartTime, room, subjectName, classDurationHour;
    private CircleFillView circleFillView;


    public MainClassRtnAdapter(Context context, ArrayList<Model_Routine> modelRoutineArrayList) {
        super(context, R.layout.list_row_main_routine, modelRoutineArrayList);
        this.context = context;
        this.mainRoutineArrayList = modelRoutineArrayList;
        circleFillView = new CircleFillView(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_row_main_routine, null, true);


        classStartTime = (TextView) convertView.findViewById(R.id.timeTv_main_listView);
        room = (TextView) convertView.findViewById(R.id.roomTv_main_listView);
        subjectName = (TextView) convertView.findViewById(R.id.subNameTv_main_listView);
        circleFillView = (CircleFillView) convertView.findViewById(R.id.circleClassDurationStatus);
        classDurationHour = (TextView) convertView.findViewById(R.id.classDurationTv_main_listView);


        subjectName.setText("Subject : " + mainRoutineArrayList.get(position).getRoutinSubjctName());
        room.setText("Room : " + mainRoutineArrayList.get(position).getRoutineRoom());
        classStartTime.setText("Class Time : " + mainRoutineArrayList.get(position).getRoutinTime());
        classDurationHour.setText("Class Duration : " + mainRoutineArrayList.get(position).getRoutinDuration());


        //calculate time here
        String timeFromDatabase = mainRoutineArrayList.get(position).getRoutinTime();
        int duration = mainRoutineArrayList.get(position).getRoutinDuration();
        String[] timeInString = timeFromDatabase.split(":", 0);
        int hour = Integer.parseInt(timeInString[0]);
        int minute = Integer.parseInt(timeInString[1]);
        int classStartTime = (hour * 60) + minute;
        int classEndTime = classStartTime + duration;

        //current time
        Calendar mCalender = Calendar.getInstance();
        currentHour = mCalender.get(Calendar.HOUR_OF_DAY) * 60;
        currentMinute = mCalender.get(Calendar.MINUTE);
        int currentTimeInMinute = currentHour + currentMinute;

        int afterClassExtra20 = classEndTime + 20;


        if (currentTimeInMinute >= classStartTime) {
            int passedTimeinMinute = currentTimeInMinute - classStartTime;
            int oneForth = (int) (duration * 0.25);
            int half = (int) (duration * 0.50);
            int oneThird = (int) (duration * 0.75);
            if (passedTimeinMinute >= classStartTime && passedTimeinMinute <= oneForth) {
                circleFillView.setValue(25);
            } else if (passedTimeinMinute > oneForth && passedTimeinMinute <= half) {
                circleFillView.setValue(50);
            } else if (passedTimeinMinute > half && passedTimeinMinute <= oneThird) {
                circleFillView.setValue(75);
            } else if (passedTimeinMinute >= classEndTime && passedTimeinMinute <= afterClassExtra20) {
                circleFillView.setValue(100);
            } else {
                circleFillView.setValue(0);
            }
        }


        return convertView;
    }
}

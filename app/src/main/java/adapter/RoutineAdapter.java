package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tdd.classroutine.R;
import db_helper.RoutineDataSource;
import models.Model_Routine;

/**
 * Created by mishu on 8/3/2016.
 */
public class RoutineAdapter extends ArrayAdapter<Model_Routine> {
    private Context context;
    private ArrayList<Model_Routine> modelRoutineArrayList;
    private RoutineDataSource routineDataSource;


    public RoutineAdapter(Context context, ArrayList<Model_Routine> modelRoutineArrayList) {
        super(context, R.layout.list_row_class, modelRoutineArrayList);
        this.context = context;
        this.modelRoutineArrayList = modelRoutineArrayList;
        routineDataSource = new RoutineDataSource(context);
    }

    private static final class ViewHolder {
        private TextView subjectNameTv;
        private TextView timeTv;
        private TextView roomTv;
        private TextView dayNameTv;
        private TextView durationTv;
        private ImageButton deleteClassBtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_class, null, true);

            viewHolder.subjectNameTv = (TextView) convertView.findViewById(R.id.subjectName_Tv_cr_listRow);
            viewHolder.timeTv = (TextView) convertView.findViewById(R.id.timeTV_cr_listRow);
            viewHolder.roomTv = (TextView) convertView.findViewById(R.id.roomTV_cr_listRow);
            viewHolder.dayNameTv = (TextView) convertView.findViewById(R.id.dayTV_cr_listRow);
            viewHolder.durationTv = (TextView) convertView.findViewById(R.id.durationTV_cr_listRow);
            viewHolder.deleteClassBtn = (ImageButton) convertView.findViewById(R.id.deleteBtn_cr_listRow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RoutineAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.subjectNameTv.setText(modelRoutineArrayList.get(position).getRoutinSubjctName());
        viewHolder.timeTv.setText("Time: "+ modelRoutineArrayList.get(position).getRoutinTime());
        viewHolder.roomTv.setText("Room: "+ modelRoutineArrayList.get(position).getRoutineRoom());
        int dayNameInt = modelRoutineArrayList.get(position).getRoutinDay();
        if (dayNameInt == 7) {
            viewHolder.dayNameTv.setText("SATURDAY");
        } else if (dayNameInt == 1) {
            viewHolder.dayNameTv.setText("SUNDAY");
        } else if (dayNameInt == 2) {
            viewHolder.dayNameTv.setText("MONDAY");
        } else if (dayNameInt == 3) {
            viewHolder.dayNameTv.setText("TUESDAY");
        } else if (dayNameInt == 4) {
            viewHolder.dayNameTv.setText("WEDNESDAY");
        } else if (dayNameInt == 5) {
            viewHolder.dayNameTv.setText("THURSDAY");
        } else if (dayNameInt == 6) {
            viewHolder.dayNameTv.setText("FRIDAY");
        }
        viewHolder.durationTv.setText("Class duration: "+ modelRoutineArrayList.get(position).getRoutinDuration()+" minute");

        viewHolder.deleteClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleteStatus = routineDataSource.deleteClassRoutine(modelRoutineArrayList.get(position).getId());
                if (deleteStatus){
                    Toast.makeText(context, "Successfully deleted "+ modelRoutineArrayList.get(position).getRoutinSubjctName(), Toast.LENGTH_SHORT).show();
                    modelRoutineArrayList.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "No data deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  convertView;
    }
}

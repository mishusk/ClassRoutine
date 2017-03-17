package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import tdd.classroutine.R;
import db_helper.ExamDataSource;
import models.Model_Exam;

/**
 * Created by MD_Tareq on 3/16/2017.
 */

public class ExamAdapter extends ArrayAdapter<Model_Exam> {

    private Context context;
    private ArrayList<Model_Exam> modelExamArrayList;
    private ExamDataSource dataSource;

    public ExamAdapter(Context context, ArrayList<Model_Exam> modelExamArrayList) {
        super(context, R.layout.list_row_exam, modelExamArrayList);
        this.context = context;
        this.modelExamArrayList = modelExamArrayList;
        dataSource = new ExamDataSource(context);
    }

    private static final class ViewHolder {
        private TextView tvSubjectName;
        private TextView tvExamTime;
        private TextView tvExamDate;
        private TextView tvExamDuration;
        private ImageButton ivDeleteBtn;
    }

    @NonNull
    @Override
    public View getView(final int position, View cv, ViewGroup parent) {
        final ViewHolder vh;
        if (cv == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    context.LAYOUT_INFLATER_SERVICE);
            cv = inflater.inflate(R.layout.list_row_exam, null, true);

            vh.tvSubjectName = (TextView) cv.findViewById(R.id.tv_subName_examLR);
            vh.tvExamTime = (TextView) cv.findViewById(R.id.tvTime_examLR);
            vh.tvExamDate = (TextView) cv.findViewById(R.id.tv_date_examLR);
            vh.tvExamDuration = (TextView) cv.findViewById(R.id.tvDuration_examLR);
            vh.ivDeleteBtn = (ImageButton) cv.findViewById(R.id.ivDelete_examLR);
            cv.setTag(vh);
        } else {
            vh = (ExamAdapter.ViewHolder) cv.getTag();
        }

        vh.tvSubjectName.setText(modelExamArrayList.get(position).getSubject_name());
        vh.tvExamTime.setText(modelExamArrayList.get(position).getTime());
        vh.tvExamDate.setText(modelExamArrayList.get(position).getDate());
        vh.tvExamDuration.setText(modelExamArrayList.get(position).getDuration());

        vh.ivDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleteStatus = dataSource.deleteExamByID(modelExamArrayList.get(position).getId());
                if (deleteStatus) {
                    modelExamArrayList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        return cv;
    }
}

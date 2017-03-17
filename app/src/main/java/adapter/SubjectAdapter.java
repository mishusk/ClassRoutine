package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tdd.classroutine.R;
import db_helper.SubjectDataSource;
import models.Model_Subject;

/**
 * Created by mishu on 7/28/2016.
 */
public class SubjectAdapter extends ArrayAdapter<Model_Subject> {

    private Context context;
    private ArrayList<Model_Subject> modelSubjectArrayList;
    private SubjectDataSource dataSource;

    public SubjectAdapter(Context context, ArrayList<Model_Subject> modelSubjectArrayList) {
        super(context, R.layout.list_row_subject, modelSubjectArrayList);
        this.context = context;
        this.modelSubjectArrayList = modelSubjectArrayList;
        dataSource = new SubjectDataSource(context);
    }

    private static final class ViewHolder {
        private TextView subjectName;
        private ImageButton editSubBtn;
        private ImageButton deleteSubBtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_subject, null, true);

            viewHolder.subjectName = (TextView) convertView.findViewById(R.id.subNamTv_fragAdSub_LR);
            viewHolder.editSubBtn = (ImageButton) convertView.findViewById(R.id.editBtn_addSub_listRow);
            viewHolder.deleteSubBtn = (ImageButton) convertView.findViewById(R.id.deleteBtn_addSub_listRow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SubjectAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.subjectName.setText(modelSubjectArrayList.get(position).getSubjectName());

        //listView edit subject functionality
        viewHolder.editSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alert dialogue builder
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Edit subject name:");
                LinearLayout layout = new LinearLayout(context.getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etEditSubject = new EditText(context.getApplicationContext());
                etEditSubject.setHintTextColor(context.getApplicationContext().getResources().getColor(R.color.colorTextHint));
                etEditSubject.setHint(viewHolder.subjectName.getText().toString());
                layout.addView(etEditSubject);
                dialog.setView(layout);

                dialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean status = false;
                        if (!etEditSubject.getText().toString().isEmpty())
                            status = dataSource.updateSubjectByIdName(
                                    modelSubjectArrayList.get(position).getId(), etEditSubject.getText().toString());
                        if (status)
                            Toast.makeText(context.getApplicationContext(), "Change successful", Toast.LENGTH_SHORT).show();
                        Model_Subject model = new Model_Subject(etEditSubject.getText().toString());
                        modelSubjectArrayList.set(position, model);
                        notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });

        viewHolder.deleteSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dataChangeStatus = dataSource.deleteSubject(modelSubjectArrayList.get(position).getId());
                if (dataChangeStatus) {
                    modelSubjectArrayList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }
}

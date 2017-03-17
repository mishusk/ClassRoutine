package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import tdd.classroutine.R;
import db_helper.NoteDataSource;
import models.Model_Note;

/**
 * Created by MD_Tareq on 3/13/2017.
 */

public class NoteAdapter extends ArrayAdapter<Model_Note> {
    private Context context;
    private ArrayList<Model_Note> noteArrayList;
    private NoteDataSource noteDataSource;

    public NoteAdapter(Context context, ArrayList<Model_Note> modelNoteArrayList) {
        super(context, R.layout.list_row_notes, modelNoteArrayList);
        this.context = context;
        this.noteArrayList = modelNoteArrayList;
        noteDataSource = new NoteDataSource(context);
    }

    private static final class ViewHolder {
        private TextView tvNote;
        private ImageButton ivDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_notes, null, true);

            viewHolder.tvNote = (TextView) convertView.findViewById(R.id.tv_noteLR);
            viewHolder.ivDelete = (ImageButton) convertView.findViewById(R.id.ivDeleteNoteLR);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NoteAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tvNote.setText(noteArrayList.get(position).getNote());

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleteNoteStatus = noteDataSource.deleteNoteByID(noteArrayList.get(position).getId());
                if (deleteNoteStatus) {
                    noteArrayList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }
}

package fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.RoutineAdapter;
import tdd.classroutine.R;
import db_helper.RoutineDataSource;
import models.Model_Routine;

/**
 * Created by mishu on 8/11/2016.
 */
public class Fragment_view_all_class extends Fragment {

    ListView fullRoutine;
    ArrayList<Model_Routine> modelRoutineArrayList;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_view_all_class_exam, container, false);

        fullRoutine = (ListView) view.findViewById(R.id.fullRoutineListView);

        modelRoutineArrayList = new ArrayList<>();
        RoutineDataSource source = new RoutineDataSource(view.getContext());
        modelRoutineArrayList = source.getAllClassRoutine();
        RoutineAdapter adapter = new RoutineAdapter(view.getContext(), modelRoutineArrayList);

        fullRoutine.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
}

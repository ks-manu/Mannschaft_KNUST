package mannschaft_knust.classrep;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class CourseListFragment extends Fragment {

    RecyclerView courseListRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ClassRep");

        courseListRecyclerView = v.findViewById(R.id.course_list_recycler);
        final CourseListAdapter courseListAdapter = new CourseListAdapter();
        courseListRecyclerView.setAdapter(courseListAdapter);

        DatabaseViewModel courseListViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
        courseListViewModel.getCourseList().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courseList) {
                courseListAdapter.updateData(courseList);
            }
        });

        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_list, menu);

    }

}

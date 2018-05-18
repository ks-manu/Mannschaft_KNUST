package mannschaft_knust.classrep;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


public class CourseListFragment extends Fragment {

    RecyclerView courseListRecyclerView;
    DatabaseViewModel databaseViewModel;
    CourseListAdapter courseListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ClassRep");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Courses");

        courseListRecyclerView = v.findViewById(R.id.course_list_recycler);
        courseListAdapter = new CourseListAdapter();
        courseListRecyclerView.setAdapter(courseListAdapter);

        databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
        databaseViewModel.getCourseList().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courseList) {
                courseListAdapter.updateData(courseList);
            }
        });

        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                courseListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                courseListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}

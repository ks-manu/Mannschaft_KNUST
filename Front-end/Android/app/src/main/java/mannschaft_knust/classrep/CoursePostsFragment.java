package mannschaft_knust.classrep;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class CoursePostsFragment extends Fragment {
    RecyclerView coursePostsRecyclerView;
    CoursePostsAdapter coursePostsAdapter;

    FragmentManager fragmentManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course_posts, container, false);

        //configure floating send button
        fragmentManager = getChildFragmentManager();
        FloatingActionButton sendPostActionButton = v.findViewById(R.id.send_post);
        sendPostActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SendPostDialog sendPostDialog = new SendPostDialog();
                sendPostDialog.showNow(fragmentManager,null);
            }
        });

        coursePostsRecyclerView = v.findViewById(R.id.course_post_recycler);
        coursePostsAdapter = new CoursePostsAdapter(v.getContext(),
                ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString());
        coursePostsRecyclerView.setAdapter(coursePostsAdapter);

        DatabaseViewModel databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
        databaseViewModel.getCoursePosts().observe(this, new Observer<List<CoursePost>>() {
            @Override
            public void onChanged(@Nullable List<CoursePost> coursePosts) {
                coursePostsAdapter.updateData(coursePosts);
            }
        });

        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_posts, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem timetableItem = menu.findItem(R.id.view_timetable);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                coursePostsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                coursePostsAdapter.getFilter().filter(newText);
                Toast.makeText(getContext(), newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        timetableItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final TimetableFragment timetableFragment = new TimetableFragment();
                final FragmentManager fragmentManager = getFragmentManager();

                // observer for timetable fragment refresh
                DatabaseViewModel databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
                databaseViewModel.getCourseSessions().observe(getActivity(), new Observer<List<CourseSession>>() {
                    @Override
                    public void onChanged(@Nullable List<CourseSession> courseSessions) {
                        if(fragmentManager.findFragmentByTag("timetable fragment") != null){
                            fragmentManager.beginTransaction().detach(timetableFragment).attach(timetableFragment).commit();
                        }
                    }
                });

                //load timetable fragment
                fragmentManager.beginTransaction().replace(
                        R.id.main_fragment_container,timetableFragment, "timetable fragment"
                ).addToBackStack(null).commit();
                return false;
            }
        });
    }

    static public class SendPostDialog extends DialogFragment{
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.fragment_send_post);
            return dialog;
        }
    }
}

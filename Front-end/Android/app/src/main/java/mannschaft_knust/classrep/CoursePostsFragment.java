package mannschaft_knust.classrep;

<<<<<<< HEAD
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
=======
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
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
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import java.util.List;

public class CoursePostsFragment extends ListFragment {

    RecyclerView coursePostsRecyclerView;
=======
import org.threeten.bp.LocalTime;

import java.util.List;

public class CoursePostsFragment extends Fragment {
    CoursePostsAdapter coursePostsAdapter;
    String userType;
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course_posts, container, false);

<<<<<<< HEAD
        coursePostsRecyclerView = v.findViewById(R.id.course_post_recycler);
        final CoursePostsAdapter coursePostsAdapter = new CoursePostsAdapter(v.getContext());
        coursePostsRecyclerView.setAdapter(coursePostsAdapter);

        CoursePostsViewModel coursePostsViewModel =
                ViewModelProviders.of(this).get(CoursePostsViewModel.class);
        coursePostsViewModel.getCoursePosts().observe(this, new Observer<List<CoursePost>>() {
=======
        setHasOptionsMenu(true);

        userType = getActivity()
                .getSharedPreferences("mannschaft_knust.classrep.USER_PREF", Context.MODE_PRIVATE)
                .getString("user type", "");

        //configure floating send button
        FloatingActionButton sendPostActionButton = v.findViewById(R.id.send_post);
        if(userType.equals("Instructor")){
            final FragmentManager fragmentManager = getChildFragmentManager();
            sendPostActionButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    SendPostDialog sendPostDialog = new SendPostDialog();
                    sendPostDialog.showNow(fragmentManager,"send post");
                }
            });
        }
        else sendPostActionButton.setVisibility(View.GONE);

        //config recycler view and its adapter
        RecyclerView coursePostsRecyclerView = v.findViewById(R.id.course_post_recycler);
        coursePostsAdapter = new CoursePostsAdapter(v.getContext(),
                ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString());
        coursePostsRecyclerView.setAdapter(coursePostsAdapter);

        //set observer for updating recycler data
        DatabaseViewModel databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
        databaseViewModel.getCoursePosts().observe(this, new Observer<List<CoursePost>>() {
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
            @Override
            public void onChanged(@Nullable List<CoursePost> coursePosts) {
                coursePostsAdapter.updateData(coursePosts);
            }
        });

        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_posts, menu);
<<<<<<< HEAD
=======

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

        //config view timetable options item
        if(userType.equals("Student")){
            menu.removeItem(R.id.view_timetable);
            return;
        }
        //timetable option menu item to be configured only for instructors
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Post Something")
                    .setView(R.layout.dialog_send_post)
                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogFragment sendPostDialog =
                                    (DialogFragment) getFragmentManager()
                                            .findFragmentByTag("send post");

                            EditText messageInput =
                                    sendPostDialog.getDialog().findViewById(R.id.message_input);

                            DatabaseViewModel databaseViewModel =
                                    ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);

                            String postID = ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString();
                            String userLastName = getActivity()
                                    .getSharedPreferences("mannschaft_knust.classrep.USER_PREF",Context.MODE_PRIVATE)
                                    .getString("last name", "");
                            String userTitle = getActivity()
                                    .getSharedPreferences("mannschaft_knust.classrep.USER_PREF",Context.MODE_PRIVATE)
                                    .getString("title", "");

                            //insert post into database
                            databaseViewModel.insertPost(new CoursePost(postID+ LocalTime.now().toString(),
                                    messageInput.getText().toString(),
                                    null, userTitle+" "+ userLastName,
                                    false,false)
                            );
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();
        }
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
    }
}

package mannschaft_knust.classrep;

<<<<<<< HEAD
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
=======
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
<<<<<<< HEAD

public class MainActivity extends AppCompatActivity {

    private CoursesFragment coursesFragment = new CoursesFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
=======
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CourseListFragment courseListFragment = new CourseListFragment();
    private CoursePostsFragment coursePostsFragment = new CoursePostsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    DatabaseViewModel databaseViewModel;
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
<<<<<<< HEAD
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
=======
            //clear back stack
            for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
            switch (item.getItemId()) {
                case R.id.navigation_courses:
                    if (fragmentManager.findFragmentByTag("courses fragment") != null)
                        return true;
                    fragmentTransaction.replace(R.id.main_fragment_container,
<<<<<<< HEAD
                            coursesFragment, "courses fragment");
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("ClassRep");
=======
                            courseListFragment, "courses fragment");
                    fragmentTransaction.commit();
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
                    return true;
                case R.id.navigation_timetable:
                    if (fragmentManager.findFragmentByTag("timetable fragment") != null)
                        return true;
                    fragmentTransaction.replace(R.id.main_fragment_container,
<<<<<<< HEAD
                            coursesFragment, "timetable fragment");
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Timetable");
=======
                            courseListFragment, "timetable fragment");
                    fragmentTransaction.commit();
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
                    return true;
                case R.id.navigation_user_profile:
                    if (fragmentManager.findFragmentByTag("profile fragment") != null)
                        return true;
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            profileFragment, "profile fragment");
                    fragmentTransaction.commit();
<<<<<<< HEAD
                    getSupportActionBar().setTitle("Profile");
=======
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

<<<<<<< HEAD
        SharedPreferences userPref;
        userPref = getSharedPreferences(
                "mannschaft_knust.classrep.USER_PREF" , MODE_PRIVATE);
        if ( !(userPref.contains("username") && userPref.contains("password"))){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }
=======
        /*SharedPreferences userPref;
        userPref = getSharedPreferences(
                "mannschaft_knust.classrep.USER_PREF" , MODE_PRIVATE);
        if ( !(userPref.contains("userID") && userPref.contains("password"))){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }*/
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

<<<<<<< HEAD
        coursesFragment.setHasOptionsMenu(true);
        profileFragment.setHasOptionsMenu(true);
        fragmentManager.beginTransaction().add(R.id.main_fragment_container,coursesFragment,
                "courses fragment").commit();
=======
        databaseViewModel = ViewModelProviders.of(this).get(
                DatabaseViewModel.class);

        courseListFragment.setHasOptionsMenu(true);
        coursePostsFragment.setHasOptionsMenu(true);
        profileFragment.setHasOptionsMenu(true);

        fragmentManager.beginTransaction().add(R.id.main_fragment_container, courseListFragment,
                "courses fragment").commit();

>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
<<<<<<< HEAD

        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) searchItem.getActionView();

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }


=======
        return super.onCreateOptionsMenu(menu);
    }

    //loading post fragment on course list item click
    public void onCourseListItemClick(View view){
        TextView courseName = view.findViewById(R.id.course_name);
        TextView courseDetail = view.findViewById(R.id.course_detail);
        //set action bar title to chosen course
        getSupportActionBar().setTitle(courseName.getText());
        getSupportActionBar().setSubtitle(courseDetail.getText());
        //load fragment
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, coursePostsFragment,
                "course fragment").addToBackStack(null).commit();
    }
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
}

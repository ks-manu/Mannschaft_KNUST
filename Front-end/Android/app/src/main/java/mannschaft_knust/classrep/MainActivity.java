package mannschaft_knust.classrep;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
<<<<<<< HEAD
=======
import android.content.Intent;
import android.content.SharedPreferences;
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import java.util.List;

public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
    private CourseListFragment courseListFragment = new CourseListFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
=======
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
    private FragmentManager fragmentManager = getSupportFragmentManager();
    DatabaseViewModel databaseViewModel;
    AppCompatActivity thisActivity = this;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //clear back stack
            for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }

            switch (item.getItemId()) {

                case R.id.navigation_courses:
                    if (fragmentManager.findFragmentByTag("courses fragment") != null)
                        return true;
<<<<<<< HEAD
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            courseListFragment, "courses fragment");
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("ClassRep");
=======
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container,
                            new CourseListFragment(), "courses fragment").commit();
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
                    return true;

                case R.id.navigation_timetable:
                    final TimetableFragment timetableFragment = new TimetableFragment();
                    if (fragmentManager.findFragmentByTag("timetable fragment") != null)
                        return true;
<<<<<<< HEAD
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            courseListFragment, "timetable fragment");
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Timetable");
=======
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container,
                            timetableFragment, "timetable fragment").commit();
                    //set observer for timetable refresh
                    databaseViewModel.getCourseSessions().observe(thisActivity, new Observer<List<CourseSession>>() {
                        @Override
                        public void onChanged(@Nullable List<CourseSession> courseSessions) {
                            if(fragmentManager.findFragmentByTag("timetable fragment") != null){
                                fragmentManager.beginTransaction().detach(timetableFragment).attach(timetableFragment).commit();
                            }
                        }
                    });
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
                    return true;

                case R.id.navigation_user_profile:
                    if (fragmentManager.findFragmentByTag("profile fragment") != null)
                        return true;
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container,
                            new ProfileFragment(), "profile fragment").commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*SharedPreferences userPref;
        userPref = getSharedPreferences(
                "mannschaft_knust.classrep.USER_PREF" , MODE_PRIVATE);
<<<<<<< HEAD
        if ( !(userPref.contains("userID") && userPref.contains("password"))){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }*/
=======
        if ( !userPref.contains("userID")){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
            finish();
        }
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        //remove timetable icon in navigation if user is instructor
        if(userPref.getString("user type", "").equals("Instructor"))
            navigation.getMenu().removeItem(R.id.navigation_timetable);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

<<<<<<< HEAD
        courseListFragment.setHasOptionsMenu(true);
        profileFragment.setHasOptionsMenu(true);
        fragmentManager.beginTransaction().add(R.id.main_fragment_container, courseListFragment,
=======
        databaseViewModel = ViewModelProviders.of(this).get(
                DatabaseViewModel.class);


        fragmentManager.beginTransaction().add(R.id.main_fragment_container, new CourseListFragment(),
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
                "courses fragment").commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return super.onCreateOptionsMenu(menu);
    }
<<<<<<< HEAD
=======

    //load post fragment on course list item click
    public void onCourseListItemClick(View view){
        TextView courseName = view.findViewById(R.id.course_name);
        TextView courseParticipant = view.findViewById(R.id.course_participant);

        //set action bar title to chosen course
        getSupportActionBar().setTitle(courseName.getText());
        getSupportActionBar().setSubtitle(courseParticipant.getText());

        //load fragment
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new CoursePostsFragment(),
                "course fragment").addToBackStack(null).commit();
    }
>>>>>>> 43472d64ddc513de0ab2db5c474cd328cc28f1b2
}

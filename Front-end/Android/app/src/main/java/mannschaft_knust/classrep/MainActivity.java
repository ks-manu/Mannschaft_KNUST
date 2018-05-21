package mannschaft_knust.classrep;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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
                    fragmentManager.beginTransaction().replace(R.id.main_fragment_container,
                            new CourseListFragment(), "courses fragment").commit();
                    return true;

                case R.id.navigation_timetable:
                    final TimetableFragment timetableFragment = new TimetableFragment();
                    if (fragmentManager.findFragmentByTag("timetable fragment") != null)
                        return true;
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
        AndroidThreeTen.init(this);

        SharedPreferences userPref;
        userPref = getSharedPreferences(
                "mannschaft_knust.classrep.USER_PREF" , MODE_PRIVATE);
        if ( !userPref.contains("userID")){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        //remove timetable icon in navigation if user is instructor
        if(userPref.getString("user type", "").equals("Lecturer"))
            navigation.getMenu().removeItem(R.id.navigation_timetable);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        databaseViewModel = ViewModelProviders.of(this).get(
                DatabaseViewModel.class);


        fragmentManager.beginTransaction().add(R.id.main_fragment_container, new CourseListFragment(),
                "courses fragment").commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
}

package mannschaft_knust.classrep;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

    BottomNavigationView navigation;
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

        createNotificationChannel();

        SharedPreferences userPref;
        userPref = getSharedPreferences(
                "mannschaft_knust.classrep.USER_PREF" , MODE_PRIVATE);
        if ( !userPref.contains("userID")){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
            finish();
        }

        databaseViewModel = ViewModelProviders.of(this).get(
                DatabaseViewModel.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

        navigation = findViewById(R.id.navigation);
        //remove timetable icon in navigation if user is instructor
        if(userPref.getString("user type", "").equals("Lecturer"))
            navigation.getMenu().removeItem(R.id.navigation_timetable);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        //background synch
        JobScheduler syncJob = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder jobBuilder = new JobInfo
                .Builder(0, new ComponentName(this,DataBackgroundSync.class.getName()));
        syncJob.schedule(jobBuilder.setPeriodic(300000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build());

        if( savedInstanceState == null){
            fragmentManager.beginTransaction().add(R.id.main_fragment_container, new CourseListFragment(),
                    "courses fragment").commit();
        }

        handleNotificationIntent();
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

        loadCoursePostFragment(courseName.getText().toString()
                , courseParticipant.getText().toString());
    }

    private void loadCoursePostFragment(String courseName, String courseParticipant){
        //set action bar title to chosen course
        getSupportActionBar().setTitle(courseName);
        getSupportActionBar().setSubtitle(courseParticipant);

        //load fragment
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new CoursePostsFragment(),
                "course fragment").addToBackStack(null).commit();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "post channel";
            String description = "notifies user of new posts";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("post channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void handleNotificationIntent(){
        String userType;
        if(databaseViewModel.getUser().getValue() != null){
            userType = databaseViewModel.getUser().getValue().userType;

            if(getIntent().getExtras() != null){
                String postID = getIntent().getExtras().getString("PostID");

                if(postID != null){
                    List<CourseSession> courseSessions =
                            databaseViewModel.getCourseSessions().getValue();

                    if(courseSessions != null){
                        for(CourseSession courseSession: courseSessions){
                            if(postID.contains(courseSession.courseAndCode)){
                                navigation.setSelectedItemId(R.id.navigation_courses);
                                if(userType.equals("Student"))
                                    loadCoursePostFragment(courseSession.courseAndCode
                                            ,courseSession.participants);
                                else if(postID.contains(courseSession.participants))
                                    loadCoursePostFragment(courseSession.courseAndCode
                                            ,courseSession.participants);
                            }
                        }
                    }
                }
            }
        }
    }
}

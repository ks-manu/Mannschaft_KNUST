package mannschaft_knust.classrep;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private CoursesFragment coursesFragment = new CoursesFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_courses:
                    if (fragmentManager.findFragmentByTag("courses fragment") != null)
                        return true;
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            coursesFragment, "courses fragment");
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("ClassRep");
                    return true;
                case R.id.navigation_timetable:
                    if (fragmentManager.findFragmentByTag("timetable fragment") != null)
                        return true;
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            coursesFragment, "timetable fragment");
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Timetable");
                    return true;
                case R.id.navigation_user_profile:
                    if (fragmentManager.findFragmentByTag("profile fragment") != null)
                        return true;
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            profileFragment, "profile fragment");
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Profile");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences userPref;
        userPref = getSharedPreferences(
                "mannschaft_knust.classrep.USER_PREF" , MODE_PRIVATE);
        if ( !(userPref.contains("username") && userPref.contains("password"))){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        coursesFragment.setHasOptionsMenu(true);
        profileFragment.setHasOptionsMenu(true);
        fragmentManager.beginTransaction().add(R.id.main_fragment_container,coursesFragment,
                "courses fragment").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);

        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) searchItem.getActionView();

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }


}

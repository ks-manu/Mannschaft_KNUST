package mannschaft_knust.classrep;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CourseListFragment courseListFragment = new CourseListFragment();
    private CoursePostsFragment coursePostsFragment = new CoursePostsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    DatabaseViewModel databaseViewModel;

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
                            courseListFragment, "courses fragment");
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_timetable:
                    if (fragmentManager.findFragmentByTag("timetable fragment") != null)
                        return true;
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            courseListFragment, "timetable fragment");
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_user_profile:
                    if (fragmentManager.findFragmentByTag("profile fragment") != null)
                        return true;
                    fragmentManager.popBackStack();//to remove history
                    fragmentTransaction.replace(R.id.main_fragment_container,
                            profileFragment, "profile fragment");
                    fragmentTransaction.commit();
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
        if ( !(userPref.contains("userID") && userPref.contains("password"))){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        databaseViewModel = ViewModelProviders.of(this).get(
                DatabaseViewModel.class);

        courseListFragment.setHasOptionsMenu(true);
        coursePostsFragment.setHasOptionsMenu(true);
        profileFragment.setHasOptionsMenu(true);

        fragmentManager.beginTransaction().add(R.id.main_fragment_container, courseListFragment,
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

    //loading post fragment on course list item click
    public void onCourseListItemClick(View view){
        TextView courseName = view.findViewById(R.id.course_name);
        //Toast.makeText(this,courseName.getText(),Toast.LENGTH_LONG).show();
        //load fragment
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, coursePostsFragment,
                "course fragment").addToBackStack(null).commit();
        //load post set into database
        databaseViewModel.changePostSet((String) courseName.getText());
        //set action bar title
        getSupportActionBar().setTitle(courseName.getText());
    }
}

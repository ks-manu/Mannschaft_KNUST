package mannschaft_knust.classrep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    ProfileFragment profileFragment = new ProfileFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_courses:
                    fragmentTransaction.remove(profileFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_user_profile:
                    fragmentTransaction.replace(R.id.profile_fragment_container, profileFragment);
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
        if ( !(userPref.contains("username") && userPref.contains("password"))){
            Intent signInIntent = new Intent(this, SignInActivity.class);
            startActivity(signInIntent);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoursesListFragment coursesListFragment = new CoursesListFragment();
        CourseViewFragment courseViewFragment = new CourseViewFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);

        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) searchItem.getActionView();

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Created by Hassan on 22/03/2018.
     */

    public static class CoursesListFragment extends ListFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.courses_list_fragment, container, false);
        }
    }

    /**
     * Created by Hassan on 23/03/2018.
     */

    public static class CourseViewFragment extends ListFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.course_view_fragment, container, false);
        }
    }

    /**
     * Created by Hassan on 23/03/2018.
     */

    public static class PostViewFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.post_view_fragment, container, false);
        }
    }

    /**
     * Created by Hassan on 22/03/2018.
     */

    public static class ProfileFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.profile_fragment, container, false);
        }
    }

}

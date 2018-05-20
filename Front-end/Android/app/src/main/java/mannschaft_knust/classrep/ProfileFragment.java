package mannschaft_knust.classrep;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Comparator;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    DatabaseViewModel databaseViewModel;
    User user;
    ProfileFragment thisProfileFragment = this;

    //string comparator for sorting array adapter(for both college and programme spinners)
    private Comparator<CharSequence> stringComparator = new Comparator<CharSequence>(){
        @Override
        public int compare(CharSequence o1, CharSequence o2) {
            return o1.toString().compareToIgnoreCase(o2.toString());
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
        user = databaseViewModel.getUser().getValue();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("");

        //check user type for visibilty of user type specific views
        if(user.userType.equals("Student")){
            //remove title field
            (v.findViewById(R.id.instructor_title)).setVisibility(View.GONE);

            //spinner and adapter for college options
            Spinner collegesSpinner = v.findViewById(R.id.college_spinner);
            ArrayAdapter<CharSequence> collegesAdapter = ArrayAdapter.createFromResource(v.getContext(),
                    R.array.college_array, android.R.layout.simple_spinner_item);
            collegesAdapter.sort(stringComparator);
            collegesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            collegesSpinner.setAdapter(collegesAdapter);
            collegesSpinner.setOnItemSelectedListener(this);

            //spinner and adapter for programme options

            String programmeAndYear = user.programmeAndYear;
            String year = programmeAndYear.substring(programmeAndYear.indexOf('(')+1,
                    programmeAndYear.length()-1);
            //show student index programme and year
            ((EditText) v.findViewById(R.id.index_number_input))
                    .setText(String.valueOf(user.indexNumber));
            ((EditText) v.findViewById(R.id.year_input))
                    .setText(year);
            //set spinner to students current college and programme
            collegesSpinner.setSelection(collegesAdapter.getPosition(user.college));
            Toast.makeText(getContext(), user.college, Toast.LENGTH_SHORT).show();
        }
        else {//if user type is instructor set student fields to GONE
            (v.findViewById(R.id.student_extra)).setVisibility(View.GONE);

            //show lecturer title
            ((EditText) v.findViewById(R.id.instructor_title))
                    .setText(user.title);
        }


        //show non user type dependent details (i.e first name and last name)
        ((EditText) v.findViewById(R.id.first_name_input))
                .setText(user.firstName);
        ((EditText) v.findViewById(R.id.last_name_input))
                .setText(user.lastName);

        //config sign out button
        Button signOutButton = v.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //clear all in user preference
                getActivity().getSharedPreferences("mannschaft_knust.classrep.USER_PREF",
                        Context.MODE_PRIVATE).edit().clear().apply();

                //delete all in database
                DatabaseViewModel databaseViewModel = ViewModelProviders.of(getActivity())
                        .get(DatabaseViewModel.class);
                databaseViewModel.deleteAll();

                //load sign in activity and finish main activity
                Intent signInIntent = new Intent(getActivity(), SignInActivity.class);
                startActivity(signInIntent);
                getActivity().finish();
            }
        });

        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);

        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
                return false;
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedCollege = (parent.getItemAtPosition(pos)).toString();

        //spinner and adapter for programmes depending on selected college
        Spinner programmeSpinner = getView().findViewById(R.id.programme_spinner);
        ArrayAdapter<CharSequence> programmesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item);
        switch (selectedCollege) {
            case "Engineering":
                programmesAdapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.engineering_array, android.R.layout.simple_spinner_item);
                break;
            case "Science":
                programmesAdapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.science_array, android.R.layout.simple_spinner_item);
                break;
            case "Arts and Built Environment":
                programmesAdapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.art_and_built_environment_array, android.R.layout.simple_spinner_item);
                break;
        }
        programmesAdapter.sort(new Comparator<CharSequence>() {
            @Override
            public int compare(CharSequence o1, CharSequence o2) {
                return o1.toString().compareToIgnoreCase(o2.toString());
            }
        });
        programmesAdapter.sort(stringComparator);
        programmesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programmeSpinner.setAdapter(programmesAdapter);

        //set users current programme
        String programmeAndYear = user.programmeAndYear;
        String programme = programmeAndYear.substring(0,programmeAndYear.indexOf('('));
        programmeSpinner
                .setSelection(programmesAdapter
                        .getPosition(programme));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

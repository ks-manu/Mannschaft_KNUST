package mannschaft_knust.classrep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Comparator;

//todo: post bio api
public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //string comparator for sorting array adapter(for both college and programme spinners)
    private Comparator<CharSequence> stringComparator = new Comparator<CharSequence>(){
        @Override
        public int compare(CharSequence o1, CharSequence o2) {
            return o1.toString().compareToIgnoreCase(o2.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //spinner and adapter for college options
        Spinner collegesSpinner = findViewById(R.id.college_spinner);
        ArrayAdapter<CharSequence> collegesAdapter = ArrayAdapter.createFromResource(this,
                R.array.college_array, android.R.layout.simple_spinner_item);
        collegesAdapter.sort(stringComparator);
        collegesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegesSpinner.setAdapter(collegesAdapter);
        collegesSpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedCollege = (parent.getItemAtPosition(pos)).toString();

        //spinner and adapter for programmes depending on selected college
        Spinner programmeSpinner = findViewById(R.id.programme_spinner);
        ArrayAdapter<CharSequence> programmesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item);
        switch (selectedCollege) {
            case "Engineering":
                programmesAdapter = ArrayAdapter.createFromResource(this,
                        R.array.engineering_array, android.R.layout.simple_spinner_item);
                break;
            case "Science":
                programmesAdapter = ArrayAdapter.createFromResource(this,
                        R.array.science_array, android.R.layout.simple_spinner_item);
                break;
            case "Arts and Built Environment":
                programmesAdapter = ArrayAdapter.createFromResource(this,
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

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onClickSignUpButton(View signUpButton){

        Intent signUpIntent = new Intent(this, MainActivity.class);
        startActivity(signUpIntent);
    }
}

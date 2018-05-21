package mannschaft_knust.classrep;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private Scene scene1;
    private Scene scene2;
    private Transition autoTransition;
    private View clickedView;
    private Scene currentScene;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //scenes and transition
        ViewGroup sceneRoot = findViewById(R.id.scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_sign_in_scene1,
                    this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_sign_in_scene2,
                    this);
        scene2.setEnterAction(new Runnable() {
            @Override

            //do this every time whe show sign in view
            public void run() {
                EditText userIDInput = findViewById(R.id.userID);
                TextView signUpLink = findViewById(R.id.signUpLink);

                if(clickedView.getId() == R.id.student_button){
                    userIDInput.setHint(R.string.student_id);
                    userIDInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    signUpLink.setVisibility(View.VISIBLE);
                }
                else if(clickedView.getId() == R.id.instructor_button){
                    userIDInput.setHint(R.string.instructor_id);
                    userIDInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    signUpLink.setVisibility(View.GONE);
                }
            }
        });
        autoTransition = new AutoTransition();

    }

    // called when user type(instructor or student) button is clicked
    public void onClickUserType(View button){
        clickedView = button;
        userType = ((TextView)button).getText().toString();

        //transition to sign in view
        TransitionManager.go(scene2, autoTransition);
        currentScene = scene2;
    }

    @Override
    public void onBackPressed() {
        //go back to scene1 if back pressed on scene 2
        if (currentScene == scene2) {
            TransitionManager.go(scene1, autoTransition);
            currentScene = scene1;
        }
        else
            super.onBackPressed();
    }

    public void onClickSignUpLink(View signUpLink){
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    public void onClickSignInButton(View button){
        DataRepository dataRepository = new DataRepository(this);
        EditText userIDInput = findViewById(R.id.userID);
        EditText passwordInput = findViewById(R.id.password);

        String userID = userIDInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(userID.equals("") || password.equals("")){
            Toast.makeText(this, "You omitted id/password",Toast.LENGTH_SHORT).show();
            return;
        }

        String userType = this.userType;
        dataRepository.signIn(userType, userID, password);
    }
}

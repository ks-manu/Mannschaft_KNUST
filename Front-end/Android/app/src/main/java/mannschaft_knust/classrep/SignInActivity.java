package mannschaft_knust.classrep;

import android.content.Intent;
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

public class SignInActivity extends AppCompatActivity {

    private ViewGroup sceneRoot;
    private Scene scene1;
    private Scene scene2;
    private Transition autoTransition;
    private View clickedView;
    private Scene currentScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //scenes and transition
        sceneRoot = findViewById(R.id.scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_sign_in_scene1,
                    this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_sign_in_scene2,
                    this);
        scene2.setEnterAction(new Runnable() {
            @Override

            //do this every time whe show sign in view
            public void run() {EditText userIDInput = findViewById(R.id.userID);
                TextView signUpLink = findViewById(R.id.signUpLink);

                if(clickedView.getId() == R.id.student_button){
                    userIDInput.setHint(R.string.student_id);
                    userIDInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    signUpLink.setClickable(true);
                    signUpLink.setVisibility(View.VISIBLE);
                }
                else if(clickedView.getId() == R.id.instructor_button){
                    userIDInput.setHint(R.string.instructor_id);
                    userIDInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    signUpLink.setClickable(false);
                    signUpLink.setVisibility(View.INVISIBLE);
                }
            }
        });
        autoTransition = new AutoTransition();

    }

    // called when user typ(instructor or student button is clicked
    public void onClickUserType(View button){
        clickedView = button;

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
}

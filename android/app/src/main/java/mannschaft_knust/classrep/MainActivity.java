package mannschaft_knust.classrep;

import android.content.SharedPreferences;
import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewGroup sceneRoot;
    private Scene scene1;
    private Scene scene2;
    private Transition autoTransition;
    private View clickedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences userPref;
        userPref = getSharedPreferences(
                "mannschaft_knust.classrep.USER_PREF" , MODE_PRIVATE);
        if (userPref.contains("username") && userPref.contains("password"))
            //code for loading class list activity here
            ;
        else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //scenes and transition
            sceneRoot = findViewById(R.id.scene_root);
            scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_main_scene1,
                    this);
            scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_main_scene2,
                    this);
            scene2.setEnterAction(new Runnable() {
                @Override

                //do this every time whe show sign in view
                public void run() {
                    EditText userID = findViewById(R.id.userID);
                    TextView signUpLink = findViewById(R.id.signUpLink);

                    if(clickedView.getId() == R.id.student_button){
                        userID.setHint(R.string.student_id);
                        signUpLink.setClickable(true);
                        signUpLink.setVisibility(View.VISIBLE);
                    }
                    else if(clickedView.getId() == R.id.instructor_button){
                        userID.setHint(R.string.instructor_id);
                        signUpLink.setClickable(false);
                        signUpLink.setVisibility(View.INVISIBLE);
                    }
                }
            });
            autoTransition = new AutoTransition();
        }
    }

    // called when user typ(instructor or student button is clicked
    public void sceneTransition(View button){
        clickedView = button;

        //transition to sign in view
        TransitionManager.go(scene2, autoTransition);
    }

    @Override
    public void onBackPressed() {
        //go back to scene1 if back pressed on scene 2
        if (Scene.getSceneForLayout(sceneRoot, R.layout.activity_main_scene2,this )
                == scene2)
        TransitionManager.go(scene1, autoTransition);
        else
            super.onBackPressed();
    }

}

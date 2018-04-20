package mannschaft_knust.classrep;

<<<<<<< HEAD
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
=======
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
<<<<<<< HEAD
        return inflater.inflate(R.layout.fragment_profile, container, false);
=======
        View v = inflater.inflate(R.layout.fragment_profile_instructor, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("");

        return v;
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
<<<<<<< HEAD
=======

        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
                return false;
            }
        });
>>>>>>> de8ef97882507ee65dbc280704872c516e30d3ec
    }
}

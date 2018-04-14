package mannschaft_knust.classrep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.tobiasschuerg.weekview.view.WeekView;

public class TimetableFragment extends Fragment {
    private String currentProgrammeAndYear;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_timetable, container, false);

        WeekView weekView = v.findViewById(R.id.week_view);
        return v;
    }
}

package mannschaft_knust.classrep;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.TypeConverter;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.tobiasschuerg.weekview.data.Event;
import de.tobiasschuerg.weekview.data.EventConfig;
import de.tobiasschuerg.weekview.view.EventView;
import de.tobiasschuerg.weekview.view.WeekView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class TimetableFragment extends Fragment {

    DatabaseViewModel databaseViewModel;
    List<CourseSession> courseSessions;
    //reference to current fragment
    TimetableFragment timetableFragment = this;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setHasOptionsMenu(true);

        AndroidThreeTen.init(getContext());

        //inflate fragment layout
        View v = inflater.inflate(R.layout.fragment_timetable, container, false);

        //config week view
        final WeekView weekView = v.findViewById(R.id.week_view);
        weekView.setEventConfig(new EventConfig(true,
                true,true,true,
                true,true));

        //load events
        databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
        courseSessions = databaseViewModel.getCourseSessions().getValue();
        if(courseSessions == null){
            courseSessions = new ArrayList<>();
        }
        weekView.addLessonsToTimetable(eventsFromCourseSessions(courseSessions));

        //set observer for events reload
        databaseViewModel.getCourseSessions().observe(this, new Observer<List<CourseSession>>() {
            @Override
            public void onChanged(@Nullable List<CourseSession> newCourseSessions) {
                if(newCourseSessions!=null){

                    //clear visible events before update
                    if(weekView.getChildCount()>1){

                        for (int i = 0 ; i < weekView.getChildCount();i++){
                            weekView.removeViewAt(1);
                        }
                    }
                    //update events
                    courseSessions = newCourseSessions;
                    weekView.addLessonsToTimetable(eventsFromCourseSessions(courseSessions));
                }
                else
                    Toast.makeText(getContext(),"no sessions",Toast.LENGTH_SHORT).show();

            }
        });

        //configuring on session(event) click
        weekView.setLessonClickListener(new Function1<EventView, Unit>() {
            @Override
            public Unit invoke(EventView eventView) {
                //load dialog for editing session
                //look through courseSessions
                for(CourseSession courseSession : courseSessions){
                    //compare id of event and course session to be edited
                    if(courseSession.courseSessionID == eventView.getEvent().getId()){

                        UpdateSessionFragment updateSessionFragment = new UpdateSessionFragment();
                        updateSessionFragment.showNow(getChildFragmentManager(),"update session");

                        //get reference to the dialog
                        AlertDialog dialog =
                                ((AlertDialog) updateSessionFragment.getDialog());

                        //set message
                        dialog.setMessage("Edit " + courseSession.courseAndCode+
                                "for "+ courseSession.participants);

                        //display information of selected course session(event)
                        TextView courseName = dialog.findViewById(R.id.course_name);
                        TextView participants = dialog.findViewById(R.id.participants);
                        courseName.setText(courseSession.courseAndCode);
                        participants.setText(courseSession.participants);

                        //load day spinner
                        Spinner daySpinner = dialog.findViewById(R.id.course_spinner);
                        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(getActivity(),
                                R.array.days_of_week, android.R.layout.simple_spinner_item);
                        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        daySpinner.setAdapter(dayAdapter);

                        //config time input with time picker dialog
                        EditText timeInput = dialog.findViewById(R.id.start_time_input);
                        timeInput.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //show time picker dialog
                                DialogFragment timePickerFragment = new TimePickerFragment();
                                timePickerFragment.show(getChildFragmentManager(), "time picker");
                            }
                        });

                        //load available venues(this would be dynamic base on time selected)
                        Spinner venueSpinner = dialog.findViewById(R.id.venue_spinner);
                        ArrayAdapter<CharSequence> venueAdapter = ArrayAdapter.createFromResource(getActivity(),
                                R.array.venues, android.R.layout.simple_spinner_item);
                        venueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        venueSpinner.setAdapter(venueAdapter);

                        break;
                    }
                }
                return null;
            }
        });


        weekView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getPointerCount() == 1){
                    Log.d("Scroll", "1-pointer touch");
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                else if(event.getPointerCount() == 2){
                    Log.d("Zoom", "2-pointer touch");
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        return v;
    }


    //convert  course sessions to events for weekview
    @TypeConverter
    public List<Event.Single> eventsFromCourseSessions(@NonNull List<CourseSession> courseSession){
        List<Event.Single> events = new ArrayList<>();

        for(int i = 0; i < courseSession.size(); i++){

            int id = courseSession.get(i).courseSessionID;
            LocalDate date = LocalDate.now();
            String title = courseSession.get(i).courseAndCode;
            String shortTitle = title.substring(title.indexOf('_')+1);
            String subtitle = courseSession.get(i).participants.substring(0,5);

            int day = 0;
            switch(courseSession.get(i).day.toLowerCase()){
                case "monday":
                    day = Calendar.MONDAY;
                    break;
                case "tuesday":
                    day = Calendar.TUESDAY;
                    break;
                case "wednesday":
                    day =  Calendar.WEDNESDAY;
                    break;
                case "thursday":
                    day = Calendar.THURSDAY;
                    break;
                case "friday":
                    day = Calendar.FRIDAY;
                    break;
            }

            LocalTime startTime = LocalTime.parse(courseSession.get(i).startingTime.toString());
            LocalTime endTime = LocalTime.parse(courseSession.get(i).endingTime.toString());

            String upperText = title.substring(0,5);
            String lowerText = courseSession.get(i).venue;


            //default colors
            int textColor = Color.WHITE;
            int backgroundColor = Color.GRAY;

            events.add(new Event.Single(id,date ,title , shortTitle,subtitle ,day,startTime,
                    endTime,upperText , lowerText,textColor,backgroundColor));
        }

        return events;
    }

    //options menu for fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_timtable, menu);

        MenuItem addCourseSession = menu.findItem(R.id.add_session_button);
        MenuItem refreshTimetable = menu.findItem(R.id.refresh_timetable_button);

        addCourseSession.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //show add session dialog
                AddSessionFragment addSessionFragment = new AddSessionFragment();
                addSessionFragment.showNow(getChildFragmentManager(),"add session");

                //get dialog for customisation
                Dialog dialog = addSessionFragment.getDialog();

                //load course spinner(list to be filtered from database)
                Spinner coursesSpinner = dialog.findViewById(R.id.course_spinner);
                ArrayAdapter<CharSequence> coursesAdapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.courses, android.R.layout.simple_spinner_item);
                coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                coursesSpinner.setAdapter(coursesAdapter);

                //config time input with time picker dialog
                EditText timeInput = dialog.findViewById(R.id.start_time_input);
                timeInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show time picker dialog
                        DialogFragment timePickerFragment = new TimePickerFragment();
                        timePickerFragment.show(getChildFragmentManager(), "time picker");
                    }
                });

                //load available venues(this would be dynamic base on time selected)
                Spinner venueSpinner = dialog.findViewById(R.id.venue_spinner);
                ArrayAdapter<CharSequence> venueAdapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.venues, android.R.layout.simple_spinner_item);
                venueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                venueSpinner.setAdapter(venueAdapter);

                return false;
            }
        });

        refreshTimetable.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //refresh timetable on refresh button click
                getFragmentManager().beginTransaction().detach(timetableFragment).attach(timetableFragment).commit();
                return false;
            }
        });
    }

    //update session dialog
    static public class UpdateSessionFragment extends DialogFragment {
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            //configure builder
            builder.setTitle("Edit Session")
                    .setView(R.layout.dialog_update_session)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();
        }
    }

    //time picker dialog
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String timeString = hourOfDay+":"+minute;

            DialogFragment sessionEditAddFragment;
            FragmentManager fragmentManager = getFragmentManager();

            //check fragment invoking time picker
            if(fragmentManager.findFragmentByTag("update session")== null){
                sessionEditAddFragment = (AddSessionFragment) fragmentManager
                        .findFragmentByTag("add session");
            }
            else sessionEditAddFragment = (UpdateSessionFragment) fragmentManager
                    .findFragmentByTag("update session");

            AlertDialog dialog = (AlertDialog) sessionEditAddFragment.getDialog();

            //write time to dialog time input
            ((EditText)dialog.findViewById(R.id.start_time_input)).setText(timeString);
        }
    }

    //dialog fragment for add session
    static public class AddSessionFragment extends DialogFragment{
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            //configure builder
            builder.setTitle("Add Session")
                    .setView(R.layout.dialog_add_session)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();
        }
    }
}

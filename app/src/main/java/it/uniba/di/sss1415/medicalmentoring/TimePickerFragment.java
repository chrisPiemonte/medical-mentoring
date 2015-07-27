package it.uniba.di.sss1415.medicalmentoring;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;



/**
 * Created by Chris on 27/07/2015.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    Button bt;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        // Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        String timeFrom = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
        bt.setText(timeFrom);


    }
    public TimePickerFragment(Button v){

        this.bt = v;

    }

    public TimePickerFragment(){}


}
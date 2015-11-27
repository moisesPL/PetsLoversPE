package pe.edu.ulima.petapp.ui.navigator.Item;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.edu.ulima.petapp.R;

public class AddActividadFragment extends Fragment {

    @InjectView(R.id.output_actividad_date)
    TextView _fecha;
    @InjectView(R.id.output_actividad_hora)
    TextView _hora;
    @InjectView(R.id.sp_actividadType)
    Spinner _sp_tipo;
    @InjectView(R.id.sp_actividadAlerta)
    Spinner _sp_alerta;
    @InjectView(R.id.btnCalendarActividad)
    ImageButton _btn_fecha;
    @InjectView(R.id.btnHoraActividad)
    ImageButton _btn_hora;
    @InjectView(R.id.btn_actividad_register)
    Button _btn_register;

    private static final String TIME_PATTERN = "HH:mm";
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_actividad, container, false);
        ButterKnife.inject(this, view);

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        _btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        _btn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });

        _btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        return view;
    }


    private void showDateDialog(){
        new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                _fecha.setText(dateFormat.format(calendar.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(){
        new TimePickerDialog(getActivity(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                _hora.setText(timeFormat.format(calendar.getTime()));
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true).show();
    }

    public void add() {

        if (!validate()) {
            onAddFailed();
            return;
        }

        _btn_register.setEnabled(false);

        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registrando Actividad...");
        progressDialog.show();

        addActividadToParse();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        //onSignupSuccess();
                        // onSignupFailed();
                    }
                }, 3000);
    }

    public void onAddFailed() {
        Toast.makeText(getActivity(), "No se logro registrar el evento", Toast.LENGTH_LONG).show();

        _btn_register.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _fecha.getText().toString();
        String age = _hora.getText().toString();

        if (name.isEmpty()) {
            _fecha.setError("Seleccione una fecha");
            valid = false;
        } else {
            _fecha.setError(null);
        }

        if (age.isEmpty()) {
            _hora.setError("Seleccione una hora");
            valid = false;
        } else {
            _hora.setError(null);
        }

        return valid;
    }
    private void addActividadToParse(){
        String fecha= _fecha.getText().toString();
        String hora = _hora.getText().toString();
        String tipo = String.valueOf(_sp_tipo.getSelectedItem());

        ParseObject pet = new ParseObject("Actividad");
        pet.put("fecha", fecha);
        pet.put("hora", hora);
        pet.put("tipo", tipo);
        pet.put("alerta", hora);
        pet.put("petID", hora);
        //pet.put("tipo", tipo);

        progressDialog.cancel();
    }
}

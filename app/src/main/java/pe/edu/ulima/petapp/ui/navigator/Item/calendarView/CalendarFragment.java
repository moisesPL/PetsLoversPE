package pe.edu.ulima.petapp.ui.navigator.Item.calendarView;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.PetController;
import pe.edu.ulima.petapp.dao.Actividad;
import pe.edu.ulima.petapp.dao.Pet;


public class CalendarFragment extends Fragment {

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private ArrayList<Actividad> actividadArrayList;
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();
        actividadArrayList = new ArrayList<>();
        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }
        getActivitiesParse();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                if(!actividadArrayList.isEmpty())
                    for (int i = 0; i < actividadArrayList.size(); i++) {
                        Log.e("para mostrar", formatter.format(actividadArrayList.get(i).getDate()).toString() + " - " + formatter.format(date).toString());
                           if(formatter.format(actividadArrayList.get(i).getDate()).equals(formatter.format(date)))
                               showDialogActividad(actividadArrayList.get(i));
                    }

            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);
        return view;
    }

    private void showDialogActividad(Actividad actividad){
        Dialog dialog =null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.actividad_dialog, null);
        builder.setView(v);
        TextView txtMascota = (TextView)v.findViewById(R.id.txtActividadMascota);
        TextView txtFecha = (TextView)v.findViewById(R.id.txtActividadFecha);
        TextView txtHora = (TextView)v.findViewById(R.id.txtActividadHora);
        TextView txtTipo = (TextView)v.findViewById(R.id.txtActividadTipo);

        for (Pet tempo:PetController.getInstance().getPetArray()) {
            if(tempo.getPetId().equals(actividad.getPetID())){
                txtMascota.setText(tempo.getPetName());
                break;
            }
        }
        txtFecha.setText(formatter.format(actividad.getDate()));
        txtHora.setText(actividad.getTime());
        txtTipo.setText(actividad.getTipo());

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar Evento", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelarEvento();
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();



    }

    private void cancelarEvento() {

        Toast.makeText(getActivity(),"Actividad Cancelada",Toast.LENGTH_SHORT).show();

    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

    private void getActivitiesParse(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date tempdate = cal.getTime();
        actividadArrayList.add(new Actividad(tempdate,"10:00 am","cita mensual",1,"gTEi8ywUz5",null,1));
        cal.add(Calendar.DATE, 4);
        tempdate = cal.getTime();
        actividadArrayList.add(new Actividad(tempdate,"8:00 am","baño",1,null,null,2));
        cal.add(Calendar.DATE, 9);
        tempdate = cal.getTime();
        actividadArrayList.add(new Actividad(tempdate, "6:00 pm", "tratamiento", 1, null, null, 3));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Actividad");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("Actividad", "Retrieved " + scoreList.size() + " activities");
                    for (int i = 0; i < scoreList.size(); i++) {
                        ParseObject temp = (ParseObject) scoreList.get(i).get("petID");
                        Actividad temporalActividad = new Actividad(scoreList.get(i).getDate("fecha"), scoreList.get(i).get("hora").toString(), scoreList.get(i).get("tipo").toString()
                                , Integer.valueOf(scoreList.get(i).get("alerta").toString()), temp.getObjectId().toString(), scoreList.get(i).getObjectId().toString(), 1);
                        Log.e("temporalPet: ", temporalActividad.toString());
                        actividadArrayList.add(temporalActividad);
                    }
                    setCustomResourceForDates();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }

    private void setCustomResourceForDates() {

        for (int i = 0; i <actividadArrayList.size() ; i++) {
            if (caldroidFragment != null) {
                caldroidFragment.setBackgroundResourceForDate(R.color.green,
                        actividadArrayList.get(i).getDate());
                caldroidFragment.setTextColorForDate(R.color.white,
                        actividadArrayList.get(i).getDate());
            }
        }
        caldroidFragment.refreshView();

        /*Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            caldroidFragment.setBackgroundResourceForDate(R.color.blue,
                    blueDate);
            caldroidFragment.setBackgroundResourceForDate(R.color.green,
                    greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }*/
    }
}

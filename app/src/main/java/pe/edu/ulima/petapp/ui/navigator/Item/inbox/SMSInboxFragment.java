package pe.edu.ulima.petapp.ui.navigator.Item.inbox;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.SmsController;
import pe.edu.ulima.petapp.dao.Sms;

public class SMSInboxFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapterInbox recyclerAdapterInbox;
    ArrayList<Sms> smsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_inbox, container, false);
        // Inflate the layout for this fragment
        //smsArrayList = SmsController.getInstance().getSmsArrayList();
        smsArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyle_view_inbox);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerHeader = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManagerHeader);



        readSms();
        return view;
    }

    private void readSms(){
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cur = getActivity().getContentResolver().query(uriSMSURI, null, null, null, null);
        String sms = "";
        while (cur.moveToNext()) {
            String body= cur.getString(cur.getColumnIndex("body"));
            String number = cur.getString(2);
            if(body.startsWith("petApp")){
                sms = "From :" + number + " : " + body+"\n";
                Log.e("smsREader", sms);
                Sms temporalSmas= new Sms(body,number,""+0,"");
                smsArrayList.add(temporalSmas);
            }
            else
                Log.e("smsReader", "sms no pertenece a petApp");
        }
        mostrarInbox();
    }
    private void mostrarInbox(){
        recyclerAdapterInbox = new RecyclerAdapterInbox(getActivity(),smsArrayList);
        recyclerView.setAdapter(recyclerAdapterInbox);
    }
}

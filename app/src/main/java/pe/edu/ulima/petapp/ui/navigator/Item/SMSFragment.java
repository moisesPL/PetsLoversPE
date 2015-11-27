package pe.edu.ulima.petapp.ui.navigator.Item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.edu.ulima.petapp.R;

public class SMSFragment extends Fragment {
    @InjectView(R.id.send)
    Button _btnSendSMS;
    @InjectView(R.id.mobileNumber)
    EditText _txtPhoneNo;
    @InjectView(R.id.smsBody)
    EditText _txtMessageBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_sms, container, false);
        ButterKnife.inject(this, view);

        _btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = _txtPhoneNo.getText().toString();
                String sms = _txtMessageBody.getText().toString();
                sms = "petApp "+sms;

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(getActivity(), "SMS enviado",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(),
                            "SMS no enviado, pruebe otra vez",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

}

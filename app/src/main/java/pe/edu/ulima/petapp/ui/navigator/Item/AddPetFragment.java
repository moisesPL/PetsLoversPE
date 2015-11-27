package pe.edu.ulima.petapp.ui.navigator.Item;


import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.PetController;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.dao.Pet;


public class AddPetFragment extends Fragment {
    private static final String TAG = "AddPetFragment";
    ProgressDialog progressDialog;
    View view;
    @InjectView(R.id.input_pet_name)
    EditText _nameText;
    @InjectView(R.id.input_pet_age) EditText _ageText;
    @InjectView(R.id.btn_pet_register) Button _signupButton;
    @InjectView(R.id.btn_pet_pic) Button _picButton;
    @InjectView(R.id.sp_petType) Spinner _spPetType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_add_pet, container, false);
        ButterKnife.inject(this,view);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        _picButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPhoto();
            }
        });
        return view;
    }

    public void add() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onAddFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Agregando mascota...");
        progressDialog.show();



        registerParse();

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
        Toast.makeText(getActivity(), "No se logro agregar la mascota", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String age = _ageText.getText().toString();

        if (name.isEmpty()) {
            _nameText.setError("No puede estar vacio");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (age.isEmpty()) {
            _ageText.setError("No puede estar vacio");
            valid = false;
        } else {
            _ageText.setError(null);
        }

        return valid;
    }

    private void registerParse(){
        final String name = _nameText.getText().toString();
        final String type = String.valueOf(_spPetType.getSelectedItem());
        final String age = _ageText.getText().toString();
        ParseFile file=null;

        ParseObject pet = new ParseObject("Pet");
        pet.put("petName", name);
        pet.put("petType", type);
        pet.put("petAge", age);
        pet.put("user", ParseObject.createWithoutData("_User", UserController.getInstance().getUser().getUserCode().toString()));
        if (conFoto) {
            Log.e("registrando", "confoto=true");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] data = stream.toByteArray();
            //System.out.println(bytearray.toString());  //test case
            file = new ParseFile(name + ".jpg", data);
            file.saveInBackground();
            pet.put("petImage", file);
        }
        final Pet peto = new Pet(false,type,name,age,file);
        Log.e("Peto To add",peto.toString());
        pet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    progressDialog.cancel();
                    PetController.getInstance().getPetArray().add(peto);
                    Toast.makeText(getActivity(), _nameText+" registrado con éxito", Toast.LENGTH_LONG).show();

                } else {
                    _signupButton.setEnabled(true);
                    progressDialog.cancel();
                    Toast.makeText(getActivity(), "No se logro registrar a "+ _nameText,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private static int RESULT_LOAD_IMG = 1;
    boolean conFoto=false;
    Bitmap bitMap;
    String imgDecodableString;
    public void pickPhoto() {
        //TODO: launch the photo picker
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) view.findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                bitMap = BitmapFactory
                        .decodeFile(imgDecodableString);
                Log.e("bitmap",bitMap.toString());
                conFoto=true;

            } else {
                Toast.makeText(getActivity(), "Imagen no seleccionada",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Algo ocurrio mal", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
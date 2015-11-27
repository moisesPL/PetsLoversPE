package pe.edu.ulima.petapp;

import android.app.Application;

import com.parse.Parse;

public class Aplicacion extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "fmGW5fef1skJCEA1pLxiqz2sJyDot6yaZeBqkCzH", "kvgWWGWLpYxgsBaKMfWEU0063i4sB0oGP8tcckaJ");


    }
}

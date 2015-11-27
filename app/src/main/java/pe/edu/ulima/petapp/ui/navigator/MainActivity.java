package pe.edu.ulima.petapp.ui.navigator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pe.edu.ulima.petapp.R;
import pe.edu.ulima.petapp.controller.UserController;
import pe.edu.ulima.petapp.ui.navigator.Item.AddActividadFragment;
import pe.edu.ulima.petapp.ui.navigator.Item.AddPetFragment;
import pe.edu.ulima.petapp.ui.navigator.Item.SMSFragment;
import pe.edu.ulima.petapp.ui.navigator.Item.SettingFragment;
import pe.edu.ulima.petapp.ui.navigator.Item.calendarView.CalendarFragment;
import pe.edu.ulima.petapp.ui.navigator.Item.inbox.SMSInboxFragment;
import pe.edu.ulima.petapp.ui.navigator.Item.petProfile.ProfilePetFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @InjectView(R.id.txtHeaderName)
    TextView _nameHeader;
    @InjectView(R.id.txtHeaderEmail)
    TextView _emailHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.inject(this);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeaderUser();

        goToFragment(new ProfilePetFragment());
    }

    private void navHeaderUser(){
        _nameHeader.setText(UserController.getInstance().getUser().getUserName());
        _emailHeader.setText(UserController.getInstance().getUser().getUserEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_pet) {
            goToFragment(new AddPetFragment());
        } else if (id == R.id.nav_profile_pet) {
            goToFragment(new ProfilePetFragment());
        } else if (id == R.id.nav_send_sms) {
            goToFragment(new SMSFragment());
        } else if (id == R.id.nav_all_sms) {
            goToFragment(new SMSInboxFragment());
        } else if (id == R.id.nav_activities) {
            goToFragment(new CalendarFragment() );
        } else if (id == R.id.nav_new_activity) {
            goToFragment(new AddActividadFragment());
        } else if(id == R.id.nav_setting) {
            goToFragment(new SettingFragment());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void goToFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }
}

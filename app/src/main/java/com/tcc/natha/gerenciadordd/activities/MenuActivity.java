package com.tcc.natha.gerenciadordd.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tcc.natha.gerenciadordd.R;
import com.tcc.natha.gerenciadordd.fragments.ViewPagePersonagem;
import com.tcc.natha.gerenciadordd.fragments.AventuraFragment;
import com.tcc.natha.gerenciadordd.fragments.EditAventuraFragment;
import com.tcc.natha.gerenciadordd.fragments.EditPersonagemFragment;
import com.tcc.natha.gerenciadordd.fragments.PersonagemFragment;
import com.tcc.natha.gerenciadordd.fragments.ResistenciaPericiaFragment;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PersonagemFragment.OnFragmentInteractionListener,
        AventuraFragment.OnFragmentInteractionListener,
        EditPersonagemFragment.OnFragmentInteractionListener,
        EditAventuraFragment.OnFragmentInteractionListener,
        ViewPagePersonagem.OnFragmentInteractionListener,
        ResistenciaPericiaFragment.OnFragmentInteractionListener
{

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private static final String TAG = "MenuActivity";
    private FirebaseUser user;
    private Context context;
    private TextView mEmailMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Views
        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        mEmailMenu = (TextView) headerLayout.findViewById(R.id.email_menu);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (user.getEmail() != null) {
                        mEmailMenu.setText(user.getEmail().toString());
                    }
                }
            }
        };

        //começa com a tela de personagem carregada
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment personagemFragment = new PersonagemFragment();
        transaction.replace(R.id.headlines_fragment, personagemFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment personagemFragment = new PersonagemFragment();
        Fragment aventuraFragment = new AventuraFragment();
        if (id == R.id.nav_camera) {
            transaction.replace(R.id.headlines_fragment, personagemFragment);
        } else if (id == R.id.nav_gallery) {
            transaction.replace(R.id.headlines_fragment, aventuraFragment);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            context = this.getApplicationContext();
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(Uri uri){

    }
}

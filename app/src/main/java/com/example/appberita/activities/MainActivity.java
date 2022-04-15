package com.example.appberita.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appberita.R;
import com.example.appberita.activities.login.LoginActivity;
import com.example.appberita.fragments.SearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private FirebaseUser firebaseUser;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private static final String CATEGORY="category";
    private static final String TOOLBAR_TITLE="toolbar_title";
    private NavController navController;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("Apakah kamu yakin")
                .setPositiveButton("ok", (dialogInterface, i) -> finish())
                .setNegativeButton("cencel", (dialogInterface, i) -> dialogInterface.cancel())
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.general:
                navController.navigate(R.id.generalFragment);
                break;
            case R.id.business:
                navController.navigate(R.id.businessFragment);
                break;
            case R.id.health:
                navController.navigate(R.id.healthFragment);
                break;
            case R.id.science:
                navController.navigate(R.id.scienceFragment);
                break;
            case R.id.sport:
                navController.navigate(R.id.sportFragment);
                break;
            case R.id.technology:
                navController.navigate(R.id.technologyFragment);
                break;
            case R.id.entertainment:
                navController.navigate(R.id.entertainmentFragment);
                break;
            case R.id.note:
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




//    private void exit(){
//        //Creating an alert dialog to confirm logout
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("Anda Yakin Ingin Keluar ?");
//        alertDialogBuilder.setPositiveButton("Iya",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent bus = new Intent(MainActivity.this, LoginActivity.class);
//                        startActivity(bus);
//                    }
//                });
//
//        alertDialogBuilder.setNegativeButton("Tidak",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                    }
//                });
//
//        //Showing the alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//
//    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        final MenuItem item = menu.findItem(R.id.search);
        searchView =(SearchView)menu.findItem(R.id.search).getActionView();
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                onBackPressed();
                return true;
            }
        });
        searchView.setQueryHint("Type a word or a phrase");
        searchView.setOnQueryTextListener(this);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchFragment searchFragment=new SearchFragment();
        String searchFor=  searchView.getQuery().toString();
        switch (navController.getCurrentDestination().getId()){
            case R.id.businessFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.setCategory("business");
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.generalFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.setCategory("general");
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.scienceFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.setCategory("science");
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.sportFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.setCategory("sports");
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.healthFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.setCategory("health");
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.technologyFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.setCategory("technology");
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.entertainmentFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.setCategory("entertainment");
                navController.navigate(R.id.searchFragment);
                break;
            case R.id.searchFragment:
                searchFragment.setSearchfor(searchFor);
                searchFragment.loadNews();
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

        //    @Override
    //    public void setSupportActionBar(@Nullable Toolbar toolbar) {
    //        super.setSupportActionBar(toolbar);
    //    }
}

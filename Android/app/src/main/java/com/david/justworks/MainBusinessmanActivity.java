package com.david.justworks;

import static com.david.justworks.serverCommunication.Messages.CL_LOGIN;
import static com.david.justworks.serverCommunication.Messages.CL_LOGOUT;
import static com.david.justworks.serverCommunication.Messages.CL_USER_DETAILS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.david.justworks.entities.User;
import com.david.justworks.login.LoginActivity;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.ui.users.UserViewer;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.david.justworks.databinding.ActivityMainBusinessmanBinding;

public class MainBusinessmanActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBusinessmanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBusinessmanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainBusinessman.toolBar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        TextView nameText = navigationView.getHeaderView(0).findViewById(R.id.navHeaderNameText);
        TextView emailText = navigationView.getHeaderView(0).findViewById(R.id.navHeaderEmailText);
        CommunicationMethods.getInstance().sendMessage(CL_USER_DETAILS);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        for(int i= 1;i<processedInput.length;i=i+6){
            User user= new User(processedInput[i],processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],processedInput[i+5] );

            nameText.setText(user.getName()+" "+user.getSurname());
            emailText.setText(user.getEmail());
        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_offers,R.id.nav_all_offers,R.id.nav_welcome,R.id.nav_my_notifications)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserViewer.class);
                startActivity(intent);
            }
        });

        Button logoutButton = navigationView.getHeaderView(0).findViewById(R.id.navHeaderButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunicationMethods.getInstance().sendMessage(CL_LOGOUT);
                String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                if(processedInput[1].equals("C")){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
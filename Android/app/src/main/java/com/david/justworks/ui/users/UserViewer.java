package com.david.justworks.ui.users;

import static com.david.justworks.serverCommunication.Messages.CL_USER_DETAILS;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.david.justworks.R;
import com.david.justworks.entities.User;
import com.david.justworks.serverCommunication.CommunicationMethods;

public class UserViewer extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_viewer);

        CommunicationMethods.getInstance().sendMessage(CL_USER_DETAILS);
        String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
        for(int i= 1;i<processedInput.length;i=i+6){
            user= new User(processedInput[i],processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],processedInput[i+5] );

        }

        TextView dniTextView = findViewById(R.id.userViewerDNIText);
        TextView nameTextView = findViewById(R.id.userViewerNameText);
        TextView surnameTextView = findViewById(R.id.userViewersurnameText);
        TextView emailTextView = findViewById(R.id.userViewerEmailText);
        TextView userTextView = findViewById(R.id.userViewerUserText);
        TextView passwordTextView = findViewById(R.id.userViewerPasswordText);

        dniTextView.setText(user.getDni());
        nameTextView.setText(user.getName());
        surnameTextView.setText(user.getSurname());
        emailTextView.setText(user.getEmail());
        userTextView.setText(user.getUser());
        passwordTextView.setText(user.getPassword());

    }
}
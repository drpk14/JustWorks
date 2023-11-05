package com.david.justworks.login;

import static com.david.justworks.serverCommunication.Messages.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.david.justworks.MainBusinessmanActivity;
import com.david.justworks.MainWorkerActivity;
import com.david.justworks.R;

import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.serverCommunication.CommunicationThread;
import com.david.justworks.serverCommunication.CommunicationThreadUDP;
import com.david.justworks.serverCommunication.SharedCollection;

public class LoginActivity extends AppCompatActivity {

    private EditText userEditText;
    private EditText passwordEditText;
    private Button loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEditText = findViewById(R.id.user);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = userEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(userEditText.getText().length()>0 && passwordEditText.getText().length()>0){
                    CommunicationMethods.getInstance().sendMessage(CL_LOGIN+":"+user+":"+password);
                    String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                    System.out.println(processedInput);
                    if(processedInput[1].equals("C")){

                        if(processedInput[2].equals("B")){
                            Intent intent = new Intent(LoginActivity.this, MainBusinessmanActivity.class);
                            startActivity(intent);
                        }else if(processedInput[2].equals("W")){
                            Intent intent = new Intent(LoginActivity.this, MainWorkerActivity.class);
                            startActivity(intent);
                        }

                        if(processedInput[4].equals("True")){
                            Toast.makeText(getApplicationContext(), "You have new notifications", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "All the fields must be filed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
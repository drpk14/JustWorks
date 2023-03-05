package com.david.justworks.serverCommunication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.david.justworks.R;

import com.david.justworks.login.LoginActivity;

public class ConnectionActivity extends AppCompatActivity {

    private EditText ipEditText;
    private EditText portEditText;
    private Button connectionButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ipEditText = findViewById(R.id.ip_direction);
        portEditText = findViewById(R.id.port);
        connectionButton = findViewById(R.id.connection);

        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ipEditText.getText().length()>0 && portEditText.getText().length()>0){
                    String ip = ipEditText.getText().toString();
                    int port = Integer.parseInt(portEditText.getText().toString());
                    SharedCollection sharedCollection = new SharedCollection();
                    CommunicationMethods.getInstance().setSharedCollection(sharedCollection);
                    new CommunicationThread(ip,port,sharedCollection).start();
                    Intent intent = new Intent(ConnectionActivity.this, LoginActivity.class);

                    startActivity(intent);
                }
            }
        });



    }
}
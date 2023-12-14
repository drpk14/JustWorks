package com.david.justworks.login;


import static com.david.justworks.serverCommunication.Messages.CL_LOGIN;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.david.justworks.MainBusinessmanActivity;
import com.david.justworks.MainWorkerActivity;
import com.david.justworks.R;
import com.david.justworks.databinding.FragmentLoginBinding;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.serverCommunication.CommunicationThread;
import com.david.justworks.serverCommunication.CommunicationThreadUDP;
import com.david.justworks.serverCommunication.SharedCollection;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private static boolean connected = false;

    public static boolean isConnected() {
        return connected;
    }

    public static void setConnected(boolean connected) {
        LoginFragment.connected = connected;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = binding.userEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();

                if(binding.userEditText.getText().length()>0 && binding.passwordEditText.getText().length()>0){
                    CommunicationMethods.getInstance().sendMessage(CL_LOGIN+":"+user+":"+password);
                    String[] processedInput = CommunicationMethods.getInstance().recieveMessage().split(":");
                    if(processedInput[1].equals("C")){

                        if(processedInput[2].equals("B")){
                            Intent intent = new Intent(getContext(), MainBusinessmanActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else if(processedInput[2].equals("W")){
                            Intent intent = new Intent(getContext(), MainWorkerActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        if(processedInput[4].equals("True")){
                            Toast.makeText(getContext(), "You have new notifications", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getContext(), processedInput[2], Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "All the fields must be filed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.actionToConnectionFragment);
            }
        });
        if(!LoginFragment.isConnected()) {
            connection();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void connection(){
        SharedPreferences preferences = getActivity().getPreferences(0);
        SharedCollection sharedCollection = new SharedCollection();
        CommunicationMethods.getInstance().setSharedCollection(sharedCollection);
        String IP = preferences.getString("IP", "");
        String TCPPort = preferences.getString("TCPPort", "");
        String UDPPort = preferences.getString("UDPPort", "");

        if (!IP.equals("") && !TCPPort.equals("") && !UDPPort.equals("")) {
            new CommunicationThread(IP, Integer.parseInt(TCPPort), sharedCollection).start();
            synchronized (sharedCollection) {
                try {
                    sharedCollection.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (!LoginFragment.isConnected()) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.actionToConnectionFragment);
                Toast.makeText(getContext(), "No possible to connect to the server", Toast.LENGTH_SHORT).show();
            } else {
                new CommunicationThreadUDP(IP, Integer.parseInt(UDPPort),getContext()).start();
            }
        } else {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.actionToConnectionFragment);
            Toast.makeText(getContext(), "Fill all the config parametres", Toast.LENGTH_SHORT).show();
        }
    }
}
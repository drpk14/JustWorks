package com.david.justworks.login;

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

import com.david.justworks.R;
import com.david.justworks.databinding.FragmentConnectionBinding;
import com.david.justworks.serverCommunication.CommunicationMethods;
import com.david.justworks.serverCommunication.CommunicationThread;
import com.david.justworks.serverCommunication.CommunicationThreadUDP;
import com.david.justworks.serverCommunication.SharedCollection;

public class ConnectionFragment extends Fragment {

    private FragmentConnectionBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentConnectionBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        binding.buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loadSettings();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void saveSettings() {
        SharedPreferences preferences = getActivity().getPreferences(0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("IP", binding.ipSettingEditText.getText().toString());
        editor.putString("TCPPort", binding.TPCPortSettingEditText.getText().toString());
        editor.putString("UDPPort", binding.UDPPortSettingEditText.getText().toString());
        editor.apply();

        NavHostFragment.findNavController(ConnectionFragment.this)
                .navigate(R.id.actionToLoginFragment);
    }

    private void loadSettings() {
        SharedPreferences preferences = getActivity().getPreferences(0);
        binding.ipSettingEditText.setText(preferences.getString("IP", ""));
        binding.TPCPortSettingEditText.setText(preferences.getString("TCPPort", ""));
        binding.UDPPortSettingEditText.setText(preferences.getString("UDPPort", ""));
    }

    /*private void connection(){
        SharedPreferences preferences = getActivity().getPreferences(0);
        SharedCollection sharedCollection = new SharedCollection();
        CommunicationMethods.getInstance().setSharedCollection(sharedCollection);
        String IP = preferences.getString("IP", "");
        String TCPPort = preferences.getString("TCPPort", "");
        String UDPPort = preferences.getString("UDPPort", "");
        if(!IP.equals("") && !TCPPort.equals("") && !UDPPort.equals("")) {
            binding.infoText.setTextColor(Color.YELLOW);
            binding.infoText.setText("Conecting ...");

            new CommunicationThread(preferences.getString("IP", ""), Integer.parseInt(preferences.getString("TCPPort", "")), sharedCollection).start();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("DIMELOMANIN", "HA PASADO EL SLEEP");

            if (LoginFragment.isConnected()) {
                NavHostFragment.findNavController(ConnectionFragment.this)
                        .navigate(R.id.actionToLoginFragment);
                new CommunicationThreadUDP(preferences.getString("IP", ""), Integer.parseInt(preferences.getString("UDPPort", ""))).start();

            } else {
                binding.infoText.setTextColor(Color.RED);
                binding.infoText.setText("Error, try again");
            }
        }else{
            Toast.makeText(getContext(), "Fill all the config parametres", Toast.LENGTH_SHORT).show();
        }
    }*/
}
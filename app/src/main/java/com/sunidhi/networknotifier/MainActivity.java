package com.sunidhi.networknotifier;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.sunidhi.networknotifier.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{

    private NetworkStateReceiver networkStateReceiver;
    int count = 0;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        startNetworkBroadcastReceiver(this);

        /**
         * Call Fragment
         */
        binding.fragmentButton.setOnClickListener(v -> {
            binding.fragmentButton.setVisibility(View.GONE);
            Fragment fragment = new FragmentOne();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.parentLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        });
    }

    /**
     * Network Available
     */
    @Override
    public void networkAvailable() {
        if(count == 1){
            Snackbar.make(binding.parentLayout, "Back Online", Snackbar.LENGTH_SHORT).show();
        }

    }


    /**
     * Network Unavailable
     */
    @Override
    public void networkUnavailable() {
        Snackbar.make(binding.parentLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).show();
        count = 1;

    }

    /**
     * Network State Receiver
     */
    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    /**
     * Register the NetworkStateReceiver with activity
     */
    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     Unregister the NetworkStateReceiver with activity
     */
    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }


    @Override
    protected void onPause() {
        unregisterNetworkBroadcastReceiver(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerNetworkBroadcastReceiver(this);
        super.onResume();

    }
}
package com.sunidhi.networknotifier;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.sunidhi.networknotifier.databinding.FragmentOneBinding;

public class FragmentOne extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener{

    private NetworkStateReceiver networkStateReceiver;
    int count = 0;
    FragmentOneBinding binding;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        startNetworkBroadcastReceiver(context);
        return inflater.inflate(R.layout.fragment_one, container, false);
    }


    /**
     * Network Available
     */
    @Override
    public void networkAvailable() {
        if(count == 2){
            Snackbar.make(binding.parentLayout, "Back Online", Snackbar.LENGTH_SHORT).show();
        }

    }

    /**
     * Network Unavailable
     */
    @Override
    public void networkUnavailable() {
        Snackbar.make(binding.parentLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).show();
        count = 2;

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

}
package shet.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import still.interactive.shet.R;

/*

TODO Rework this class!!

 */
public class ScrumSyncActivity extends Activity {

    private String mUsername;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private ServerSocket mServerSocket;
    private int mLocalPort;
    private NsdManager mNsdManager;
    private NsdManager.RegistrationListener mRegistrationListener;
    private NsdManager.ResolveListener mResolveListener;
    private List<NsdServiceInfo> mDiscoveredServices;
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private boolean mRegistred = false;

    public ScrumSyncActivity() {
        constructListeners();
        mDiscoveredServices = new ArrayList<NsdServiceInfo>();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrum_sync);

        mUsername = "Stuff...";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("STUFF_HERE");
            if (username != null)
                mUsername = username;
        }
        Log.i(getClass().getName(),"Username: " + mUsername);

        mProgressBar = (ProgressBar) findViewById(R.id.scrum_sync_progressbar);
        mListView = (ListView) findViewById(R.id.scrum_sync_listview);
        try {
            initializeServerSocket();
        } catch (IOException exception) {
            Log.e(getClass().getName(),"Got exception from starting sync process", exception);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNsdServices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNsdServices();
    }

    private void unregisterNsdServices() {
        if (mRegistred) {
            mNsdManager.unregisterService(mRegistrationListener);
            mNsdManager.stopServiceDiscovery(mDiscoveryListener);
            mRegistred = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerService();
        mNsdManager.discoverServices(MainActivity.SCRUM_SERVICE_NAME, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    private void initializeServerSocket() throws IOException {
        // Initialize a server socket on the
        // next available port.
        mServerSocket = new ServerSocket(0);

        // Store the chosen port.
        mLocalPort = mServerSocket.getLocalPort();
    }

    public void registerService() {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(mUsername);
        serviceInfo.setServiceType(MainActivity.SCRUM_SERVICE_NAME);
        serviceInfo.setPort(mLocalPort);
        mNsdManager = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
        Log.i(getClass().getName(),"Registering service...");
        mNsdManager.registerService(
                serviceInfo,
                NsdManager.PROTOCOL_DNS_SD,
                mRegistrationListener);
        Log.i(getClass().getName(),"Service registered...");
        mRegistred = true;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrum_sync, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void constructListeners() {

        mRegistrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                mUsername = serviceInfo.getServiceName();

            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {

            }
        };

        mDiscoveryListener = new NsdManager.DiscoveryListener(){

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {

            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {

            }

            @Override
            public void onDiscoveryStarted(String serviceType) {

            }

            @Override
            public void onDiscoveryStopped(String serviceType) {

            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                if (!serviceInfo.getServiceType().equals(MainActivity.SCRUM_SERVICE_NAME)) {
                    Log.e("ScrumSyncActivity","Unknown service found => " + serviceInfo.getServiceName());
                } else if (serviceInfo.getServiceName().equals(mUsername)) {
                    Log.i("ScrumSyncActivity","Same device:" + serviceInfo.getServiceName());
                } else {
                    Log.i("XALEOSK", "FOUND SERVICE!!! " + serviceInfo.getServiceName());
                    mDiscoveredServices.add(serviceInfo);
                    mNsdManager.resolveService(serviceInfo, mResolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                if (!serviceInfo.getServiceType().equals(MainActivity.SCRUM_SERVICE_NAME)) {
                    Log.e("ScrumSyncActivity","Unknown service found => " + serviceInfo.getServiceName());
                } else if (serviceInfo.getServiceName().equals(mUsername)) {
                    Log.i("ScrumSyncActivity","Same device:" + serviceInfo.getServiceName());
                } else {
                    Log.i("XALEOSK","FOUND SERVICE!!! " + serviceInfo.getServiceName());
                    if (mDiscoveredServices.contains(serviceInfo))
                        mDiscoveredServices.remove(serviceInfo);
                }
            }
        };
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {

            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.i("XALEOSK","SERVICE REOLSVED");

            }
        };
    }
}

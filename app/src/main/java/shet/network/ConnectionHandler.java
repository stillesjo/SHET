package shet.network;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import shet.activities.MainActivity;

/**
 * Created by stillesjo on 14-11-16.
 */
public class ConnectionHandler {
    private static final String CONNECTION_HANDLER_TAG = ConnectionHandler.class.getName();
    private final NsdManager mNsdManager;
    private final ScrumServiceListener mServiceListener;
    private boolean mServiceOngoing;
    private ServerSocket mServerSocket;
    private int mLocalPort;
    private String mUsername;
    private NsdManager.RegistrationListener mRegistrationListener;
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private NsdManager.ResolveListener mResolveListener;

    private List<NsdServiceInfo> mPotentialUsers;
    private List<NsdServiceInfo> mConnectedUsers;
    private boolean mSocketAllocated;

    public ConnectionHandler(NsdManager manager, String serviceName, ScrumServiceListener serviceListener) {
        mNsdManager = manager;
        mUsername = serviceName;
        mServiceListener = serviceListener;
        createListeners();
        mServiceOngoing = false;
        mSocketAllocated = false;
        mPotentialUsers = new ArrayList<NsdServiceInfo>();
        mConnectedUsers = new ArrayList<NsdServiceInfo>();
    }

    public void initializeAndStartService() throws IOException {
        allocateSocket();
        restartServices();
    }

    public void pauseService() {
        if (mServiceOngoing) {
            mNsdManager.unregisterService(mRegistrationListener);
            mNsdManager.stopServiceDiscovery(mDiscoveryListener);
            mServiceOngoing = true;
        }
    }

    private void allocateSocket() throws IOException {
        // Initialize a server socket on the next available port.
        mServerSocket = new ServerSocket(0);

        // Store the chosen port.
        mLocalPort = mServerSocket.getLocalPort();
        mSocketAllocated = true;
    }

    public void restartServices() {
        startServices();
    }

    private void startServices() {
        if (!mSocketAllocated) {
            Log.e(CONNECTION_HANDLER_TAG,"Socket not initialized before trying to start the service... Will return");
            return;
        }

        registerService();
        mNsdManager.discoverServices(MainActivity.SCRUM_SERVICE_NAME, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
        mServiceOngoing = true;
    }

    private void registerService() {
        // Create nsd service object, containing all the information needed in order to start a connection.
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(mUsername);
        serviceInfo.setServiceType(MainActivity.SCRUM_SERVICE_NAME);
        serviceInfo.setPort(mLocalPort);

        // Attempt to register the service
        Log.i(CONNECTION_HANDLER_TAG, "Registering service.");
        mNsdManager.registerService(
                serviceInfo,
                NsdManager.PROTOCOL_DNS_SD,
                mRegistrationListener);
        Log.i(CONNECTION_HANDLER_TAG, "Registration completed.");

    }

    public interface ScrumServiceListener {
        public void connectionSuccessful(String user);
        public void connectionFailed(String user);
        public void foundUser(String user);
        public void lostUser(String user);
    }

    private void createListeners() {
        mRegistrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
                Log.i(CONNECTION_HANDLER_TAG,"onRegistrationFailed");
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
                Log.i(CONNECTION_HANDLER_TAG,"onUnregistrationFailed");
            }

            @Override
            public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
                Log.i(CONNECTION_HANDLER_TAG,"onServiceRegistered");
                mUsername = nsdServiceInfo.getServiceName();
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
                Log.i(CONNECTION_HANDLER_TAG,"onServiceUnregistered");
            }
        };
        mDiscoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String s, int i) {
                Log.i(CONNECTION_HANDLER_TAG,"onStartDiscoveryFailed");
            }

            @Override
            public void onStopDiscoveryFailed(String s, int i) {
                Log.i(CONNECTION_HANDLER_TAG,"onStopDiscoveryFailed");
            }

            @Override
            public void onDiscoveryStarted(String s) {
                Log.i(CONNECTION_HANDLER_TAG,"onDiscoveryStarted");
            }

            @Override
            public void onDiscoveryStopped(String s) {
                Log.i(CONNECTION_HANDLER_TAG,"onDiscoveryStopped");
            }

            @Override
            public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
                Log.i(CONNECTION_HANDLER_TAG,"onServiceFound");
                if (!nsdServiceInfo.getServiceType().equals((MainActivity.SCRUM_SERVICE_NAME))) {
                    Log.e(CONNECTION_HANDLER_TAG,"Unknown service found: " + nsdServiceInfo.getServiceType());
                } else if (nsdServiceInfo.getServiceName().equals(mUsername)) {
                    Log.i(CONNECTION_HANDLER_TAG,"Same device: " + nsdServiceInfo.getServiceName());
                } else {
                    Log.i(CONNECTION_HANDLER_TAG, "New service found: " + nsdServiceInfo.getServiceName());
                    mPotentialUsers.add(nsdServiceInfo);
                    mServiceListener.foundUser(nsdServiceInfo.getServiceName());
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
                Log.i(CONNECTION_HANDLER_TAG,"onServiceLost. ServiceName: " + nsdServiceInfo.getServiceName());
                mServiceListener.lostUser(nsdServiceInfo.getServiceName());
            }
        };
        mResolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
                Log.i(CONNECTION_HANDLER_TAG, "Service failed to resolve: " + nsdServiceInfo.getServiceType() + ", unit: " + nsdServiceInfo.getServiceName());
                mServiceListener.connectionFailed(nsdServiceInfo.getServiceName());
            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
                Log.i(CONNECTION_HANDLER_TAG, "Service resolved: " + nsdServiceInfo.getServiceType() + ", unit: " + nsdServiceInfo.getServiceName());
                mServiceListener.connectionSuccessful(nsdServiceInfo.getServiceName());
            }
        };
    }
}

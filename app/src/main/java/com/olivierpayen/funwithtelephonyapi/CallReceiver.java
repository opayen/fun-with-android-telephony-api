package com.olivierpayen.funwithtelephonyapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "CallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        OutgoingIncomingCallListener phoneListener = new OutgoingIncomingCallListener();
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {

            telephony.listen(phoneListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    class OutgoingIncomingCallListener extends PhoneStateListener {
        boolean wasRinging;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i(LOG_TAG, "RINGING");
                    wasRinging = true;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(LOG_TAG, "OFFHOOK");

                    if (!wasRinging) {
                        Log.i(LOG_TAG, "!wasRinging");
                    } else {
                        Log.i(LOG_TAG, "wasRinging");
                    }

                    wasRinging = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(LOG_TAG, "IDLE");
                    wasRinging = false;
                    break;
            }
        }
    }
}
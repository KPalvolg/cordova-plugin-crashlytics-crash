package org.apache.cordova.crashlyticscrash;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class CrashlyticsCrash extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("crash")) {
            this.crashMethod(callbackContext);
            return true;
        }
        return false;
    }

    private void crashMethod(CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Class.forName("com.crashlytics.android.Crashlytics");
                    //Crashlytics exists

                    com.crashlytics.android.Crashlytics.getInstance().crash();
                    callbackContext.success("App failed successfully.");
                } catch(ClassNotFoundException cnfe) {
                    //Crashlytics does not exist
                    callbackContext.error("Missing core Crashlytics plugin.");
                }
            }
        });
    }
}

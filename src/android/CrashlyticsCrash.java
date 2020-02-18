package org.apache.cordova.crashlyticscrash;

import org.apache.cordova.CordovaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    private void crashMethod(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                Class<?> crashlyticsClass = null;
                Method method = null;
                Method crash = null;
                Object obj = null;
                Class[] params = null;
                try {
                    crashlyticsClass = Class.forName("com.crashlytics.android.Crashlytics");
                    method = crashlyticsClass.getMethod("getInstance", params);
                    crash = crashlyticsClass.getMethod("crash", params);
                    if(crash == null || method == null) {
                        callbackContext.error("Error");
                    }
                    //Crashlytics exists

                    obj = method.invoke(null);
                    crash.invoke(obj);
                    callbackContext.success("App failed successfully.");
                } catch(ClassNotFoundException cnfe) {
                    //Crashlytics does not exist
                    callbackContext.error("Missing core Crashlytics plugin.");
                } catch (NoSuchMethodException e) {
                    callbackContext.error("Error");
                } catch (SecurityException e) {
                    callbackContext.error("Error");
                } catch (IllegalAccessException e) {
                    callbackContext.error("Error");
                } catch (InvocationTargetException e) {
                    callbackContext.error("Error");
                }
            }
        });
    }
}

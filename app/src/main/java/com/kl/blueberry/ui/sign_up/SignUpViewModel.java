package com.kl.blueberry.ui.sign_up;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.kl.blueberry.data.AppPreferences;
import com.kl.blueberry.data.Database;
import com.kl.blueberry.events.OpenActivityEvent;
import com.kl.blueberry.model.register_user.User;
import com.kl.blueberry.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Kaltrina Lubovci on 09,June,2020
 */
public class SignUpViewModel extends ViewModel {

    public void registerUser(Context context, AppPreferences appPreferences, String fullName, String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put(User.Fullname, fullName);
        cv.put(User.Username, username);
        cv.put(User.Email, email);
        cv.put(User.Password, password);

        SQLiteDatabase objDb = new Database(context).getWritableDatabase();

        try {
            long retValue = objDb.insert(Database.UserTable, null, cv);
            if (retValue > 0) {
                Toast.makeText(context, "ID inserted: " + retValue,
                        Toast.LENGTH_LONG).show();
                appPreferences.setFullName(fullName);
                appPreferences.setUsername(username);
                appPreferences.setEmail(email);
                appPreferences.setPassword(password);
                EventBus.getDefault().post(new OpenActivityEvent(new MainActivity()));
            }
        } catch (Exception ex) {
            Log.e("excep", ex.getMessage());
        } finally {
            objDb.close();
        }
    }
}

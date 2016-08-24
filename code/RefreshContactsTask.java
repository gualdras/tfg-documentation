package com.university.gualdras.tfgapp.domain.network;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.university.gualdras.tfgapp.Constants;
import com.university.gualdras.tfgapp.ServerSharedConstants;
import com.university.gualdras.tfgapp.domain.ContactItem;
import com.university.gualdras.tfgapp.gcm.ServerComunication;
import com.university.gualdras.tfgapp.persistence.DataProvider;
import com.university.gualdras.tfgapp.presentation.contactsTab.ContactTab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by gualdras on 6/03/16.
 */

public class RefreshContactsTask extends AsyncTask<Context, Void, String> {

    String TAG = "refreshContactTab";
    Boolean error = true;
    Context mContext;

    @Override
    protected String doInBackground(Context... params) {
        mContext = params[0];
        String data = "";

        HttpURLConnection httpURLConnection = null;

        JSONObject jsonParams = processPhoneNumbers();

        try {
            httpURLConnection = ServerComunication.post(Constants.USERS_URL, jsonParams, Constants.MAX_ATTEMPTS);
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                data = NetworkUtils.readStream(in);
                error = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != httpURLConnection)
                httpURLConnection.disconnect();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String response) {
        if (!error) {
            updateContacts(response);
        }
        ContactTab.OnRefreshContactsFinish();
    }

    private JSONObject processPhoneNumbers(){

        String contactId, completeNumber;

        ArrayList<String> contactsAlreadyChecked = new ArrayList<>();

        JSONObject jsonParams = new JSONObject();
        JSONArray contactsJson = new JSONArray();

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber numberProto = null;
        Cursor phones = null;

        ContentResolver cr = mContext.getContentResolver();
        Cursor users = cr.query(DataProvider.CONTENT_URI_PROFILE, new String[]{DataProvider.COL_PHONE_NUMBER}, null, null, null);
        Cursor contacts = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        //Add contacts to contactsAlreadyChecked that are already users
        if(users!= null){
            while (users.moveToNext()){
                contactsAlreadyChecked.add(users.getString(users.getColumnIndex(DataProvider.COL_PHONE_NUMBER)));
            }
        }
        if (contacts != null && contacts.getCount() > 0) {
            while (contacts.moveToNext()) {
                contactId = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));
                phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

                while (phones != null && phones.moveToNext()) {
                    String unformattedNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                    String countryID = manager.getSimCountryIso().toUpperCase().trim();
                    try {
                        numberProto = phoneUtil.parse(unformattedNumber, countryID);
                    } catch (NumberParseException e) {
                        e.printStackTrace();
                    }

                    if (phoneUtil.isValidNumber(numberProto)) {
                        completeNumber = Long.toString(numberProto.getCountryCode()) + Long.toString(numberProto.getNationalNumber());

                        if (!contactsAlreadyChecked.contains(completeNumber)) {
                            contactsAlreadyChecked.add(completeNumber);
                            JSONObject c = new JSONObject();
                            try {
                                c.put(ServerSharedConstants.ID, contactId);
                                c.put(ServerSharedConstants.PHONE_NUMBER, completeNumber);
                                contactsJson.put(c);
                            } catch (JSONException e) {
                                Log.d(TAG, e.toString());
                            }
                        }
                    }
                }
            }
        }

        if (phones != null && !phones.isClosed()) {
            phones.close();
        }

        if (contacts != null && !contacts.isClosed()) {
            contacts.close();
        }
        try {
            jsonParams.put(ServerSharedConstants.CONTACTS, contactsJson);
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }

        return jsonParams;
    }

    private void updateContacts(String response) {
        ArrayList<ContactItem> newContacts = JSONProcess(response);

        for (ContactItem contact : newContacts) {
            contact.saveContact(mContext);
        }
    }
}

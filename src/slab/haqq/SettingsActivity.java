package slab.haqq;

import java.io.File;
import java.util.List;

import slab.haqq.lib.GlobalController;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {
	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */
	private static final boolean ALWAYS_SIMPLE_PREFS = false;
	public static Context context;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setTheme(R.style.Theme_Haqq_Light);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		context = this;
		//activity = this;
		setupSimplePreferencesScreen();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}

		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Add 'general' preferences.
		// addPreferencesFromResource(R.xml.pref_general);
		addPreferencesFromResource(R.xml.pref_haqq);

		// Add 'notifications' preferences, and a corresponding header.
		/*
		 * PreferenceCategory fakeHeader = new PreferenceCategory(this);
		 * fakeHeader.setTitle(R.string.pref_header_notifications);
		 * getPreferenceScreen().addPreference(fakeHeader);
		 * addPreferencesFromResource(R.xml.pref_notification);
		 */

		// Add 'data and sync' preferences, and a corresponding header.
		/*
		 * fakeHeader = new PreferenceCategory(this);
		 * fakeHeader.setTitle(R.string.pref_header_data_sync);
		 * getPreferenceScreen().addPreference(fakeHeader);
		 * addPreferencesFromResource(R.xml.pref_data_sync);
		 */

		// Bind the summaries of EditText/List/Dialog/Ringtone preferences to
		// their values. When their values change, their summaries are updated
		// to reflect the new value, per the Android Design guidelines.
		// bindPreferenceSummaryToValue(findPreference("example_text"));
		// bindPreferenceSummaryToValue(findPreference("example_list"));
		// bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
		// bindPreferenceSummaryToValue(findPreference("sync_frequency"));
		bindPreferenceSummaryToValue(findPreference("arabictext_pref_list"));
		bindPreferenceSummaryToValue(findPreference("scoring_pref_list"));
		bindPreferenceSummaryToValue(findPreference("prefix"));
		bindPreferenceSummaryToValue(findPreference("displayNameHaqq"));
		bindPreferenceSummaryToValue(findPreference("accountdata"));
		findPreference("accountdata").setOnPreferenceClickListener(
				sClickPreferenceListener);
		findPreference("register").setOnPreferenceClickListener(
				sClickPreferenceListener);
		findPreference("sendresult").setOnPreferenceClickListener(
				sClickPreferenceListener);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
	 * doesn't have newer APIs like {@link PreferenceFragment}, or the device
	 * doesn't have an extra-large screen. In these cases, a single-pane
	 * "simplified" settings UI should be shown.
	 */
	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(context);
	}

	/** {@inheritDoc} */
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			loadHeadersFromResource(R.xml.pref_headers, target);
		}
	}

	public static Preference.OnPreferenceClickListener sClickPreferenceListener = new Preference.OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			if (preference.getKey().equalsIgnoreCase("register")) {
				Log.d("register", "Register Preference");
				Toast.makeText(context, "Disabled. Please contact the developer", Toast.LENGTH_LONG).show();
				return false;
				/*final AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				View vi = (View) layoutInflater.inflate(
						R.layout.custdialog_register, null);
				builder.setView(vi);
				builder.setTitle("Register");
				final EditText fullname, email, pass;
				fullname = (EditText) vi.findViewById(R.id.fullnameEdit);
				email = (EditText) vi.findViewById(R.id.emailedit);
				pass = (EditText) vi.findViewById(R.id.passedit);
				builder.setCancelable(false)
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										arg0.dismiss();
									}
								})
						.setPositiveButton("Register",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										HashMap<String, Object> result = SPHINXRest
												.Register(email.getText()
														.toString(), pass
														.getText().toString(),
														fullname.getText()
																.toString());
										if (Integer.parseInt(String
												.valueOf(result
														.get("statusCode"))) == 200) {
											Toast.makeText(context,
													"Registration successful.",
													Toast.LENGTH_LONG).show();
											;
											SharedPreferences sharePref = PreferenceManager
													.getDefaultSharedPreferences(context);
											sharePref.edit().putString(
													"emailApi",
													email.getText().toString());
											sharePref.edit().putString(
													"passApi",
													pass.getText().toString());
											sharePref.edit().commit();
										} else {
											Toast.makeText(
													context,
													"Registration unsuccessful.",
													Toast.LENGTH_LONG).show();
											;
										}
									}
								});
				AlertDialog dialog = builder.create();
				dialog.show();*/
			} else if (preference.getKey().equalsIgnoreCase("accountdata")) {
				Log.d("accountdata", "account Preference");
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				View vi = (View) layoutInflater.inflate(
						R.layout.custdialog_login, null);
				
				builder.setView(vi);
				builder.setTitle("Account Data");
				
				final EditText email, pass;
				email = (EditText) vi.findViewById(R.id.emailedit);
				pass = (EditText) vi.findViewById(R.id.passedit);
				SharedPreferences sharePref = PreferenceManager
						.getDefaultSharedPreferences(context);
				Log.d("preference", sharePref.getString("emailApi", ""));
				email.setText(sharePref.getString("emailApi", ""));
				pass.setText(sharePref.getString("passApi", ""));
				
				builder.setCancelable(false)
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										arg0.dismiss();
									}
								})
						.setPositiveButton("Save",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										SharedPreferences.Editor sharePref = PreferenceManager
												.getDefaultSharedPreferences(context).edit();
										sharePref.putString("emailApi",
												email.getText().toString());
										sharePref.putString("passApi",
												pass.getText().toString());
										sharePref.putString("accountdata", String.valueOf(System.currentTimeMillis()));
										Log.d("email", email.getText().toString());
										sharePref.commit();	
										
									}
								});
				AlertDialog dialog = builder.create();
				dialog.show();
			} else if(preference.getKey().equalsIgnoreCase("sendresult")){
				Uri uri;
				File file = GlobalController.resultProvider.getResultFile();
				uri = Uri.fromFile(file);
				SharedPreferences sharePref = PreferenceManager
						.getDefaultSharedPreferences(context);
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"haqqtesting@gmail.com"});
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Result "+sharePref.getString("displayNameHaqq", "HAQQ"));
				emailIntent.setType("message/rfc822");
				emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
				context.startActivity(Intent.createChooser(emailIntent, "Choose an email client :"));
			}else {
				return false;
			}
			return false;
		}
	};

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();

			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;

				// GlobalController.setPrefVal(preference.getKey(),
				// ((ListPreference) preference).getValue());

				int index = listPreference.findIndexOfValue(stringValue);
				Log.v("prefVal", String.valueOf(index));
				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			} else if (preference.getKey().equalsIgnoreCase("register")) {
				Log.d("register", "Register Preference");
			} else if (preference.getKey().equalsIgnoreCase("accountdata")) {
				Log.d("accountdata", "account Preference");
				SharedPreferences sharePref = PreferenceManager
						.getDefaultSharedPreferences(context);
				preference.setSummary(sharePref.getString("emailApi", "No saved account"));
			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);

			}
			return true;
		}
	};

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}

	/**
	 * This fragment shows general preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_haqq);

			// Bind the summaries of EditText/List/Dialog/Ringtone preferences
			// to their values. When their values change, their summaries are
			// updated to reflect the new value, per the Android Design
			// guidelines.
			bindPreferenceSummaryToValue(findPreference("arabictext_pref_list"));
			bindPreferenceSummaryToValue(findPreference("prefix"));
			bindPreferenceSummaryToValue(findPreference("displayNameHaqq"));
			bindPreferenceSummaryToValue(findPreference("accountdata"));
			findPreference("accountdata").setOnPreferenceClickListener(
					sClickPreferenceListener);
			findPreference("register").setOnPreferenceClickListener(
					sClickPreferenceListener);
		}
	}

}

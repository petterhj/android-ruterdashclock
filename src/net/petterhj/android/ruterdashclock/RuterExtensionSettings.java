package net.petterhj.android.ruterdashclock;

import android.os.Bundle;

public class RuterExtensionSettings extends BaseSettingsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setIcon(R.drawable.ic_extension_ruter);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void setupSimplePreferencesScreen() {
        // In the simplified UI, fragments are not used at all and we instead
        // use the older PreferenceActivity APIs.
        
        // Add 'general' preferences.
        addPreferencesFromResource(R.layout.extension_settings);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences to
        // their values. When their values change, their summaries are updated
        // to reflect the new value, per the Android Design guidelines.
        bindPreferenceSummaryToValue(findPreference(RuterExtension.PREF_USERNAME));
        bindPreferenceSummaryToValue(findPreference(RuterExtension.PREF_PASSWORD));
    }
}
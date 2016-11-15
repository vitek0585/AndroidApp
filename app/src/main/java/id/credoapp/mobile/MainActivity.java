package id.credoapp.mobile;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;

import java.util.Collection;

import Dtos.Element;
import Extractors.Activity.CallExtractor;
import Extractors.Activity.SmsExtractor;
import Extractors.ApplicationExtractor;
import Extractors.Browsers.AndroidBrowserHistoryExtractor;
import Extractors.Browsers.ChromeBrowserHistoryExtractor;
import Extractors.Calendars.CalendarAttendeesExtractor;
import Extractors.Calendars.CalendarEventsExtractor;
import Extractors.Calendars.CalendarExtractor;
import Extractors.Calendars.CalendarRemindersExtractor;
import Extractors.Contacts.ContactExtractor;
import Extractors.Contacts.ContactGroupExtractor;
import Extractors.Files.AudioExternalExtractor;
import Extractors.Files.AudioInternalExtractor;
import Extractors.Files.DownloadDirectoryExtractor;
import Extractors.Files.ImagesExternalExtractor;
import Extractors.Files.ImagesInternalExtractor;
import Extractors.Files.VideoExternalExtractor;
import Extractors.Files.VideoInternalExtractor;
import Extractors.General.GeneralDataExtractor;
import Extractors.General.HardwareExtractor;
import Extractors.General.RegisteredAccountsExtractor;
import Extractors.GmailExtractor;
import Infrastructure.DataExtractorManager;
import Infrastructure.IDataExtractorManager;
import layout.CollectFragment;
import layout.FinallyFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void Collect(View view) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.content, new FinallyFragment());
        tx.addToBackStack(null);
        tx.commit();

        IDataExtractorManager dataExtractorManager = new DataExtractorManager();
        dataExtractorManager.AddExtractor(new CallExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new SmsExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new AndroidBrowserHistoryExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new ChromeBrowserHistoryExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new CalendarAttendeesExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new CalendarEventsExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new CalendarExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new CalendarRemindersExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new ContactExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new ContactGroupExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new AudioExternalExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new AudioInternalExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new DownloadDirectoryExtractor());
        dataExtractorManager.AddExtractor(new ImagesInternalExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new ImagesExternalExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new VideoExternalExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new VideoInternalExtractor(getContentResolver()));

        Context applicationContext = getApplicationContext();
        dataExtractorManager.AddExtractor(new GeneralDataExtractor((TelephonyManager) getSystemService(TELEPHONY_SERVICE),
                Resources.getSystem().getConfiguration()));
        dataExtractorManager.AddExtractor(new HardwareExtractor(getWindowManager(),
                (ActivityManager) getSystemService(ACTIVITY_SERVICE),
                applicationContext));

        dataExtractorManager.AddExtractor(new RegisteredAccountsExtractor(applicationContext));
        dataExtractorManager.AddExtractor(new ApplicationExtractor(getPackageManager()));
        dataExtractorManager.AddExtractor(new GmailExtractor(getContentResolver(), (AccountManager) applicationContext.getSystemService(ACCOUNT_SERVICE)));

        Collection<Element> elements = dataExtractorManager.CollectData();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void goToCollect(View view) {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.content, new CollectFragment());
        tx.addToBackStack(null);
        tx.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            fragmentManager.popBackStack();
            if (fragmentManager.getBackStackEntryCount() == 1)
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
}

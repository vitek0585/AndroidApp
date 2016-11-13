package id.credoapp.mobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Collection;

import Dtos.Element;
import Extractors.Activity.CallExtractor;
import Extractors.Activity.SmsExtractor;
import Extractors.Browsers.AndroidBrowserHistoryExtractor;
import Extractors.Browsers.ChromeBrowserHistoryExtractor;
import Infrastructure.DataExtractorManager;
import Infrastructure.IDataExtractorManager;
import TestSimple.TestFunc;

public class CollectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon);
            TestFunc.getTask();
        }

    }

    public void Collect(View view){

        IDataExtractorManager dataExtractorManager = new DataExtractorManager();
        dataExtractorManager.AddExtractor(new CallExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new SmsExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new AndroidBrowserHistoryExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new ChromeBrowserHistoryExtractor(getContentResolver()));
        Collection<Element> elements = dataExtractorManager.CollectData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
        }

        return (super.onOptionsItemSelected(menuItem));
    }

}
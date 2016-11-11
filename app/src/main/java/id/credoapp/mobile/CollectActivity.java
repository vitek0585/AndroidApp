package id.credoapp.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.Collection;

import Dtos.Element;
import Extractors.Activity.CallExtractor;
import Extractors.Activity.SmsExtractor;
import Extractors.Browsers.AndroidBrowserHistoryExtractor;
import Extractors.Browsers.ChromeBrowserHistoryExtractor;
import Infrastructure.DataExtractorManager;
import Infrastructure.IDataExtractorManager;

public class CollectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
    }

    public void Collect(View view){

        IDataExtractorManager dataExtractorManager = new DataExtractorManager();
        dataExtractorManager.AddExtractor(new CallExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new SmsExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new AndroidBrowserHistoryExtractor(getContentResolver()));
        dataExtractorManager.AddExtractor(new ChromeBrowserHistoryExtractor(getContentResolver()));
        Collection<Element> elements = dataExtractorManager.CollectData();
    }
}
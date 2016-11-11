package Extractors.Browsers;

import android.content.ContentResolver;
import android.net.Uri;

public class ChromeBrowserHistoryExtractor extends BrowserHistoryExtractorBase {

    public ChromeBrowserHistoryExtractor(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected String GetBrowserType() {
        return "Chrome";
    }

    @Override
    protected Uri GetDataUri() {
        return Uri.parse("content://com.android.chrome.browser/bookmarks");
    }
}

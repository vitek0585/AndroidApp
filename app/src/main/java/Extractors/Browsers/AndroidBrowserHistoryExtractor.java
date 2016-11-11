package Extractors.Browsers;

import android.content.ContentResolver;
import android.net.Uri;

public class AndroidBrowserHistoryExtractor extends BrowserHistoryExtractorBase {

    @Override
    protected String GetBrowserType() {
        return "Android";
    }

    @Override
    protected Uri GetDataUri() {
        return BookmarkColumns.BOOKMARKS_URI;
    }

    public AndroidBrowserHistoryExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }
}

package Extractors.Browsers;

import java.util.ArrayList;
import java.util.List;
import Extractors.Common.ContentExtractorBase;


public abstract class BrowserHistoryExtractorBase extends ContentExtractorBase {

    protected abstract String GetBrowserType();

    @Override
    protected String GetDataSourceNameValue(){
        return String.format("Browser %s", GetBrowserType());
    }

    @Override
    protected String GetMainColumnName(){
        return BookmarkColumns.TITLE;
    }

    @Override
    protected List<String> GetColumnNames(){

        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(BookmarkColumns.URL);
        columns.add(BookmarkColumns.DATE);
        columns.add(BookmarkColumns.VISITS);

        return columns;
    }

    public BrowserHistoryExtractorBase(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }
}

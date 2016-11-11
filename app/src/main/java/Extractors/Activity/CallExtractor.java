package Extractors.Activity;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.CallLog;
import java.util.ArrayList;
import java.util.List;

public class CallExtractor extends ActivityExtractorBase {

    @Override
    public String ContactNumberField() {
        return CallLog.Calls.NUMBER;
    }

    @Override
    protected List<String> GetColumnNames() {
        ArrayList<String> columns = new ArrayList<String>();
        columns.add(this.GetMainColumnName());
        columns.add(CallLog.Calls.TYPE);
        columns.add(CallLog.Calls.DURATION);
        columns.add(CallLog.Calls.NUMBER);
        columns.add(CallLog.Calls.IS_READ);
        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return CallLog.Calls.DATE;
    }

    @Override
    protected Uri GetDataUri() {
        return CallLog.Calls.CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "Call";
    }

    public CallExtractor(ContentResolver resolver) {
        super(resolver);
    }
}

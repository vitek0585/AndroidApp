package Extractors.Activity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

public class SmsExtractor extends ActivityExtractorBase {

    @Override
    public String ContactNumberField() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Telephony.Sms.ADDRESS;
        }

        return "address";
    }


    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            columns.add(GetMainColumnName());
            columns.add(Telephony.Sms.TYPE);
            columns.add(Telephony.Sms.SEEN);
            columns.add(Telephony.Sms.READ);
            columns.add(Telephony.Sms.SUBJECT);
            columns.add(Telephony.Sms.STATUS);
            columns.add(Telephony.Sms.ADDRESS);
        }else {
            columns.add(GetMainColumnName());
            columns.add("type");
            columns.add("seen");
            columns.add("read");
            columns.add("subject");
            columns.add("status");
            columns.add("address");
        }

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Telephony.Sms.DATE_SENT;
        }

        return "date_sent";
    }

    @Override
    protected Uri GetDataUri() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            return Telephony.Sms.CONTENT_URI;
        }

        return Uri.parse("content://sms");
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "Sms";
    }

    public SmsExtractor(ContentResolver resolver) {
        super(resolver);
    }
}

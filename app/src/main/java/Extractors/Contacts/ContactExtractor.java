package Extractors.Contacts;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import Extensions.StringExtension;
import Extractors.Common.ContentExtractorBase;

public class ContactExtractor extends ContentExtractorBase {

    private List<String> GetUnNormalizedColumnNames()
    {
        List<String> columns = new ArrayList<String>();
        columns.add(ContactsContract.Contacts.PHOTO_ID);
        columns.add(ContactsContract.Contacts.CONTACT_STATUS);
        return columns;
    }

    public ContactExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(ContactsContract.Contacts.CUSTOM_RINGTONE);
        columns.add(ContactsContract.Contacts.LAST_TIME_CONTACTED);
        columns.add(ContactsContract.Contacts.SEND_TO_VOICEMAIL);
        columns.add(ContactsContract.Contacts.STARRED);
        columns.add(ContactsContract.Contacts.TIMES_CONTACTED);
        columns.add(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        columns.add(ContactsContract.Contacts.IN_VISIBLE_GROUP);
        columns.add(ContactsContract.Contacts.IS_USER_PROFILE);
        columns.add(ContactsContract.Contacts.PHOTO_ID);
        columns.add(ContactsContract.Contacts.CONTACT_STATUS);
        columns.add(ContactsContract.Contacts.CONTACT_STATUS_RES_PACKAGE);
        columns.add(ContactsContract.Contacts.CONTACT_STATUS_TIMESTAMP);

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return ContactsContract.Contacts._ID;
    }

    @Override
    protected Uri GetDataUri() {
        return ContactsContract.Contacts.CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "Contact";
    }

    @Override
    protected String NormalizeValue(String column, String value)
    {
        if (GetUnNormalizedColumnNames().contains(column))
        {
            if(StringExtension.IsNullOrWhitespace(value)){
                return Boolean.FALSE.toString();
            }else{
                return Boolean.TRUE.toString();
            }
        }

        return value;
    }
}

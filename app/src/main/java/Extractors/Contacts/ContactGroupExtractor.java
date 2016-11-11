package Extractors.Contacts;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import Extensions.StringExtension;
import Extractors.Common.ContentExtractorBase;

public class ContactGroupExtractor extends ContentExtractorBase {

    public ContactGroupExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(ContactsContract.Groups.AUTO_ADD);
        columns.add(ContactsContract.Groups.DELETED);
        columns.add(ContactsContract.Groups.FAVORITES);
        columns.add(ContactsContract.Groups.GROUP_IS_READ_ONLY);
        columns.add(ContactsContract.Groups.GROUP_VISIBLE);
        columns.add(ContactsContract.Groups.NOTES);
        columns.add(ContactsContract.Groups.SHOULD_SYNC);
        columns.add(ContactsContract.Groups.ACCOUNT_TYPE);

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return ContactsContract.Groups._ID;
    }

    @Override
    protected Uri GetDataUri() {
        return ContactsContract.Groups.CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "ContactGroup";
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

    private List<String> GetUnNormalizedColumnNames()
    {
        List<String> columns = new ArrayList<String>();
        columns.add(ContactsContract.Groups.NOTES);
        return columns;
    }

}

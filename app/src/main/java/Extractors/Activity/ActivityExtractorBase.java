package Extractors.Activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Extractors.Common.ContentExtractorBase;


public abstract class ActivityExtractorBase extends ContentExtractorBase{

    private final Uri _contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private String _conditions;
    private final String _contactIdField = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

    public abstract String ContactNumberField();

    public ActivityExtractorBase(ContentResolver resolver) {
        super(resolver);
        List<String> conditions = new ArrayList<String>();
        List<String> datas = Arrays.asList(
                ContactsContract.CommonDataKinds.Phone.DATA1,
                ContactsContract.CommonDataKinds.Phone.DATA2,
                ContactsContract.CommonDataKinds.Phone.DATA3,
                ContactsContract.CommonDataKinds.Phone.DATA4,
                ContactsContract.CommonDataKinds.Phone.DATA5,
                ContactsContract.CommonDataKinds.Phone.DATA6,
                ContactsContract.CommonDataKinds.Phone.DATA7,
                ContactsContract.CommonDataKinds.Phone.DATA8,
                ContactsContract.CommonDataKinds.Phone.DATA9,
                ContactsContract.CommonDataKinds.Phone.DATA10,
                ContactsContract.CommonDataKinds.Phone.DATA11,
                ContactsContract.CommonDataKinds.Phone.DATA12,
                ContactsContract.CommonDataKinds.Phone.DATA13,
                ContactsContract.CommonDataKinds.Phone.DATA14,
                ContactsContract.CommonDataKinds.Phone.DATA15
        );

        for (String data : datas) {
            conditions.add(data + "='%1$s'");
        }

        _conditions = TextUtils.join(" OR ",conditions);
    }

    @Override
    protected String NormalizeValue(String column, String value)
    {
        if (ContactNumberField()==column)
        {
            Cursor queryContacts = ContentResolver.query(_contactsUri, new String[] { _contactIdField }, String.format(_conditions, value), null, null);
            queryContacts.moveToFirst();
            if (queryContacts.getCount() > 0)
            {
                return queryContacts.getString(queryContacts.getColumnIndex(_contactIdField));
            }

            return null;
        }

        return super.NormalizeValue(column, value);
    }

    @Override
    protected String NormalizeColumn(String column)
    {
        if (ContactNumberField()== column)
        {
            return ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        }

        return column;
    }
}


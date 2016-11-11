package Extractors.Calendars;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

import Extractors.Common.ContentExtractorBase;

public class CalendarRemindersExtractor extends ContentExtractorBase {

    public CalendarRemindersExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(CalendarContract.Reminders.METHOD);
        columns.add(CalendarContract.Reminders.MINUTES);

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return CalendarContract.Reminders.EVENT_ID;
    }

    @Override
    protected Uri GetDataUri() {
        return CalendarContract.Reminders.CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "CalendarReminders";
    }
}

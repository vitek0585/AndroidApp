package Extractors.Calendars;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

import Extractors.Common.ContentExtractorBase;

public class CalendarExtractor extends ContentExtractorBase {
    public CalendarExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> colmns = new ArrayList<String>();
        colmns.add(GetMainColumnName());
        colmns.add(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES);
        colmns.add(CalendarContract.Calendars.ALLOWED_AVAILABILITY);
        colmns.add(CalendarContract.Calendars.ALLOWED_REMINDERS);
        colmns.add(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL);
        colmns.add(CalendarContract.Calendars.CALENDAR_COLOR);
        colmns.add(CalendarContract.Calendars.CALENDAR_TIME_ZONE);
        colmns.add(CalendarContract.Calendars.VISIBLE);
        colmns.add(CalendarContract.Calendars.ACCOUNT_TYPE);

        return colmns;
    }

    @Override
    protected String GetMainColumnName() {
        return CalendarContract.Instances._ID;
    }

    @Override
    protected Uri GetDataUri() {
        return CalendarContract.Calendars.CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "Calendars";
    }
}

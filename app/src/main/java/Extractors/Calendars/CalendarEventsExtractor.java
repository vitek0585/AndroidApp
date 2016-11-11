package Extractors.Calendars;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

import Extractors.Common.ContentExtractorBase;

public class CalendarEventsExtractor extends ContentExtractorBase {
    public CalendarEventsExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(CalendarContract.Events.ACCESS_LEVEL);
        columns.add(CalendarContract.Events.ALL_DAY);
        columns.add(CalendarContract.Events.AVAILABILITY);
        columns.add(CalendarContract.Events.CALENDAR_ID);
        columns.add(CalendarContract.Events.DTSTART);
        columns.add(CalendarContract.Events.DTEND);
        columns.add(CalendarContract.Events.DURATION);
        columns.add(CalendarContract.Events.EVENT_COLOR);
        columns.add(CalendarContract.Events.EVENT_TIMEZONE);
        columns.add(CalendarContract.Events.EXDATE);
        columns.add(CalendarContract.Events.EXRULE);
        columns.add(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS);
        columns.add(CalendarContract.Events.GUESTS_CAN_MODIFY);
        columns.add(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS);
        columns.add(CalendarContract.Events.HAS_ALARM);
        columns.add(CalendarContract.Events.HAS_ATTENDEE_DATA);
        columns.add(CalendarContract.Events.HAS_EXTENDED_PROPERTIES);
        columns.add(CalendarContract.Events.LAST_DATE);
        columns.add(CalendarContract.Events.RDATE);
        columns.add(CalendarContract.Events.RRULE);
        columns.add(CalendarContract.Events.STATUS);

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return CalendarContract.Instances._ID;
    }

    @Override
    protected Uri GetDataUri() {
        return CalendarContract.Events.CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "CalendarEvents";
    }
}

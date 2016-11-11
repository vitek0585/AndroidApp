package Extractors.Calendars;

import android.content.ContentResolver;
import android.icu.util.Calendar;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import android.provider.CalendarContract;
import Extractors.Common.ContentExtractorBase;


public class CalendarAttendeesExtractor extends ContentExtractorBase {
    public CalendarAttendeesExtractor(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP);
        columns.add(CalendarContract.Attendees.ATTENDEE_TYPE);
        columns.add(CalendarContract.Attendees.ATTENDEE_STATUS);

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return CalendarContract.Attendees.EVENT_ID;
    }

    @Override
    protected Uri GetDataUri() {
        return CalendarContract.Attendees.CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "CalendarAttendees";
    }
}

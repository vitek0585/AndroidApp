package Extractors.Files.Common;


import android.content.ContentResolver;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import Extensions.StringExtension;
import Extractors.Common.ContentExtractorBase;

public abstract class VideoExtractorBase extends ContentExtractorBase {
    public VideoExtractorBase(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(MediaStore.Video.Media.DATE_MODIFIED);
        columns.add(MediaStore.Video.Media.DATE_TAKEN);
        columns.add(MediaStore.Video.Media.DESCRIPTION);
        columns.add(MediaStore.Video.Media.DURATION);
        columns.add(MediaStore.Video.Media.IS_PRIVATE);
        columns.add(MediaStore.Video.Media.LANGUAGE);
        columns.add(MediaStore.Video.Media.MIME_TYPE);
        columns.add(MediaStore.Video.Media.RESOLUTION);
        columns.add(MediaStore.Video.Media.SIZE);

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return MediaStore.Video.Media.DATE_ADDED;
    }

    private List<String> GetUnNormalizedColumnNames()
    {
        List<String> columns = new ArrayList<String>();
        columns.add(MediaStore.Video.Media.TAGS);
        return columns;
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

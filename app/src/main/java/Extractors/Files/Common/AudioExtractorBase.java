package Extractors.Files.Common;

import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

import Extractors.Common.ContentExtractorBase;

public abstract class AudioExtractorBase extends ContentExtractorBase {
    public AudioExtractorBase(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected String GetMainColumnName() {
        return MediaStore.Audio.Media.DATE_ADDED;
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(MediaStore.Audio.Media.DATE_MODIFIED);
        columns.add(MediaStore.Audio.Media.DURATION);
        columns.add(MediaStore.Audio.Media.MIME_TYPE);
        columns.add(MediaStore.Audio.Media.IS_MUSIC);
        columns.add(MediaStore.Audio.Media.YEAR);

        return columns;
    }
}

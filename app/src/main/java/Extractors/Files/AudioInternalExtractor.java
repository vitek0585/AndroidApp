package Extractors.Files;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;

import Extractors.Files.Common.AudioExtractorBase;

public class AudioInternalExtractor extends AudioExtractorBase {
    public AudioInternalExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected Uri GetDataUri() {
        return MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "AudioInternal";
    }
}

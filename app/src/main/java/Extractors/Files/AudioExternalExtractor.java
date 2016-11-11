package Extractors.Files;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;

import Extractors.Files.Common.AudioExtractorBase;

public class AudioExternalExtractor extends AudioExtractorBase {
    public AudioExternalExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected Uri GetDataUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "AudioExternal";
    }
}

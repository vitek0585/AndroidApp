package Extractors.Files;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;

import Extractors.Files.Common.VideoExtractorBase;

public class VideoInternalExtractor extends VideoExtractorBase {
    public VideoInternalExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected Uri GetDataUri() {
        return  MediaStore.Video.Media.INTERNAL_CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "VideoInternal";
    }
}

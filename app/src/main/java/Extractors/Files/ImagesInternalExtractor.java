package Extractors.Files;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;

import Extractors.Files.Common.ImagesExtractorBase;


public class ImagesInternalExtractor extends ImagesExtractorBase {
    public ImagesInternalExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected Uri GetDataUri() {
        return MediaStore.Images.Media.INTERNAL_CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "ImagesInternal";
    }
}

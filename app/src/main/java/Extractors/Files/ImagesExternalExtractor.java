package Extractors.Files;

import android.net.Uri;
import android.provider.MediaStore;

import Extractors.Files.Common.ImagesExtractorBase;

public class ImagesExternalExtractor extends ImagesExtractorBase {
    public ImagesExternalExtractor(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected Uri GetDataUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "ImagesExternal";
    }
}

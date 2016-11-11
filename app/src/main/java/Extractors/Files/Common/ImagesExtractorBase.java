package Extractors.Files.Common;

import android.content.ContentResolver;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import Extractors.Common.ContentExtractorBase;

public abstract class ImagesExtractorBase extends ContentExtractorBase{
    public ImagesExtractorBase(android.content.ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    protected String GetMainColumnName() {
        return MediaStore.Images.Media.DATE_TAKEN;
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add(MediaStore.Images.Media.DATE_ADDED);
        columns.add(MediaStore.Images.Media.DATE_MODIFIED);
        columns.add(MediaStore.Images.Media.HEIGHT);
        columns.add(MediaStore.Images.Media.WIDTH);
        columns.add(MediaStore.Images.Media.LATITUDE);
        columns.add(MediaStore.Images.Media.LONGITUDE);
        columns.add(MediaStore.Images.Media.MIME_TYPE);
        columns.add(MediaStore.Images.Media.SIZE);
        return columns;
    }
}

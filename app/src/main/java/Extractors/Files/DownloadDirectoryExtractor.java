package Extractors.Files;

import android.os.Environment;

import Extractors.Files.Common.DirectoryExtractorBase;

public class DownloadDirectoryExtractor extends DirectoryExtractorBase {
    @Override
    protected String GetDirectoryName() {
        return Environment.DIRECTORY_DOWNLOADS;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "DownloadFiles";
    }
}

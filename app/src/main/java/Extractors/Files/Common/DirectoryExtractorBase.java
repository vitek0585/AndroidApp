package Extractors.Files.Common;

import android.os.Environment;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import Dtos.Element;
import Extractors.Common.ExtractorBase;

public abstract class DirectoryExtractorBase extends ExtractorBase {

    protected abstract String GetDirectoryName();

    private final static String FileType = "FileType";

    @Override
    public Collection<Element> ExtractInternal() {
        File directory = Environment.getExternalStoragePublicDirectory(GetDirectoryName());
        return ObtainFiles(directory);
    }

    private Collection<Element> ObtainFiles(File currentDirectory)
    {
        Collection<Element> elements = new ArrayList<Element>();

        for (File file : currentDirectory.listFiles())
        {
            if (file.isDirectory())
            {
                elements.addAll(ObtainFiles(file));
            }
            else
            {
                Element element = new Element(FileType, MimeTypeMap.getFileExtensionFromUrl(file.getName()));
                element.AddChild("Length", Long.toString(file.length()));
                element.AddChild("LastModified", Long.toString(file.lastModified()));
                elements.add(element);
            }
        }

        return elements;
    }
}

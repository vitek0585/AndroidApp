package Extractors.Common;

import java.util.Collection;

import Dtos.Element;

public abstract class ExtractorBase implements IDataExtractor {

    private final String DataSourceNameKey = "DataSourceType";

    private final String Error = "Error";

    private final String Trace = "Trace";

    protected abstract String GetDataSourceNameValue();

    public Element Extract()
    {
        Element elementRoot = new Element(DataSourceNameKey, GetDataSourceNameValue());
        elementRoot.AddChilds(ExtractInternal());

        return elementRoot;
    }

    public abstract Collection<Element> ExtractInternal();
}

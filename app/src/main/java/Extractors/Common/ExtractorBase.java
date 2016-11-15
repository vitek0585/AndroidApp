package Extractors.Common;

import java.util.Collection;

import Dtos.Element;

public abstract class ExtractorBase implements IDataExtractor {

    private final String DataSourceNameKey = "DataSourceType";

    protected abstract String GetDataSourceNameValue();

    public Element Extract()
    {
        Element elementRoot = new Element(DataSourceNameKey, GetDataSourceNameValue());
        Collection<Element> elements = ExtractInternal();

        if(elements!=null){
            elementRoot.AddChilds(elements);
        }

        return elementRoot;
    }

    public abstract Collection<Element> ExtractInternal();
}

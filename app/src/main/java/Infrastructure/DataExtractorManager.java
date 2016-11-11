package Infrastructure;

import java.util.ArrayList;
import java.util.Collection;

import Dtos.Element;
import Extractors.Common.IDataExtractor;

public class DataExtractorManager implements IDataExtractorManager{

    private Collection<IDataExtractor> _extractors;

    public DataExtractorManager()
    {
        _extractors = new ArrayList<IDataExtractor>();
    }

    public Collection<Element> CollectData()
    {
        Collection<Element> elements = new ArrayList<Element>();
        for (IDataExtractor extractor:_extractors){
            elements.add(extractor.Extract());
        }
        return elements;
    }

    public void AddExtractor(IDataExtractor extractor)
    {
        _extractors.add(extractor);
    }
}

package Infrastructure;

import java.util.Collection;

import Dtos.Element;
import Extractors.Common.IDataExtractor;

public interface IDataExtractorManager {
    Collection<Element> CollectData();
    void AddExtractor(IDataExtractor extractor);
}

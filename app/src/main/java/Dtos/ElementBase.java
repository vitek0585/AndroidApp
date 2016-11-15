package Dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Extensions.Delegate;

public abstract class ElementBase {

    private Collection<Element> _childElements;

    public Collection<Element> get_childElement() {
        return _childElements;
    }

    public void set_childElement(List<Element> _childElements) {
        this._childElements = _childElements;
    }

    public void AddChild(Element element)
    {
        AddChildInternal(element);
    }

    public void AddChilds(Collection<Element> elements)
    {
        if (_childElements == null)
            _childElements = new ArrayList<Element>();

        _childElements.addAll(elements);
    }

    public void AddChild(String name, String value)
    {
        AddChildInternal(new Element(name, value));
    }

    public void AddChild(String name, Delegate value)
    {
       try {
           AddChildInternal(new Element(name,value.GetResult()));
       }catch (Exception ex){
           AddChildInternal(new Element(name,ex.getMessage()));
       }
    }

    public void AddError(String name, Exception ex)
    {
        AddChildInternal(new Element(name, String.format("EXTRACTING ERROR : %s",ex.getMessage())));
    }

    private void AddChildInternal(Element element)
    {
        if (_childElements == null)
            _childElements = new ArrayList<Element>();

        _childElements.add(element);
    }
}

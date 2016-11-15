package Dtos;

public class Element extends ElementBase {
    private String _name;

    private String _value;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public Element(String name, String value){
        _name=name;
        _value=value;
    }

    public Element(String name, Exception ex){
        _name=name;
        _value = ex.getMessage();
    }

    public Element(String name){
        _name=name;
    }
}

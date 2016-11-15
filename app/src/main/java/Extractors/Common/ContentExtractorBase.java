package Extractors.Common;

import android.database.Cursor;
import android.net.Uri;
import android.content.ContentResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Dtos.Element;
import Extensions.Delegate;


public abstract class ContentExtractorBase extends ExtractorBase{

    protected ContentResolver ContentResolver;

    protected abstract List<String> GetColumnNames();

    protected abstract String GetMainColumnName();

    protected abstract Uri GetDataUri();

    protected String Conditions;

    public ContentExtractorBase(ContentResolver contentResolver) {
        ContentResolver = contentResolver;
    }

    @Override
    public Collection<Element> ExtractInternal()
    {
        return Extract(GetDataUri());
    }

    protected Collection<Element> Extract(Uri dataUri)
    {
        Collection<String> columns = GetColumnNames();
        columns.remove(GetMainColumnName());
        Collection<Element> elements = new ArrayList<Element>();

        final Cursor query = ContentResolver.query(dataUri, GetColumnNames().toArray(new String[0]), Conditions, null, null);

        if(query==null){
            return null;
        }

        query.moveToFirst();
        if (query.getCount() > 0)
        {
            do
            {
                Element item = new Element(GetMainColumnName(), query.getString(query.getColumnIndex(GetMainColumnName())));

                for (final String column : columns)
                {
                    try{

                        item.AddChild(NormalizeColumn(column), new Delegate() {
                            @Override
                            public String GetResult() {
                                String value = query.getString(query.getColumnIndex(column));
                                return NormalizeValue(column, value);
                            }
                        });
                    }catch (Exception ex){
                        item.AddError(NormalizeColumn(column),ex);
                    }
                }

                elements.add(item);

            } while (query.moveToNext());
        }

        return elements;
    }

    protected String NormalizeValue(String column, String value)
    {
        return value;
    }

    protected String NormalizeColumn(String column)
    {
        return column;
    }
}

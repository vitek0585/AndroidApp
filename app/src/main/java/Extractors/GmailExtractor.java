package Extractors;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Dtos.Element;
import Extractors.Common.ContentExtractorBase;

public class GmailExtractor extends ContentExtractorBase {

    private final String GmailUri = "content://com.google.android.gm/%s/labels";
    private final String GmailType = "com.google";
    private AccountManager _accountManager;

    public GmailExtractor(android.content.ContentResolver contentResolver, AccountManager accountManager) {
        super(contentResolver);
        _accountManager = accountManager;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public Collection<Element> ExtractInternal() {

        Account[] accounts = _accountManager.getAccounts();
        Collection<Element> elements = new ArrayList<Element>();

        for(Account account : accounts)
        {
            if (account.type == GmailType){
                Uri dataUri = Uri.parse(String.format(GmailUri, account.name));
                Element accountElement = new Element("AccountName", account.name);
                accountElement.AddChilds(Extract(dataUri));
                elements.add(accountElement);
            }
        }

        return elements;
    }

    @Override
    protected List<String> GetColumnNames() {
        List<String> columns = new ArrayList<String>();
        columns.add(GetMainColumnName());
        columns.add("_id");
        columns.add("numConversations");
        columns.add("numUnreadConversations");

        return columns;
    }

    @Override
    protected String GetMainColumnName() {
        return "name";
    }

    @Override
    protected Uri GetDataUri() {
        return null;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "Gmail";
    }
}

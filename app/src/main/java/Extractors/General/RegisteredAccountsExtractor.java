package Extractors.General;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Collection;

import Dtos.Element;
import Extractors.Common.ExtractorBase;

public class RegisteredAccountsExtractor extends ExtractorBase {

    private Context _context;

    public RegisteredAccountsExtractor(Context context) {
        _context = context;
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "RegisteredAccounts";
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public Collection<Element> ExtractInternal() {
        AccountManager accountManager = AccountManager.get(_context);
        Collection<Element> elements = new ArrayList<Element>();
        Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts)
        {
            elements.add(new Element("Type", account.type));
        }

        return elements;
    }
}

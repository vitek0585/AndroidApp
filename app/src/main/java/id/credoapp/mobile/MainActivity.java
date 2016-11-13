package id.credoapp.mobile;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void goToCollect(View view){
        //setContentView(R.layout.activity_collect);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            FragmentTransaction tx = getFragmentManager().beginTransaction();
            tx.add(R.id.content, new MainFragment());
            tx.addToBackStack(null);
            tx.commit();

//          setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setShowHideAnimationEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
            if (getFragmentManager().getBackStackEntryCount() == 0)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
}

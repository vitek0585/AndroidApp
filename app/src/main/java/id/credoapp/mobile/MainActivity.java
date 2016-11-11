package id.credoapp.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToCollect(View view){
        Intent intent = new Intent(this, CollectActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_collect);
    }
}

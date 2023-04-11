package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ListItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        // create a new instance of your fragment
        Fragment fragment = new ListItemFragment();

        // get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // begin a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // add the fragment to the container
        fragmentTransaction.replace(R.id.fragment_container, fragment);

        // commit the transaction
        fragmentTransaction.commit();
    }
}
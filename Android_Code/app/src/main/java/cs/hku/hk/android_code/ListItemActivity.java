package cs.hku.hk.android_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

public class ListItemActivity extends AppCompatActivity {
    int[] imgid = {
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple
    };

    String[] textid = {
            "apple",
            "banana",
            "carrot",
            "apple",
            "banana",
            "carrot",
            "apple"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        GridView simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), imgid, textid);
        simpleGrid.setAdapter(customAdapter);
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity

            }
        });
    }
}
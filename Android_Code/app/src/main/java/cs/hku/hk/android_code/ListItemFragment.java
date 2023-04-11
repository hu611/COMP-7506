package cs.hku.hk.android_code;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListItemFragment extends ListFragment {

    String[] itemname = {
            "Apple",
            "Banana",
            "Cherry",
            "Date",
            "Grapes",
            "Kiwi",
            "Mango"
    };

    Integer[] imgid = {
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple
    };
    public ListItemFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageItemAdapter imageItemAdapter = new ImageItemAdapter(getActivity(), itemname, imgid);
        setListAdapter(imageItemAdapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }
}
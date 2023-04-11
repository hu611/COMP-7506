package cs.hku.hk.android_code;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageItemAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public ImageItemAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.activity_list_item, itemname);
        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fragment_list_item, null, true);
        // 获取TextView和ImageView对象
        TextView txtTitle = rowView.findViewById(R.id.titleTextView);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        // 设置文本和图像
        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;
    }
}
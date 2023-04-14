package cs.hku.hk.android_code;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    Bitmap[]imgIds;
    String[]txtIds;
    LayoutInflater inflter;
    public CustomAdapter(Context applicationContext, Bitmap[] imgIds, String[] txtIds) {
        this.context = applicationContext;
        this.imgIds = imgIds;
        this.txtIds = txtIds;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return txtIds.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_grid_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.merchant_img);
        imageView.setImageBitmap(this.imgIds[i]);
        TextView textView = (TextView) view.findViewById(R.id.merchant_txt);
        textView.setText(this.txtIds[i]);
        return view;
    }
}

package com.bvmiste.vision16;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by arvind on 14/1/16.
 */
public class Eventimageadapter extends BaseAdapter {

    private Context mContext;
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.civil_c1,
            R.drawable.it_c1,
            R.drawable.et_c1,
            R.drawable.mech_c1,
            R.drawable.electrical_c1,
            R.drawable.new_c1,
            //R.drawable.sponsor1
    };
    //constructor
    public Eventimageadapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
        return imageView;
    }
}

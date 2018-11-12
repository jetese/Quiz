package smd.quizpro;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter2 extends BaseAdapter {
        Context context;
        List<RowClasi> rowItems;

        CustomAdapter2(Context context, List<RowClasi> rowItems){
        this.context=context;
        this.rowItems=rowItems;
        }

    @Override
    public int getCount(){ return rowItems.size();}

    @Override
    public Object getItem(int position){ return rowItems.get(position);}

    @Override
    public long getItemId(int position){ return rowItems.indexOf(position);}

    private class ViewHolder{
        ImageView profile_pic;
        TextView nickname;
        TextView max_points;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CustomAdapter2.ViewHolder holder = null;
        ImageView image;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item2,null);
            holder = new CustomAdapter2.ViewHolder();

            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);
            holder.max_points = (TextView) convertView.findViewById(R.id.max_points);

            RowClasi row_pos = rowItems.get(position);

            holder.profile_pic.setImageBitmap(StringToBitMap(row_pos.getPicid()));
            holder.nickname.setText(row_pos.getNickname());
            holder.max_points.setText(row_pos.getPoints());

            convertView.setBackgroundColor(Color.parseColor("#DF1F2D"));
        }

        return convertView;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import smd.quizpro.R;
import smd.quizpro.RowItem;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    CustomAdapter(Context context, List<RowItem> rowItems){
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
        TextView games_number;
        TextView max_points;
        TextView date_last;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        ImageView image;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder();

            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);
            //holder.profile_pic = holder.profile_pic.setImageBitmap(StringToBitMap(((TextView)convertView.findViewById(R.id.profile_pic)).getText().toString()));
            holder.games_number = (TextView) convertView.findViewById(R.id.games_number);
            holder.max_points = (TextView) convertView.findViewById(R.id.max_points);
            holder.date_last = (TextView) convertView.findViewById(R.id.date_last);

            RowItem row_pos = rowItems.get(position);

            holder.profile_pic.setImageBitmap(StringToBitMap(row_pos.getPicid()));
            holder.nickname.setText(row_pos.getNickname());
            holder.games_number.setText(row_pos.getNumber());
            holder.max_points.setText(row_pos.getPoints());
            holder.date_last.setText(row_pos.getDate());
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

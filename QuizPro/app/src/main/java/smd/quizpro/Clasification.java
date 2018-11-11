package smd.quizpro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Clasification extends AppCompatActivity {
    ArrayList<String> texts = new ArrayList<>();
    ArrayList<Integer> images = new ArrayList<>();
    private PlayerDao mPlayerDao;
    List<Player> points;
    private Typeface custom_font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasification);

        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        mPlayerDao = db.playerDao();

        //Desactivar Action Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Añadir fuente
        TextView tx = (TextView)findViewById(R.id.textView3);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);


        points = mPlayerDao.getAllPlayers();

        String listText;
        for (int i = 0; i<points.size() && i<10; i++){
            listText = "        "+points.get(i).getNick();
            for (int j = 0; j <20 - points.get(i).getNick().length(); j++){
                listText +=" ";
            }
            listText += points.get(i).getScore();
            texts.add(listText);
        }

        ListView lv = (ListView) findViewById(R.id.listview_question);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,texts);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,texts){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position,convertView,parent);

                // Set the typeface/font for the current item
                item.setTypeface(custom_font);


                item.setBackgroundColor(
                        Color.parseColor("#DF1F2D") );
                // Set the list view item's text color
                //item.setTextColor(Color.parseColor("#FF3E80F1"));

                // Set the item text style to bold
                //item.setTypeface(item.getTypeface(), Typeface.BOLD);

                // Change the item text size
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
                item.setTextColor(Color.WHITE);

                // return the view
                return item;
            }
        };
        lv.setAdapter(adapter);

    }

    /***
     * Al pulsar atrás en la Clasificación siempre se vuelve al menú
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Clasification.this, Menu.class));
    }
}

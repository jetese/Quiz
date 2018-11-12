package smd.quizpro;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Clasification extends AppCompatActivity {
    private PlayerDao mPlayerDao;
    private Typeface custom_font;
    String[] nicknames;
    String[]profile_pics;
    TypedArray profi;
    String[] max_points;
    List<RowClasi> rowItems;
    ListView myListView;
    Bitmap bitmap;

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

        //Crear lista de usuarios
        setList();

    }

    /***
     * Al pulsar atrás en la Clasificación siempre se vuelve al menú
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Clasification.this, Menu.class));
    }

    //Generar o actualizar lista
    public void setList(){
        rowItems = new ArrayList<RowClasi>();

        List<Player> players = mPlayerDao.getPlayersScore();
        nicknames = new String[players.size()];
        max_points = new String[players.size()];
        profile_pics = new String[players.size()];

        profi =  getResources().obtainTypedArray(R.array.profile_pics);

        for(int i= 0; i<players.size(); i++){
            nicknames[i] = players.get(i).getNick();
            max_points[i] = Integer.toString( players.get(i).getScore());
            profile_pics[i] = players.get(i).getPhoto();

            RowClasi item = new RowClasi(nicknames[i],profile_pics[i],max_points[i]);
            rowItems.add(item);
        }

        myListView = (ListView) findViewById(R.id.listview_question);
        CustomAdapter2 adapter = new CustomAdapter2(this,rowItems);
        myListView.setAdapter(adapter);
    }
}

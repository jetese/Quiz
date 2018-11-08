package smd.quizpro;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static smd.quizpro.Configuration.EXTRA_REPLY;

public class Profile extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] nicknames;
    int[] profile_pics;
    TypedArray profi;
    String[] number_games;
    String[] max_points;
    String[] last_dates;
    String itemSelected;

    List<RowItem> rowItems;
    ListView myListView;
    private PlayerDao mPlayerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Seleccionar base de datos
        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        mPlayerDao = db.playerDao();

        setList();

        //Añadir fuente a los diferentes elementos
        TextView tx = (TextView)findViewById(R.id.textView3);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/spiderman.ttf");
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.selectUser);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.createUser);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.textView4);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.plain_text_input);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.send);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.update);
        tx.setTypeface(custom_font);
        tx = (TextView)findViewById(R.id.delete);
        tx.setTypeface(custom_font);



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        itemSelected = rowItems.get(position).getNickname();
        for (int i = 0; i < myListView.getChildCount(); i++) {
            if(position == i ){
                myListView.getChildAt(i).setBackgroundColor(Color.BLUE);
            }else{
                myListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            }
        }

        Configuration.user = itemSelected;

    }

    public void createUserMenu(View v){
        //Visibles
        findViewById(R.id.textView4).setVisibility(View.VISIBLE);
        findViewById(R.id.plain_text_input).setVisibility(View.VISIBLE);
        findViewById(R.id.send).setVisibility(View.VISIBLE);

        //Invisibles
        findViewById(R.id.createUser).setVisibility(View.INVISIBLE);
    }

    public void createUser(View v){
        EditText mEditPlayerView = findViewById(R.id.plain_text_input);
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditPlayerView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String word = mEditPlayerView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
            word = word.toLowerCase();
            Player p;
            p = mPlayerDao.selectPlayer(word);
            if (p == null){
                Player jugador = new Player(word,0,0);
                mPlayerDao.insert(jugador);
            }
            Configuration.user = word;

            //Visibles
            findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
            findViewById(R.id.plain_text_input).setVisibility(View.INVISIBLE);
            findViewById(R.id.send).setVisibility(View.INVISIBLE);

            //Invisibles
            findViewById(R.id.createUser).setVisibility(View.VISIBLE);

            setList();

        }

        //finish();
    }

    public void selectUser(View v){
        //Visibles
        findViewById(R.id.listview_question).setVisibility(View.VISIBLE);
        findViewById(R.id.update).setVisibility(View.VISIBLE);
        findViewById(R.id.delete).setVisibility(View.VISIBLE);

        //Invisibles
        findViewById(R.id.createUser).setVisibility(View.INVISIBLE);
        findViewById(R.id.selectUser).setVisibility(View.INVISIBLE);
    }

    public void setList(){
        rowItems = new ArrayList<RowItem>();

        List<Player> players = mPlayerDao.getAllPlayersNick();
        nicknames = new String[players.size()];
        number_games = new String[players.size()];
        max_points = new String[players.size()];
        last_dates = new String[players.size()];
        profile_pics = new int[players.size()];

        profi =  getResources().obtainTypedArray(R.array.profile_pics);

        for(int i= 0; i<players.size(); i++){
            nicknames[i] = players.get(i).getNick();
            max_points[i] = Integer.toString( players.get(i).getScore());
            number_games[i] = Integer.toString(players.get(i).getNumberGames());
            last_dates[i] = players.get(i).getDate();
            profile_pics[i] = profi.getResourceId(0,-1);

            RowItem item = new RowItem(nicknames[i],profile_pics[i],max_points[i],number_games[i],last_dates[i]);
            rowItems.add(item);
        }

        myListView = (ListView) findViewById(R.id.listview_question);
        CustomAdapter adapter = new CustomAdapter(this,rowItems);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);
    }



}
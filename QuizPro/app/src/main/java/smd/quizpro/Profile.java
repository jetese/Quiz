package smd.quizpro;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] nicknames;
    int[] profile_pics;
    TypedArray profi;
    String[] number_games;
    String[] max_points;
    String[] last_dates;

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        String member_name = rowItems.get(position).getNickname();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }
}

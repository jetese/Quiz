package smd.quizpro;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Profile extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] nicknames;
    String[]profile_pics;
    TypedArray profi;
    String[] number_games;
    String[] max_points;
    String[] last_dates;
    String itemSelected;
    String word;
    List<RowItem> rowItems;
    ListView myListView;
    private PlayerDao mPlayerDao;
    Bitmap bitmap;
    PackageManager pm;

    static final String ANON = "anonimous";
    static final int DO_PHOTO = 1;
    static final int PICK_IMAGE = 2;
    static final int UPDATE_PHOTO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Seleccionar base de datos
        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(this);
        mPlayerDao = db.playerDao();

        //Eliminar barra de herramientas
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Crear lista de usuarios
        setList();

        //Variable para comprobar si hay camara
        pm = this.getPackageManager();

        //Añadir fuente a los diferentes elementos
        AddFont();
    }

    //Añadir fuente a los diferentes elementos
    private void AddFont(){
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
        tx = (TextView)findViewById(R.id.confirm);
        tx.setTypeface(custom_font);
    }

    @Override   //Selección de usuario en la lista
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        itemSelected = rowItems.get(position).getNickname();
        for (int i = 0; i < myListView.getChildCount(); i++) {
            if(position == i ){
                myListView.getChildAt(i).setBackgroundColor(Color.WHITE);
            }else{
                myListView.getChildAt(i).setBackgroundColor(Color.parseColor("#DF1F2D"));
            }
        }

        Configuration.user = itemSelected;

    }

    // Menu de crear usuario
    public void createUserMenu(View v){
        //Visibles
        findViewById(R.id.textView4).setVisibility(View.VISIBLE);
        findViewById(R.id.plain_text_input).setVisibility(View.VISIBLE);
        findViewById(R.id.send).setVisibility(View.VISIBLE);

        //Invisibles
        findViewById(R.id.createUser).setVisibility(View.INVISIBLE);
    }

    //Creación de usuario
    public void createUser(View v){
        EditText mEditPlayerView = findViewById(R.id.plain_text_input);

        if (!TextUtils.isEmpty(mEditPlayerView.getText())) {
            word = mEditPlayerView.getText().toString();
            word = word.toLowerCase();

            if (mPlayerDao.selectPlayer(word) == null){
                PhotoOrGallery(DO_PHOTO);
            }
            Configuration.user = word;

            //Visibles
            findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
            findViewById(R.id.plain_text_input).setVisibility(View.INVISIBLE);
            findViewById(R.id.send).setVisibility(View.INVISIBLE);

            //Invisibles
            findViewById(R.id.createUser).setVisibility(View.VISIBLE);
        }
    }

    //Menu de listado de usuarios
    public void selectUser(View v){
        //Visibles
        findViewById(R.id.listview_question).setVisibility(View.VISIBLE);
        findViewById(R.id.update).setVisibility(View.VISIBLE);
        findViewById(R.id.delete).setVisibility(View.VISIBLE);

        //Invisibles
        findViewById(R.id.createUser).setVisibility(View.INVISIBLE);
        findViewById(R.id.selectUser).setVisibility(View.INVISIBLE);
    }

    //Llamada de botón a actualizar foto
    public void updatePhoto(View v){
        PhotoOrGallery(UPDATE_PHOTO);
    }

    // Borrar usuario seleccionado?
    public void deleteUser(View v){
        TextView confirm = findViewById(R.id.confirm);
        confirm.setText("¿Quieres eliminar el usuario "+Configuration.user);
        findViewById(R.id.confirm).setVisibility(View.VISIBLE);
        findViewById(R.id.yes).setVisibility(View.VISIBLE);
        findViewById(R.id.no).setVisibility(View.VISIBLE);
        findViewById(R.id.listview_question).setVisibility(View.INVISIBLE);
        findViewById(R.id.update).setVisibility(View.INVISIBLE);
        findViewById(R.id.delete).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView3).setVisibility(View.INVISIBLE);
    }

    //Borrar usuario
    public void deleteUserConfirm(View v){
        if(Configuration.user != ANON){
            mPlayerDao.deletePlayer(Configuration.user);
            Configuration.user = ANON;
            setList();
        }

        findViewById(R.id.confirm).setVisibility(View.INVISIBLE);
        findViewById(R.id.yes).setVisibility(View.INVISIBLE);
        findViewById(R.id.no).setVisibility(View.INVISIBLE);
        findViewById(R.id.listview_question).setVisibility(View.VISIBLE);
        findViewById(R.id.update).setVisibility(View.VISIBLE);
        findViewById(R.id.delete).setVisibility(View.VISIBLE);
        findViewById(R.id.textView3).setVisibility(View.VISIBLE);
    }

    // No borrar Usuario
    public void deleteUserDeny(View v){
        findViewById(R.id.confirm).setVisibility(View.INVISIBLE);
        findViewById(R.id.yes).setVisibility(View.INVISIBLE);
        findViewById(R.id.no).setVisibility(View.INVISIBLE);
        findViewById(R.id.listview_question).setVisibility(View.VISIBLE);
        findViewById(R.id.update).setVisibility(View.VISIBLE);
        findViewById(R.id.delete).setVisibility(View.VISIBLE);
        findViewById(R.id.textView3).setVisibility(View.VISIBLE);
    }

    //Generar o actualizar lista
    public void setList(){
        rowItems = new ArrayList<RowItem>();

        List<Player> players = mPlayerDao.getAllPlayersNick();
        nicknames = new String[players.size()];
        number_games = new String[players.size()];
        max_points = new String[players.size()];
        last_dates = new String[players.size()];
        profile_pics = new String[players.size()];

        profi =  getResources().obtainTypedArray(R.array.profile_pics);
        int selected = 0;

        for(int i= 0; i<players.size(); i++){
            nicknames[i] = players.get(i).getNick();
            if(nicknames[i] == Configuration.user){
                selected = i;
            }
            max_points[i] = Integer.toString( players.get(i).getScore());
            number_games[i] = Integer.toString(players.get(i).getNumberGames());
            last_dates[i] = players.get(i).getDate();
            profile_pics[i] = players.get(i).getPhoto();

            RowItem item = new RowItem(nicknames[i],profile_pics[i],max_points[i],number_games[i],last_dates[i]);
            rowItems.add(item);
        }

        myListView = (ListView) findViewById(R.id.listview_question);
        CustomAdapter adapter = new CustomAdapter(this,rowItems);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);
    }


    @Override // Manejador de los resultados de las Actividades Nueva foto, Elegir imagen de galeria, Actualizar foto
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if ( requestCode == DO_PHOTO && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");

            Player jugador = new Player(word, 0, 0, BitMapToString(bitmap));
            mPlayerDao.insert(jugador);
        }
        else if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            android.net.Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Player jugador = new Player(word, 0, 0, BitMapToString(bitmap));
            mPlayerDao.insert(jugador);
        }
        else if(requestCode == UPDATE_PHOTO && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");

            mPlayerDao.updatePhoto(Configuration.user,BitMapToString(bitmap));
        }

        setList();
    }

    //LLamada a las actividades Nueva foto o Actualizar foto según el parámetro de entrada
    private void PhotoOrGallery(int selectOrUpdate){
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, selectOrUpdate);
        }
        else{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }


    //Pasar BitMap a String
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Profile.this, Menu.class));
    }



}

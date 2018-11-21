package dadm.scaffold.counter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.FramesPerSecondCounter;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.space.ParallaxBackground;
import dadm.scaffold.space.SpaceShipPlayer;

//Fragmento del juego
public class GameFragment extends BaseFragment implements View.OnClickListener {

    private GameEngine theGameEngine;
    private int ship;   //Nave seleccionada
    private Typeface font;  //Guardamos la fuente para setearla en el Alertdialog del pause
    public AlertDialog dialog; // Ventana de pause

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        //Obtenemos la nave que se pasa por parámetros
        ship = getArguments().getInt("ship");

        //Cambiamos la fuente del botón pause
        TextView txt = (TextView) rootView.findViewById(R.id.btn_play_pause);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/azonix.otf");
        txt.setTypeface(font);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);

                //Incicializamos el juego
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setTheInputController(new JoystickInputController(getView()));
                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));

                //Obtenemos el identificador del drawable de la nave
                int shipIdentifier = getActivity().getApplicationContext().getResources().getIdentifier("ship"+String.valueOf(ship), "drawable",
                        getActivity().getApplicationContext().getPackageName());

                //Inicializamos el GameController
                //El game controller se encarga de inicializar elementos del juego, tale como parallax background, enemigos, nave jugador, score, etc.
                //Le pasamos la fuente para poder cambiar los textos que se dibujan directamente sobre el canvas ( ganar, puntuación)
                theGameEngine.addGameObject(new GameController(theGameEngine,shipIdentifier,20, R.drawable.maxresdefault,font));

                //Comenzamos el juego
                theGameEngine.startGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning() && theGameEngine.pause){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();

        //Creamos un alert dialog customizado con el layout alertdialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.alertdialog,null);

        //Cambiamos la fuente
        TextView aux =  (TextView) mView.findViewById(R.id.textView);
        aux.setTypeface(font);
        aux = mView.findViewById(R.id.btn_stop);
        aux.setTypeface(font);
        aux = mView.findViewById(R.id.btn_resume);
        aux.setTypeface(font);

        //Seteamos los ClickListener de los botones
        Button resume = (Button)  mView.findViewById(R.id.btn_resume);
        resume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                theGameEngine.resumeGame();
            }
        });
        Button stop = (Button)  mView.findViewById(R.id.btn_stop);
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
                theGameEngine.stopGame();
                ((ScaffoldActivity)getActivity()).navigateBack();
            }
        });

        //Creamos y mostramos el dialogo de pausa
        mBuilder.setView(mView);
        dialog = mBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                theGameEngine.resumeGame();
            }
        }).create();
        dialog.show();
    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            theGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }
}

package dadm.scaffold.counter;

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


public class GameFragment extends BaseFragment implements View.OnClickListener {
    private GameEngine theGameEngine;
    private int ship;
    private final int[] mArray = new int[4];
    private Typeface font;
    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        ship = getArguments().getInt("ship");
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
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setTheInputController(new JoystickInputController(getView()));
                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));

                int shipIdentifier = getActivity().getApplicationContext().getResources().getIdentifier("ship"+String.valueOf(ship), "drawable",
                        getActivity().getApplicationContext().getPackageName());
                theGameEngine.addGameObject(new GameController(theGameEngine,shipIdentifier,20, R.drawable.maxresdefault,font));

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

        new AlertDialog.Builder(getActivity())
                .setTitle("Game paused")
                .setMessage("Press resume to play again")
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity)getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        theGameEngine.resumeGame();
                    }
                })
                .create()
                .show();

        //TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        //Typeface face=Typeface.createFromAsset(getAssets(),"fonts/FONT");
        //textView.setTypeface(face);
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

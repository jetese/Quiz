package dadm.scaffold.counter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

public class EndFragment extends BaseFragment implements View.OnClickListener  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_end_game, container, false);

        //Cambiamos el texto del Textvieew Score con el parámetro de entrada del fragmento
        TextView score = (TextView)rootView.findViewById(R.id.score);
        score.setText("Score: "+getArguments().getInt("score"));

        //Seteamos los textos con la fuente añadida en la carpeta assets/font
        TextView txt = (TextView) rootView.findViewById(R.id.titleGame);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/azonix.otf");
        txt.setTypeface(font);
        score.setTypeface(font);
        txt = (TextView) rootView.findViewById(R.id.btn_finish);
        txt.setTypeface(font);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Añadimos el click al botón finish
        view.findViewById(R.id.btn_finish).setOnClickListener(this);

    }

    //En el click del botón, volvemos al menú principal
    @Override
    public void onClick(View v) {
        ((ScaffoldActivity)getActivity()).mainMenu();
    }


}

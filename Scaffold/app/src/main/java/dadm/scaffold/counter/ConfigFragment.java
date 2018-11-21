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

//Clase que maneja el fragmento de configuración
public class ConfigFragment extends BaseFragment implements View.OnClickListener {
    private int ship;
    public ConfigFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ship, container, false);

        //Seleccionamos la nave incial
        ship = 0;

        //Seteamos los textos con la fuente añadida en la carpeta assets/font
        TextView txt = (TextView) rootView.findViewById(R.id.textView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/azonix.otf");
        txt.setTypeface(font);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Añadimos los botones para la selección de las naves
        view.findViewById(R.id.ship1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =1;
                ((ScaffoldActivity)getActivity()).setShip(ship);
                ((ScaffoldActivity)getActivity()).mainMenu();
            }
        });
        view.findViewById(R.id.ship2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =2;
                ((ScaffoldActivity)getActivity()).setShip(ship);
                ((ScaffoldActivity)getActivity()).mainMenu();
            }
        });
        view.findViewById(R.id.ship3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =3;
                ((ScaffoldActivity)getActivity()).setShip(ship);
                ((ScaffoldActivity)getActivity()).mainMenu();
            }
        });
        view.findViewById(R.id.ship4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =4;
                ((ScaffoldActivity)getActivity()).setShip(ship);
                ((ScaffoldActivity)getActivity()).mainMenu();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

}

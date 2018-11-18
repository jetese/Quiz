package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

public class ConfigFragment extends BaseFragment implements View.OnClickListener {
    private int ship;
    public ConfigFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ship, container, false);
        ship = 0;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.back).setOnClickListener(this);
        view.findViewById(R.id.ship1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =1;
            }
        });
        view.findViewById(R.id.ship2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =2;
            }
        });
        view.findViewById(R.id.ship3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =3;
            }
        });
        view.findViewById(R.id.ship4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ship =4;
            }
        });

    }

    @Override
    public void onClick(View v) {
        ((ScaffoldActivity)getActivity()).setShip(ship);
        ((ScaffoldActivity)getActivity()).mainMenu();
    }

}

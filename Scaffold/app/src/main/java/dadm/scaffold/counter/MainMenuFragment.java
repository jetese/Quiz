package dadm.scaffold.counter;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class MainMenuFragment extends BaseFragment implements View.OnClickListener {
    public MainMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        TextView txt = (TextView) rootView.findViewById(R.id.textView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/azonix.otf");
        txt.setTypeface(font);
        txt = (TextView)rootView.findViewById(R.id.btn_start);
        txt.setTypeface(font);
        txt = (TextView)rootView.findViewById(R.id.btn_config);
        txt.setTypeface(font);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TextView tx = (TextView)view.findViewById(R.id.textView);
        //Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/spiderman.ttf");
        //tx.setTypeface(custom_font);

        view.findViewById(R.id.btn_start).setOnClickListener(this);
        view.findViewById(R.id.btn_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).configGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        ((ScaffoldActivity)getActivity()).startGame();
    }

}

package smd.quizpro;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

public class PlayerViewModel extends AndroidViewModel{
    private PlayerRepository mRepository;
    private LiveData<Player> mPlayer;

    public PlayerViewModel (Application application, String name) {
        super(application);
        mRepository = new PlayerRepository(application,name);
        mPlayer = mRepository.getPlayer();
    }

    LiveData<Player> getPlayer() { return mPlayer; }

    public void insert(Player player) { mRepository.insert(player); }
}

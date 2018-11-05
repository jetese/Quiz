package smd.quizpro;

import android.app.Application;
import android.arch.lifecycle.LiveData;


public class PlayerRepository {
    private PlayerDao mPlayerDao;
    private LiveData<Player> mPlayer;

    PlayerRepository(Application application, String name) {
        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
        //mPlayer = mPlayerDao.selectPlayer(name);
    }

    LiveData<Player> getPlayer() {
        return mPlayer;
    }

    public void insert (Player player) {
        new insertAsyncTask(mPlayerDao).execute(player);
    }

}

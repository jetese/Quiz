package smd.quizpro;

import android.os.AsyncTask;

class insertAsyncTask  extends AsyncTask<Player, Void, Void> {

    private PlayerDao mAsyncTaskDao;

    insertAsyncTask(PlayerDao dao) {
        mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Player... params) {
        mAsyncTaskDao.insert(params[0]);
        return null;
    }
}

package smd.quizpro;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {
    @Insert
    void insert(Player player);

    @Query("DELETE FROM player_table")
    void deleteAll();

    @Query("SELECT * from player_table ORDER BY Score ASC")
    List<Player> getAllPlayers();

    @Query("SELECT * from player_table ORDER BY Nick ASC")
    List<Player> getAllPlayersNick();

    @Query("UPDATE player_table SET Score = :score WHERE Nick = :name")
    void updateScore(int score, String name);

    @Query("UPDATE player_table SET Difficulty = :dif WHERE Nick = :name")
    void updateDifficulty(int dif, String name);

    @Query("UPDATE player_table SET Questions = :quest WHERE Nick = :name")
    void updateQuestions(int quest, String name);

    @Query("SELECT Score FROM player_table WHERE Nick = :name")
    int selectScore(String name);

    @Query("SELECT Difficulty FROM player_table WHERE Nick = :name")
    int selectDifficulty(String name);

    @Query("SELECT Questions FROM player_table WHERE Nick = :name")
    int selectQuestions(String name);

    @Query("Select * FROM player_table Where Nick = :name")
    Player selectPlayer(String name);

    @Query("UPDATE player_table SET Score = :score WHERE Nick = :name AND Score < :score")
    void updateScoreGreater(String name, int score);

    @Query("UPDATE player_table SET Date = :date WHERE Nick = :name")
    void updateDate(String name,String date);

    @Query("UPDATE player_table SET NumberGames = NumberGames+1 WHERE Nick = :name")
    void updateNumberGames(String name);
}

package smd.quizpro;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question q);


    @Query("SELECT * from question_table")
    List<Question> selectAll();

    @Query("DELETE FROM question_table")
    void deleteAll();


}

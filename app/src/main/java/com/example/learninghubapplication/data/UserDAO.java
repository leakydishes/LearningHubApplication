package com.example.learninghubapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.learninghubapplication.model.User;


@Dao
public interface UserDAO {
    // get user from SQL database
    @Query("SELECT * FROM user_table WHERE userName = :userName and password = :password")
    User getUser(String userName, String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}

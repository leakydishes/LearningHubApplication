package com.example.learninghubapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learninghubapplication.model.TextModel;

import java.util.List;

@Dao
public interface TextModelDao {

    //insert into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TextModel textmodel);

    //select All Data
    @Query("select * from  text_table ORDER BY name ASC")
    List<TextModel> getTextList();

    //select item from TextModel
    @Query("select * from text_table where `id`= :id ")
    TextModel findById(String id);

    //update database
    @Update
    void update(TextModel textmodel);

    //delete data from database
    @Query("delete from text_table where `id`= :id")
    void deleteData(int id);

    // Delete user from DB
    @Query("DELETE FROM text_table where name = :name")
    void deleteByUserName(String name);

    //updateData
    @Query("update text_table SET name= :name, text= :text where `id`= :id")
    void updateData(String name, String text, int id);
}

package com.shushant.androidmvvmtask.persistence
import androidx.room.*
import com.shushant.androidmvvmtask.models.SandboxResponseClassItem

@Dao
interface DemoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: List<SandboxResponseClassItem>)

    @Query("SELECT * FROM SandboxResponseClassItem")
    suspend fun getData():List<SandboxResponseClassItem>

    @Query( "DELETE FROM SandboxResponseClassItem WHERE id == :item ")
    suspend fun delete(item: String)
}

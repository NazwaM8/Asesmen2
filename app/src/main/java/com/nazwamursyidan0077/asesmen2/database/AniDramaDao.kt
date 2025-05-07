package com.nazwamursyidan0077.asesmen2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nazwamursyidan0077.asesmen2.model.AniDrama
import kotlinx.coroutines.flow.Flow

@Dao
interface AniDramaDao {
    @Insert
    suspend fun insert(aniDrama: AniDrama)

    @Update
    suspend fun update(aniDrama: AniDrama)

    @Query("SELECT * FROM anidrama ORDER BY releaseDate DESC")
    fun getAniDrama(): Flow<List<AniDrama>>

    @Query("SELECT * FROM anidrama WHERE id = :id")
    suspend fun getAniDramaById(id: Long): AniDrama?

    @Query("DELETE FROM anidrama WHERE id = :id")
    suspend fun deleteById(id: Long)
}
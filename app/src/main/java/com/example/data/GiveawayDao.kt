package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GiveawayDao {
  @Query("SELECT * FROM submissions ORDER BY createdAt DESC")
  fun getAllSubmissions(): Flow<List<GiveawaySubmission>>

  @Query("SELECT * FROM submissions WHERE id = :id LIMIT 1")
  suspend fun getSubmissionById(id: Long): GiveawaySubmission?

  @Query("SELECT * FROM submissions WHERE mobileNumber = :mobile ORDER BY createdAt DESC LIMIT 1")
  suspend fun getSubmissionByMobile(mobile: String): GiveawaySubmission?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSubmission(submission: GiveawaySubmission): Long

  @Update
  suspend fun updateSubmission(submission: GiveawaySubmission)

  @Delete
  suspend fun deleteSubmission(submission: GiveawaySubmission)

  @Query("DELETE FROM submissions WHERE id = :id")
  suspend fun deleteSubmissionById(id: Long)

  @Query("DELETE FROM submissions")
  suspend fun deleteAllSubmissions()

  // Media
  @Query("SELECT * FROM admin_media ORDER BY createdAt DESC")
  fun getAllMedia(): Flow<List<AdminMedia>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMedia(media: AdminMedia): Long

  @Delete
  suspend fun deleteMedia(media: AdminMedia)

  @Query("DELETE FROM admin_media WHERE id = :id")
  suspend fun deleteMediaById(id: Long)

  @Query("DELETE FROM admin_media")
  suspend fun deleteAllMedia()

  // App Config
  @Query("SELECT * FROM app_config WHERE id = 1 LIMIT 1")
  fun getAppConfig(): Flow<AppConfig?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateConfig(config: AppConfig)
}

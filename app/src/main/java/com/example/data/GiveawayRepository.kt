package com.example.data

import kotlinx.coroutines.flow.Flow

class GiveawayRepository(private val dao: GiveawayDao) {
  val allSubmissions: Flow<List<GiveawaySubmission>> = dao.getAllSubmissions()
  val allMedia: Flow<List<AdminMedia>> = dao.getAllMedia()

  suspend fun insertSubmission(submission: GiveawaySubmission): Long {
    return dao.insertSubmission(submission)
  }

  suspend fun getSubmissionById(id: Long): GiveawaySubmission? {
    return dao.getSubmissionById(id)
  }

  suspend fun updateSubmission(submission: GiveawaySubmission) {
    dao.updateSubmission(submission)
  }

  suspend fun deleteSubmission(submission: GiveawaySubmission) {
    dao.deleteSubmission(submission)
  }

  suspend fun deleteSubmissionById(id: Long) {
    dao.deleteSubmissionById(id)
  }

  suspend fun deleteAllSubmissions() {
    dao.deleteAllSubmissions()
  }

  suspend fun insertMedia(media: AdminMedia): Long {
    return dao.insertMedia(media)
  }

  suspend fun deleteMedia(media: AdminMedia) {
    dao.deleteMedia(media)
  }

  suspend fun deleteMediaById(id: Long) {
    dao.deleteMediaById(id)
  }

  suspend fun deleteAllMedia() {
    dao.deleteAllMedia()
  }

  val appConfig: Flow<AppConfig?> = dao.getAppConfig()

  suspend fun updateAppConfig(config: AppConfig) {
    dao.insertOrUpdateConfig(config)
  }
}

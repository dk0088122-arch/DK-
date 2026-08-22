package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [GiveawaySubmission::class, AdminMedia::class, AppConfig::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun giveawayDao(): GiveawayDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "free_warrior_giveaway.db"
        )
          .addCallback(DatabaseCallback(scope))
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }

    private class DatabaseCallback(
      private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
          scope.launch(Dispatchers.IO) {
            populateInitialData(database.giveawayDao())
          }
        }
      }

      suspend fun populateInitialData(dao: GiveawayDao) {
        // Preload sample giveaway submissions
        dao.insertSubmission(
          GiveawaySubmission(
            playerName = "ShadowHunterX",
            euid = "849201844",
            level = "78",
            mobileNumber = "+1 (555) 349-8821",
            otpCode = "73849102",
            status = "Approved",
            createdAt = System.currentTimeMillis() - 1000 * 60 * 120
          )
        )
        dao.insertSubmission(
          GiveawaySubmission(
            playerName = "ValkyrieQueen",
            euid = "918237402",
            level = "92",
            mobileNumber = "+1 (555) 883-9104",
            otpCode = "48201938",
            status = "Pending",
            createdAt = System.currentTimeMillis() - 1000 * 60 * 45
          )
        )
        dao.insertSubmission(
          GiveawaySubmission(
            playerName = "ApexBlaze99",
            euid = "449018239",
            level = "64",
            mobileNumber = "+1 (555) 472-1082",
            otpCode = "91837462",
            status = "Pending",
            createdAt = System.currentTimeMillis() - 1000 * 60 * 15
          )
        )

        // Preload sample admin media
        dao.insertMedia(
          AdminMedia(
            title = "Season 14 Grand Diamond Airdrop",
            category = "Banner",
            description = "Official promotional banner for the 20,000 Diamonds Warrior Celebration event.",
            iconType = "diamond",
            badgeText = "OFFICIAL EVENT",
            accentColorHex = 0xFF0061A4,
            imageUrl = ""
          )
        )
        dao.insertMedia(
          AdminMedia(
            title = "Proof of 20,000 Diamonds Transferred - Batch #4",
            category = "Winner Proof",
            description = "Verified in-game mailbox distribution screenshot for 50 approved warrior IDs.",
            iconType = "trophy",
            badgeText = "VERIFIED PROOF",
            accentColorHex = 0xFFFFB703,
            imageUrl = ""
          )
        )
        dao.insertMedia(
          AdminMedia(
            title = "Important Security Notice: Beware of Impersonators",
            category = "Announcement",
            description = "Only codes generated through the Free Warrior Giveaway app portal are official.",
            iconType = "shield",
            badgeText = "SECURITY",
            accentColorHex = 0xFF1B873F,
            imageUrl = ""
          )
        )

        // Preload default App Config
        dao.insertOrUpdateConfig(
          AppConfig(
            id = 1,
            appTitle = "FREE WARRIOR GIVEAWAY",
            appSubtitle = "Claim Your 20,000 Diamonds Warrior Celebration Reward",
            bannerTitle = "Season 14 Grand Diamond Airdrop",
            bannerSubtitle = "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.",
            bannerImageUrl = "",
            bannerBadge = "EXCLUSIVE REWARD",
            bannerAccentColorHex = 0xFF0061A4,
            isBannerActive = true
          )
        )
      }
    }
  }
}

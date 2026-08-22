package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "submissions")
data class GiveawaySubmission(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val playerName: String,
  val euid: String,
  val level: String,
  val mobileNumber: String,
  val otpCode: String,
  val status: String = "Pending", // "Pending", "Approved", "Rejected"
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_media")
data class AdminMedia(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val category: String, // "Banner", "Winner Proof", "Announcement", "Reward Graphic"
  val description: String,
  val imageUrl: String = "",
  val iconType: String = "diamond", // "diamond", "trophy", "shield", "gift", "star", "swords", "crown"
  val badgeText: String = "FEATURED",
  val accentColorHex: Long = 0xFF0061A4,
  val isFeatured: Boolean = true,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_config")
data class AppConfig(
  @PrimaryKey
  val id: Int = 1,
  val appTitle: String = "FREE WARRIOR GIVEAWAY",
  val appSubtitle: String = "Claim Your 20,000 Diamonds Warrior Celebration Reward",
  val bannerTitle: String = "Season 14 Grand Diamond Airdrop",
  val bannerSubtitle: String = "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.",
  val bannerImageUrl: String = "",
  val bannerBadge: String = "EXCLUSIVE REWARD",
  val bannerAccentColorHex: Long = 0xFF0061A4,
  val isBannerActive: Boolean = true
)


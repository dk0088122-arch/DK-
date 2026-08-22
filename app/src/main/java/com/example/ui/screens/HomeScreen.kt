package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.AdminMedia
import com.example.data.AppConfig
import com.example.ui.components.DiamondVectorIcon
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.DividerColor
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarriorBlue
import com.example.ui.theme.WarriorBlueBorder
import com.example.ui.theme.WarriorBlueContainer
import com.example.ui.theme.WarriorBlueDark
import com.example.ui.theme.WarriorBlueDeep
import com.example.ui.theme.WarriorDiamondCyan
import com.example.ui.theme.WarriorDiamondGold

@Composable
fun HomeScreen(
  appConfig: AppConfig = AppConfig(),
  mediaList: List<AdminMedia> = emptyList(),
  onGetLinkClicked: () -> Unit,
  onFooterDoubleTap: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundLight)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 20.dp, vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Top spacing & header with BOLD App Title present everywhere
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 12.dp, bottom = 4.dp)
      ) {
        // App title with Bold Typography
        Text(
          text = appConfig.appTitle.ifEmpty { "FREE WARRIOR\nGIVEAWAY" },
          style = MaterialTheme.typography.displayLarge.copy(
            color = WarriorBlue,
            fontWeight = FontWeight.Black,
            fontSize = 32.sp,
            lineHeight = 36.sp,
            letterSpacing = (-0.5).sp,
            textAlign = TextAlign.Center
          ),
          modifier = Modifier.testTag("app_title_text")
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = appConfig.appSubtitle.ifEmpty { "Claim Your 20,000 Diamonds Warrior Celebration Reward" },
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextSecondary,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
          ),
          modifier = Modifier.padding(horizontal = 16.dp)
        )
      }

      // Dynamic Admin Banner (if active)
      if (appConfig.isBannerActive && appConfig.bannerTitle.isNotEmpty()) {
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(
            containerColor = Color(appConfig.bannerAccentColorHex).copy(alpha = 0.08f)
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            Color(appConfig.bannerAccentColorHex).copy(alpha = 0.4f)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 440.dp)
            .testTag("home_active_banner")
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(50))
                  .background(Color(appConfig.bannerAccentColorHex))
                  .padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Text(
                  text = appConfig.bannerBadge.ifEmpty { "FEATURED EVENT" },
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    letterSpacing = 0.8.sp
                  )
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Campaign,
                  contentDescription = "Event",
                  tint = Color(appConfig.bannerAccentColorHex),
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Live Bounty",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(appConfig.bannerAccentColorHex),
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                  )
                )
              }
            }

            // Banner Image if URL provided
            if (appConfig.bannerImageUrl.isNotEmpty()) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(120.dp)
                  .clip(RoundedCornerShape(16.dp))
                  .background(WarriorBlueContainer),
                contentAlignment = Alignment.Center
              ) {
                AsyncImage(
                  model = appConfig.bannerImageUrl,
                  contentDescription = appConfig.bannerTitle,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize()
                )
              }
            }

            Text(
              text = appConfig.bannerTitle,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = WarriorBlueDeep,
                fontSize = 16.sp
              )
            )

            Text(
              text = appConfig.bannerSubtitle.ifEmpty {
                "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day."
              },
              style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
              )
            )
          }
        }
      }

      // Main Hero Card: 20,000 DIAMONDS + GET LINK
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 440.dp)
          .shadow(
            elevation = 6.dp,
            shape = RoundedCornerShape(32.dp),
            ambientColor = WarriorBlue.copy(alpha = 0.15f),
            spotColor = WarriorBlue.copy(alpha = 0.25f)
          )
          .clip(RoundedCornerShape(32.dp))
          .background(WarriorBlueContainer)
          .border(
            width = 1.5.dp,
            color = WarriorBlueBorder,
            shape = RoundedCornerShape(32.dp)
          )
          .padding(horizontal = 24.dp, vertical = 28.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(18.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          // Circular Warrior Diamond Badge
          Box(
            modifier = Modifier
              .size(96.dp)
              .shadow(8.dp, CircleShape)
              .clip(CircleShape)
              .background(
                Brush.linearGradient(
                  colors = listOf(WarriorBlue, WarriorBlueDark)
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            DiamondVectorIcon(
              size = 52.dp,
              primaryColor = Color.White,
              accentColor = WarriorDiamondCyan
            )
          }

          // Diamond Amount & Reward text
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.Diamond,
                contentDescription = "Diamond Icon",
                tint = WarriorDiamondGold,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "20,000 DIAMONDS",
                style = MaterialTheme.typography.headlineLarge.copy(
                  fontWeight = FontWeight.Black,
                  fontSize = 24.sp,
                  letterSpacing = 0.5.sp,
                  color = WarriorBlueDeep
                ),
                modifier = Modifier.testTag("diamonds_amount_text")
              )
            }

            Text(
              text = "OFFICIAL GIVEAWAY REWARD",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 2.sp,
                color = TextSecondary
              )
            )
          }

          // GET LINK Button
          Button(
            onClick = onGetLinkClicked,
            colors = ButtonDefaults.buttonColors(
              containerColor = WarriorBlue,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(50),
            elevation = ButtonDefaults.buttonElevation(
              defaultElevation = 4.dp,
              pressedElevation = 2.dp
            ),
            modifier = Modifier
              .fillMaxWidth()
              .height(56.dp)
              .testTag("btn_get_link")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = "GET LINK",
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 17.sp,
                  letterSpacing = 1.sp
                )
              )
              Spacer(modifier = Modifier.width(8.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow Forward",
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }

      // Recent Winner Proofs & Visual Elements Gallery (if available)
      if (mediaList.isNotEmpty()) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 440.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = "COMMUNITY PROOFS & UPDATES",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = WarriorBlue,
                letterSpacing = 0.8.sp,
                fontSize = 12.sp
              )
            )
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = "Verified",
              tint = WarriorBlue,
              modifier = Modifier.size(16.dp)
            )
          }

          mediaList.take(2).forEach { media ->
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = SurfaceLight),
              border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(media.accentColorHex).copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = when (media.iconType) {
                      "trophy" -> Icons.Default.EmojiEvents
                      "shield" -> Icons.Default.Shield
                      "star" -> Icons.Default.Star
                      else -> Icons.Default.Diamond
                    },
                    contentDescription = media.title,
                    tint = Color(media.accentColorHex),
                    modifier = Modifier.size(22.dp)
                  )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = media.badgeText.ifEmpty { media.category },
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(media.accentColorHex),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 9.sp
                      )
                    )
                  }
                  Text(
                    text = media.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 13.sp,
                      color = TextPrimary
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  Text(
                    text = media.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = TextSecondary,
                      fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }
            }
          }
        }
      }

      // Subtitle / quote message
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 380.dp)
          .padding(top = 8.dp, bottom = 4.dp)
      ) {
        Text(
          text = "Join thousands of warriors claiming their daily bounty. Either you get the code or direct diamonds in your mailbox!",
          style = MaterialTheme.typography.bodyMedium.copy(
            color = TextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            textAlign = TextAlign.Center
          ),
          modifier = Modifier.padding(horizontal = 8.dp)
        )
      }

      // Footer with double-tap support for admin login
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp, top = 4.dp)
      ) {
        // Decorative pill bar
        Box(
          modifier = Modifier
            .width(110.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DividerColor)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Copyright box with double-tap trigger
        val interactionSource = remember { MutableInteractionSource() }
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
              interactionSource = interactionSource,
              indication = null,
              onClick = onFooterDoubleTap
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("footer_copyright_section")
        ) {
          Text(
            text = "© 2023 ${appConfig.appTitle.ifEmpty { "FREE WARRIOR GIVEAWAY" }}",
            style = MaterialTheme.typography.labelMedium.copy(
              color = TextMuted,
              fontWeight = FontWeight.SemiBold,
              fontSize = 11.sp,
              letterSpacing = 1.sp,
              textAlign = TextAlign.Center
            )
          )
          Text(
            text = "All rights reserved. Double tap for admin login.",
            style = MaterialTheme.typography.labelSmall.copy(
              color = TextMuted.copy(alpha = 0.7f),
              fontSize = 10.sp,
              textAlign = TextAlign.Center
            )
          )
        }
      }
    }
  }
}


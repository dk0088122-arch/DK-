package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppConfig
import com.example.ui.ActiveClaim
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.DividerColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarriorBlue
import com.example.ui.theme.WarriorBlueBorder
import com.example.ui.theme.WarriorBlueContainer
import com.example.ui.theme.WarriorBlueDark
import com.example.ui.theme.WarriorBlueDeep
import com.example.ui.theme.WarriorDiamondGold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
  activeClaim: ActiveClaim?,
  appConfig: AppConfig = AppConfig(),
  onBackToHome: () -> Unit,
  modifier: Modifier = Modifier
) {
  val clipboardManager = LocalClipboardManager.current
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  var isCopied by remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()

  val code = activeClaim?.otpCode ?: "84920153"

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = appConfig.appTitle.ifEmpty { "FREE WARRIOR GIVEAWAY" },
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 17.sp,
                color = WarriorBlueDeep
              )
            )
            Text(
              text = "Verification Code & Bounty Token",
              style = MaterialTheme.typography.labelSmall.copy(
                color = TextSecondary,
                fontSize = 11.sp
              )
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onBackToHome,
            modifier = Modifier.testTag("btn_back_verification")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = WarriorBlue
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = BackgroundLight
        )
      )
    },
    containerColor = BackgroundLight,
    modifier = modifier
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(scrollState)
          .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Success Header Badge
        Box(
          modifier = Modifier
            .size(68.dp)
            .shadow(6.dp, CircleShape)
            .clip(CircleShape)
            .background(WarriorBlue),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "Verified Shield",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
          )
        }

        // Delivery Guarantee Prominent Banner
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(
            containerColor = Color.White
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, WarriorBlueBorder),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 440.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(50))
                  .background(WarriorBlueContainer)
                  .padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "BOUNTY STATUS: ISSUED",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = WarriorBlueDeep,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp
                  )
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.AccessTime,
                  contentDescription = "Time",
                  tint = WarriorBlue,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Within 1h - 1 Day",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = WarriorBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                  )
                )
              }
            }

            Text(
              text = "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                color = WarriorBlueDeep
              )
            )
          }
        }

        // Main Verification Code Card
        Card(
          shape = RoundedCornerShape(28.dp),
          colors = CardDefaults.cardColors(
            containerColor = WarriorBlueContainer
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, WarriorBlueBorder),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 440.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            // Lock and Secure Tag
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center,
              modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(WarriorBlue.copy(alpha = 0.12f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Read-only secured",
                tint = WarriorBlue,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "READ-ONLY ADMIN TOKEN",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = WarriorBlue,
                  letterSpacing = 1.sp
                )
              )
            }

            // 8 Digits in styled box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceLight)
                .border(1.5.dp, WarriorBlueBorder, RoundedCornerShape(16.dp))
                .padding(vertical = 18.dp, horizontal = 12.dp),
              contentAlignment = Alignment.Center
            ) {
              val formattedCode = if (code.length == 8) {
                "${code.substring(0, 4)}   ${code.substring(4, 8)}"
              } else {
                code
              }

              Text(
                text = formattedCode,
                style = MaterialTheme.typography.displayMedium.copy(
                  fontFamily = FontFamily.Monospace,
                  fontWeight = FontWeight.Black,
                  fontSize = 28.sp,
                  letterSpacing = 3.sp,
                  color = WarriorBlueDeep
                ),
                modifier = Modifier.testTag("verification_code_display")
              )
            }

            // Copy Code Button
            Button(
              onClick = {
                clipboardManager.setText(AnnotatedString(code))
                isCopied = true
                Toast.makeText(context, "Verification code copied to clipboard!", Toast.LENGTH_SHORT).show()
                coroutineScope.launch {
                  delay(2500)
                  isCopied = false
                }
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isCopied) SuccessGreen else WarriorBlue,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(50),
              modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("btn_copy_code")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Icon(
                  imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                  contentDescription = "Copy Icon",
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (isCopied) "CODE COPIED!" else "COPY CODE",
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                  )
                )
              }
            }
          }
        }

        // Submitted Player Details Summary Card
        if (activeClaim != null) {
          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
              containerColor = SurfaceLight
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
            modifier = Modifier
              .fillMaxWidth()
              .widthIn(max = 440.dp)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Text(
                text = "CLAIM SUMMARY",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = WarriorBlue,
                  letterSpacing = 1.sp
                )
              )

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("Player Name", style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary))
                Text(activeClaim.playerName, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = TextPrimary))
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("EUID (In-Game ID)", style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary))
                Text(activeClaim.euid, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = TextPrimary))
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("Account Level", style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary))
                Text("Level ${activeClaim.level}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = TextPrimary))
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text("Bounty Amount", style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary))
                Text("20,000 Diamonds", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = WarriorDiamondGold))
              }
            }
          }
        }

        // Disclaimer Note Section
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(
            containerColor = SurfaceLight
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 440.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Disclaimer",
                tint = WarriorBlue,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Important Disclaimer",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = WarriorBlueDeep
                )
              )
            }

            Text(
              text = "Disclaimer: This unique 8-digit verification code is generated directly by the admin server for your 20,000 Diamonds redemption. Please present this code or keep it safe. Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.",
              style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary,
                fontSize = 12.5.sp,
                lineHeight = 18.sp
              ),
              modifier = Modifier.testTag("disclaimer_note_text")
            )
          }
        }

        // Back to Home Button
        OutlinedButton(
          onClick = onBackToHome,
          shape = RoundedCornerShape(50),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 440.dp)
            .height(48.dp)
            .testTag("btn_back_home_verification")
        ) {
          Text(
            text = "BACK TO HOME",
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              color = WarriorBlue
            )
          )
        }

        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}

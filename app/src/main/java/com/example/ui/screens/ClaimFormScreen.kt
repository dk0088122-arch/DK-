package com.example.ui.screens

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppConfig
import com.example.ui.ClaimFormState
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.ErrorRed
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClaimFormScreen(
  formState: ClaimFormState,
  appConfig: AppConfig = AppConfig(),
  onPlayerNameChange: (String) -> Unit,
  onEuidChange: (String) -> Unit,
  onLevelChange: (String) -> Unit,
  onMobileChange: (String) -> Unit,
  onSubmit: () -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val focusManager = LocalFocusManager.current
  val scrollState = rememberScrollState()

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
                letterSpacing = 0.5.sp,
                color = WarriorBlueDeep
              )
            )
            Text(
              text = "Official Claim Verification Portal",
              style = MaterialTheme.typography.labelSmall.copy(
                color = TextSecondary,
                fontSize = 11.sp
              )
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("btn_back_form")
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
        // Prominent Delivery Promise Title Notice Card (MANDATORY REQUIREMENT)
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(
            containerColor = Color.White
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, WarriorBlueBorder),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 460.dp)
            .testTag("delivery_guarantee_card")
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            // Badge tag
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(50))
                  .background(WarriorBlue)
                  .padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified",
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "OFFICIAL BOUNTY GUARANTEE",
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = Color.White,
                      fontWeight = FontWeight.ExtraBold,
                      fontSize = 10.sp,
                      letterSpacing = 0.8.sp
                    )
                  )
                }
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.AccessTime,
                  contentDescription = "Time",
                  tint = WarriorBlue,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "1 Hour - 24 Hours",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = WarriorBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                  )
                )
              }
            }

            // PRIMARY TITLE AS REQUESTED BY USER
            Text(
              text = "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = WarriorBlueDeep
              ),
              modifier = Modifier.testTag("claim_delivery_title_notice")
            )

            // Two-Way Delivery Badges
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              // Option A: Redeem Code
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(14.dp))
                  .background(WarriorBlueContainer)
                  .border(1.dp, WarriorBlueBorder, RoundedCornerShape(14.dp))
                  .padding(horizontal = 10.dp, vertical = 8.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Diamond,
                    contentDescription = "Redeem Code",
                    tint = WarriorDiamondGold,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Column {
                    Text(
                      text = "Method 1",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = TextSecondary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                      )
                    )
                    Text(
                      text = "Instant Redeem Code",
                      style = MaterialTheme.typography.labelMedium.copy(
                        color = WarriorBlueDeep,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                      )
                    )
                  }
                }
              }

              // Option B: Mailbox Transfer
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(14.dp))
                  .background(WarriorBlueContainer)
                  .border(1.dp, WarriorBlueBorder, RoundedCornerShape(14.dp))
                  .padding(horizontal = 10.dp, vertical = 8.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.LocalPostOffice,
                    contentDescription = "Mailbox",
                    tint = WarriorBlue,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Column {
                    Text(
                      text = "Method 2",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = TextSecondary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                      )
                    )
                    Text(
                      text = "Direct In-Game Mail",
                      style = MaterialTheme.typography.labelMedium.copy(
                        color = WarriorBlueDeep,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                      )
                    )
                  }
                }
              }
            }
          }
        }

        // Form Fields Container
        Card(
          shape = RoundedCornerShape(28.dp),
          colors = CardDefaults.cardColors(
            containerColor = SurfaceLight
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, WarriorBlueBorder.copy(alpha = 0.6f)),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 460.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Text(
              text = "ENTER WARRIOR CREDENTIALS",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                letterSpacing = 1.sp,
                color = WarriorBlue
              )
            )

            // Player Name Field
            OutlinedTextField(
              value = formState.playerName,
              onValueChange = onPlayerNameChange,
              label = { Text("Player Name") },
              placeholder = { Text("e.g. ShadowWarrior99") },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Person,
                  contentDescription = "Player Name",
                  tint = WarriorBlue
                )
              },
              isError = formState.playerNameError != null,
              supportingText = formState.playerNameError?.let {
                { Text(it, color = ErrorRed) }
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
              ),
              keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
              ),
              shape = RoundedCornerShape(16.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WarriorBlue,
                unfocusedBorderColor = WarriorBlueBorder,
                focusedContainerColor = BackgroundLight,
                unfocusedContainerColor = BackgroundLight
              ),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_player_name")
            )

            // EUID Field
            OutlinedTextField(
              value = formState.euid,
              onValueChange = onEuidChange,
              label = { Text("EUID (In-Game ID)") },
              placeholder = { Text("e.g. 84920184") },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Badge,
                  contentDescription = "EUID",
                  tint = WarriorBlue
                )
              },
              isError = formState.euidError != null,
              supportingText = formState.euidError?.let {
                { Text(it, color = ErrorRed) }
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
              ),
              keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
              ),
              shape = RoundedCornerShape(16.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WarriorBlue,
                unfocusedBorderColor = WarriorBlueBorder,
                focusedContainerColor = BackgroundLight,
                unfocusedContainerColor = BackgroundLight
              ),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_euid")
            )

            // Level Field
            OutlinedTextField(
              value = formState.level,
              onValueChange = onLevelChange,
              label = { Text("Account Level") },
              placeholder = { Text("e.g. 50") },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.MilitaryTech,
                  contentDescription = "Level",
                  tint = WarriorBlue
                )
              },
              isError = formState.levelError != null,
              supportingText = formState.levelError?.let {
                { Text(it, color = ErrorRed) }
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
              ),
              keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
              ),
              shape = RoundedCornerShape(16.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WarriorBlue,
                unfocusedBorderColor = WarriorBlueBorder,
                focusedContainerColor = BackgroundLight,
                unfocusedContainerColor = BackgroundLight
              ),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_level")
            )

            // Mobile Number Field
            OutlinedTextField(
              value = formState.mobileNumber,
              onValueChange = onMobileChange,
              label = { Text("Mobile Number") },
              placeholder = { Text("e.g. +1 555-0199") },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Phone,
                  contentDescription = "Mobile Number",
                  tint = WarriorBlue
                )
              },
              isError = formState.mobileError != null,
              supportingText = formState.mobileError?.let {
                { Text(it, color = ErrorRed) }
              },
              singleLine = true,
              keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
              ),
              keyboardActions = KeyboardActions(
                onDone = {
                  focusManager.clearFocus()
                  onSubmit()
                }
              ),
              shape = RoundedCornerShape(16.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WarriorBlue,
                unfocusedBorderColor = WarriorBlueBorder,
                focusedContainerColor = BackgroundLight,
                unfocusedContainerColor = BackgroundLight
              ),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("input_mobile_number")
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Submit Button
            Button(
              onClick = onSubmit,
              enabled = !formState.isSubmitting,
              colors = ButtonDefaults.buttonColors(
                containerColor = WarriorBlue,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(50),
              elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("btn_submit_form")
            ) {
              if (formState.isSubmitting) {
                CircularProgressIndicator(
                  color = Color.White,
                  modifier = Modifier.size(24.dp),
                  strokeWidth = 2.5.dp
                )
              } else {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = "SUBMIT & GET CODE",
                    style = MaterialTheme.typography.labelLarge.copy(
                      fontWeight = FontWeight.Bold,
                      fontSize = 15.sp,
                      letterSpacing = 1.sp
                    )
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Submit",
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            }
          }
        }

        // Security Assurance Note
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 460.dp)
            .padding(horizontal = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = "Encrypted",
            tint = TextMuted,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Direct server verification • 256-bit Secure Handshake",
            style = MaterialTheme.typography.labelSmall.copy(
              color = TextMuted,
              fontSize = 11.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}


package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.SurfaceLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarriorBlue
import com.example.ui.theme.WarriorBlueBorder
import com.example.ui.theme.WarriorBlueContainer
import com.example.ui.theme.WarriorBlueDeep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(
  onLoginSuccess: () -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var username by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var passwordVisible by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  val focusManager = LocalFocusManager.current
  val scrollState = rememberScrollState()

  fun performLogin() {
    val cleanUsername = username.trim()
    val cleanPassword = password.trim()

    if (cleanUsername == "dkadmin" && cleanPassword == "Admin@123") {
      errorMessage = null
      onLoginSuccess()
    } else {
      errorMessage = "Invalid credentials. Required: dkadmin / Admin@123"
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "ADMIN ACCESS",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              fontSize = 18.sp,
              color = WarriorBlueDeep
            )
          )
        },
        navigationIcon = {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("btn_back_admin_login")
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
          .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        // Admin Badge
        Box(
          modifier = Modifier
            .size(80.dp)
            .shadow(6.dp, CircleShape)
            .clip(CircleShape)
            .background(WarriorBlueDeep),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.AdminPanelSettings,
            contentDescription = "Admin Security Badge",
            tint = Color.White,
            modifier = Modifier.size(44.dp)
          )
        }

        // Title and Instructions
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(
            text = "Administrator Login",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Black,
              color = WarriorBlueDeep,
              textAlign = TextAlign.Center
            )
          )
          Text(
            text = "Authorized personnel only. Access overview, OTP approval queue, and media upload.",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = TextSecondary,
              fontSize = 13.sp,
              textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }

        // Login Card
        Card(
          shape = RoundedCornerShape(28.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceLight),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, WarriorBlueBorder),
          modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 420.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            // Username Field
            OutlinedTextField(
              value = username,
              onValueChange = {
                username = it
                errorMessage = null
              },
              label = { Text("Username") },
              placeholder = { Text("dkadmin") },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Person,
                  contentDescription = "Username",
                  tint = WarriorBlue
                )
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
                .testTag("admin_username_input")
            )

            // Password Field
            OutlinedTextField(
              value = password,
              onValueChange = {
                password = it
                errorMessage = null
              },
              label = { Text("Password") },
              placeholder = { Text("Admin@123") },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Lock,
                  contentDescription = "Password",
                  tint = WarriorBlue
                )
              },
              trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                  Icon(
                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = TextMuted
                  )
                }
              },
              visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
              singleLine = true,
              keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
              ),
              keyboardActions = KeyboardActions(
                onDone = {
                  focusManager.clearFocus()
                  performLogin()
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
                .testTag("admin_password_input")
            )

            // Error Text
            if (errorMessage != null) {
              Text(
                text = errorMessage!!,
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = ErrorRed,
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 13.sp
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Login Button
            Button(
              onClick = {
                focusManager.clearFocus()
                performLogin()
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = WarriorBlue,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(50),
              modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_admin_login")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Key,
                  contentDescription = "Login",
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "SIGN IN AS ADMIN",
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    letterSpacing = 1.sp
                  )
                )
              }
            }

            // Quick Auto-Fill helper for smooth review / testing
            TextButton(
              onClick = {
                username = "dkadmin"
                password = "Admin@123"
                errorMessage = null
              },
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "Auto-fill demo credentials (dkadmin / Admin@123)",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = WarriorBlue,
                  fontSize = 11.sp
                )
              )
            }
          }
        }
      }
    }
  }
}

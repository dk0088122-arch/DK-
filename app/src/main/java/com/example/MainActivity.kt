package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.AppScreen
import com.example.ui.GiveawayViewModel
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AdminLoginScreen
import com.example.ui.screens.ClaimFormScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.VerificationScreen
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  private val viewModel: GiveawayViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = BackgroundLight
        ) {
          WarriorGiveawayApp(viewModel = viewModel)
        }
      }
    }
  }
}

@Composable
fun WarriorGiveawayApp(
  viewModel: GiveawayViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
  val adminTab by viewModel.adminTab.collectAsStateWithLifecycle()
  val claimFormState by viewModel.claimForm.collectAsStateWithLifecycle()
  val activeClaim by viewModel.activeClaim.collectAsStateWithLifecycle()
  val submissions by viewModel.submissions.collectAsStateWithLifecycle()
  val mediaList by viewModel.mediaList.collectAsStateWithLifecycle()
  val appConfig by viewModel.appConfig.collectAsStateWithLifecycle()

  // Collect Toast notifications
  LaunchedEffect(Unit) {
    viewModel.toastMessage.collect { message ->
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
  }

  // Handle system back navigation
  BackHandler(enabled = currentScreen != AppScreen.HOME) {
    when (currentScreen) {
      AppScreen.CLAIM_FORM -> viewModel.navigateTo(AppScreen.HOME)
      AppScreen.VERIFICATION -> viewModel.navigateTo(AppScreen.HOME)
      AppScreen.ADMIN_LOGIN -> viewModel.navigateTo(AppScreen.HOME)
      AppScreen.ADMIN_DASHBOARD -> viewModel.navigateTo(AppScreen.HOME)
      AppScreen.HOME -> Unit
    }
  }

  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = { fadeIn() togetherWith fadeOut() },
    label = "ScreenTransition",
    modifier = modifier.fillMaxSize()
  ) { screen ->
    when (screen) {
      AppScreen.HOME -> {
        HomeScreen(
          appConfig = appConfig,
          mediaList = mediaList,
          onGetLinkClicked = {
            viewModel.navigateTo(AppScreen.CLAIM_FORM)
          },
          onFooterDoubleTap = {
            viewModel.onFooterClicked()
          }
        )
      }

      AppScreen.CLAIM_FORM -> {
        ClaimFormScreen(
          formState = claimFormState,
          appConfig = appConfig,
          onPlayerNameChange = viewModel::onPlayerNameChanged,
          onEuidChange = viewModel::onEuidChanged,
          onLevelChange = viewModel::onLevelChanged,
          onMobileChange = viewModel::onMobileNumberChanged,
          onSubmit = viewModel::submitClaimForm,
          onBack = {
            viewModel.navigateTo(AppScreen.HOME)
          }
        )
      }

      AppScreen.VERIFICATION -> {
        VerificationScreen(
          activeClaim = activeClaim,
          appConfig = appConfig,
          onBackToHome = {
            viewModel.navigateTo(AppScreen.HOME)
          }
        )
      }

      AppScreen.ADMIN_LOGIN -> {
        AdminLoginScreen(
          onLoginSuccess = {
            viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD)
          },
          onBack = {
            viewModel.navigateTo(AppScreen.HOME)
          }
        )
      }

      AppScreen.ADMIN_DASHBOARD -> {
        AdminDashboardScreen(
          selectedTab = adminTab,
          onTabSelected = viewModel::setAdminTab,
          submissions = submissions,
          mediaList = mediaList,
          appConfig = appConfig,
          onUpdateStatus = viewModel::updateSubmissionStatus,
          onRegenerateOtp = viewModel::regenerateOtp,
          onDeleteSubmission = viewModel::deleteSubmission,
          onClearAllSubmissions = viewModel::deleteAllSubmissions,
          onAddMedia = viewModel::addMedia,
          onDeleteMedia = viewModel::deleteMedia,
          onClearAllMedia = viewModel::deleteAllMedia,
          onUpdateAppConfig = viewModel::updateAppConfig,
          onLogout = {
            viewModel.navigateTo(AppScreen.HOME)
          }
        )
      }
    }
  }
}


package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AdminMedia
import com.example.data.AppConfig
import com.example.data.AppDatabase
import com.example.data.GiveawayRepository
import com.example.data.GiveawaySubmission
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen {
  HOME,
  CLAIM_FORM,
  VERIFICATION,
  ADMIN_LOGIN,
  ADMIN_DASHBOARD
}

enum class AdminTab {
  OVERVIEW,
  OTP_APPROVAL,
  BANNER_CUSTOMIZE,
  MEDIA_UPLOAD
}

data class ClaimFormState(
  val playerName: String = "",
  val euid: String = "",
  val level: String = "",
  val mobileNumber: String = "",
  val playerNameError: String? = null,
  val euidError: String? = null,
  val levelError: String? = null,
  val mobileError: String? = null,
  val isSubmitting: Boolean = false
)

data class ActiveClaim(
  val submissionId: Long = 0,
  val playerName: String = "",
  val euid: String = "",
  val level: String = "",
  val mobileNumber: String = "",
  val otpCode: String = "",
  val status: String = "Pending"
)

class GiveawayViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: GiveawayRepository

  init {
    val database = AppDatabase.getDatabase(application, viewModelScope)
    repository = GiveawayRepository(database.giveawayDao())
  }

  val submissions: StateFlow<List<GiveawaySubmission>> =
    repository.allSubmissions.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  val mediaList: StateFlow<List<AdminMedia>> =
    repository.allMedia.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList()
    )

  val appConfig: StateFlow<AppConfig> =
    repository.appConfig
      .map { it ?: AppConfig() }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppConfig()
      )

  private val _currentScreen = MutableStateFlow(AppScreen.HOME)
  val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

  private val _adminTab = MutableStateFlow(AdminTab.OVERVIEW)
  val adminTab: StateFlow<AdminTab> = _adminTab.asStateFlow()

  private val _claimForm = MutableStateFlow(ClaimFormState())
  val claimForm: StateFlow<ClaimFormState> = _claimForm.asStateFlow()

  private val _activeClaim = MutableStateFlow<ActiveClaim?>(null)
  val activeClaim: StateFlow<ActiveClaim?> = _activeClaim.asStateFlow()

  private val _toastMessage = MutableSharedFlow<String>()
  val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

  // Footer double-tap tracker
  private var lastFooterClickTime: Long = 0L

  fun navigateTo(screen: AppScreen) {
    _currentScreen.value = screen
  }

  fun setAdminTab(tab: AdminTab) {
    _adminTab.value = tab
  }

  fun onFooterClicked() {
    val now = System.currentTimeMillis()
    if (now - lastFooterClickTime < 600) {
      // Double tap detected! Open Admin Login
      _currentScreen.value = AppScreen.ADMIN_LOGIN
      lastFooterClickTime = 0L
    } else {
      lastFooterClickTime = now
    }
  }

  fun onPlayerNameChanged(name: String) {
    _claimForm.update { it.copy(playerName = name, playerNameError = null) }
  }

  fun onEuidChanged(euid: String) {
    _claimForm.update { it.copy(euid = euid, euidError = null) }
  }

  fun onLevelChanged(level: String) {
    _claimForm.update { it.copy(level = level, levelError = null) }
  }

  fun onMobileNumberChanged(mobile: String) {
    _claimForm.update { it.copy(mobileNumber = mobile, mobileError = null) }
  }

  fun submitClaimForm() {
    val state = _claimForm.value
    var hasError = false

    var pNameError: String? = null
    var euidErr: String? = null
    var lvlErr: String? = null
    var mobErr: String? = null

    if (state.playerName.trim().isEmpty()) {
      pNameError = "Please enter your player name"
      hasError = true
    }
    if (state.euid.trim().isEmpty()) {
      euidErr = "Please enter your EUID (Game ID)"
      hasError = true
    }
    if (state.level.trim().isEmpty()) {
      lvlErr = "Please enter your level"
      hasError = true
    }
    if (state.mobileNumber.trim().isEmpty() || state.mobileNumber.trim().length < 6) {
      mobErr = "Please enter a valid mobile number"
      hasError = true
    }

    if (hasError) {
      _claimForm.update {
        it.copy(
          playerNameError = pNameError,
          euidError = euidErr,
          levelError = lvlErr,
          mobileError = mobErr
        )
      }
      return
    }

    _claimForm.update { it.copy(isSubmitting = true) }

    viewModelScope.launch {
      // Generate 8-digit verification code
      val randomCode = String.format("%08d", Random.nextInt(10000000, 99999999))

      val newSubmission = GiveawaySubmission(
        playerName = state.playerName.trim(),
        euid = state.euid.trim(),
        level = state.level.trim(),
        mobileNumber = state.mobileNumber.trim(),
        otpCode = randomCode,
        status = "Pending"
      )

      val generatedId = repository.insertSubmission(newSubmission)

      _activeClaim.value = ActiveClaim(
        submissionId = generatedId,
        playerName = newSubmission.playerName,
        euid = newSubmission.euid,
        level = newSubmission.level,
        mobileNumber = newSubmission.mobileNumber,
        otpCode = newSubmission.otpCode,
        status = "Pending"
      )

      _claimForm.value = ClaimFormState() // Reset form
      _currentScreen.value = AppScreen.VERIFICATION
    }
  }

  // Admin Actions
  fun updateSubmissionStatus(submission: GiveawaySubmission, newStatus: String) {
    viewModelScope.launch {
      val updated = submission.copy(status = newStatus)
      repository.updateSubmission(updated)
      _toastMessage.emit("Submission marked as $newStatus")
    }
  }

  fun regenerateOtp(submission: GiveawaySubmission) {
    viewModelScope.launch {
      val newCode = String.format("%08d", Random.nextInt(10000000, 99999999))
      val updated = submission.copy(otpCode = newCode)
      repository.updateSubmission(updated)
      _toastMessage.emit("New OTP code generated: $newCode")
    }
  }

  fun deleteSubmission(submission: GiveawaySubmission) {
    viewModelScope.launch {
      repository.deleteSubmission(submission)
      _toastMessage.emit("Submission for ${submission.playerName} deleted")
    }
  }

  fun deleteAllSubmissions() {
    viewModelScope.launch {
      repository.deleteAllSubmissions()
      _toastMessage.emit("All submissions cleared")
    }
  }

  fun addMedia(
    title: String,
    category: String,
    description: String,
    iconType: String,
    colorHex: Long,
    imageUrl: String = "",
    badgeText: String = "VERIFIED"
  ) {
    viewModelScope.launch {
      val media = AdminMedia(
        title = title,
        category = category,
        description = description,
        iconType = iconType,
        accentColorHex = colorHex,
        imageUrl = imageUrl,
        badgeText = badgeText
      )
      repository.insertMedia(media)
      _toastMessage.emit("Media '$title' uploaded successfully")
    }
  }

  fun deleteMedia(media: AdminMedia) {
    viewModelScope.launch {
      repository.deleteMedia(media)
      _toastMessage.emit("Media '${media.title}' deleted")
    }
  }

  fun deleteAllMedia() {
    viewModelScope.launch {
      repository.deleteAllMedia()
      _toastMessage.emit("All media cleared")
    }
  }

  fun updateAppConfig(config: AppConfig) {
    viewModelScope.launch {
      repository.updateAppConfig(config)
      _toastMessage.emit("App banner & title updated successfully!")
    }
  }
}


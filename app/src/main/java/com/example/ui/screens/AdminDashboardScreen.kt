package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.AdminMedia
import com.example.data.AppConfig
import com.example.data.GiveawaySubmission
import com.example.ui.AdminTab
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.DividerColor
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.SuccessContainer
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
import com.example.ui.theme.WarriorDiamondCyan
import com.example.ui.theme.WarriorDiamondGold
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
  selectedTab: AdminTab,
  onTabSelected: (AdminTab) -> Unit,
  submissions: List<GiveawaySubmission>,
  mediaList: List<AdminMedia>,
  appConfig: AppConfig = AppConfig(),
  onUpdateStatus: (GiveawaySubmission, String) -> Unit,
  onRegenerateOtp: (GiveawaySubmission) -> Unit,
  onDeleteSubmission: (GiveawaySubmission) -> Unit,
  onClearAllSubmissions: () -> Unit,
  onAddMedia: (String, String, String, String, Long) -> Unit,
  onDeleteMedia: (AdminMedia) -> Unit,
  onClearAllMedia: () -> Unit,
  onUpdateAppConfig: (AppConfig) -> Unit = {},
  onLogout: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showAddMediaDialog by remember { mutableStateOf(false) }
  var showClearSubmissionsDialog by remember { mutableStateOf(false) }
  var showClearMediaDialog by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(WarriorBlueContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = "Admin Shield",
                tint = WarriorBlue,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "WARRIOR ADMIN",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Black,
                  fontSize = 16.sp,
                  color = WarriorBlueDeep
                )
              )
              Text(
                text = "Logged in as dkadmin",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = TextMuted,
                  fontSize = 10.sp
                )
              )
            }
          }
        },
        actions = {
          IconButton(
            onClick = onLogout,
            modifier = Modifier.testTag("btn_admin_logout")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ExitToApp,
              contentDescription = "Logout",
              tint = ErrorRed
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
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      // Tab Bar
      PrimaryTabRow(
        selectedTabIndex = selectedTab.ordinal,
        containerColor = BackgroundLight,
        contentColor = WarriorBlue
      ) {
        Tab(
          selected = selectedTab == AdminTab.OVERVIEW,
          onClick = { onTabSelected(AdminTab.OVERVIEW) },
          text = {
            Text(
              text = "Overview",
              fontWeight = if (selectedTab == AdminTab.OVERVIEW) FontWeight.Bold else FontWeight.Normal,
              fontSize = 13.sp
            )
          },
          modifier = Modifier.testTag("tab_overview")
        )
        Tab(
          selected = selectedTab == AdminTab.OTP_APPROVAL,
          onClick = { onTabSelected(AdminTab.OTP_APPROVAL) },
          text = {
            Text(
              text = "OTP Approval",
              fontWeight = if (selectedTab == AdminTab.OTP_APPROVAL) FontWeight.Bold else FontWeight.Normal,
              fontSize = 12.sp
            )
          },
          modifier = Modifier.testTag("tab_otp_approval")
        )
        Tab(
          selected = selectedTab == AdminTab.BANNER_CUSTOMIZE,
          onClick = { onTabSelected(AdminTab.BANNER_CUSTOMIZE) },
          text = {
            Text(
              text = "Banner & Title",
              fontWeight = if (selectedTab == AdminTab.BANNER_CUSTOMIZE) FontWeight.Bold else FontWeight.Normal,
              fontSize = 12.sp
            )
          },
          modifier = Modifier.testTag("tab_banner_customize")
        )
        Tab(
          selected = selectedTab == AdminTab.MEDIA_UPLOAD,
          onClick = { onTabSelected(AdminTab.MEDIA_UPLOAD) },
          text = {
            Text(
              text = "Media Upload",
              fontWeight = if (selectedTab == AdminTab.MEDIA_UPLOAD) FontWeight.Bold else FontWeight.Normal,
              fontSize = 12.sp
            )
          },
          modifier = Modifier.testTag("tab_media_upload")
        )
      }

      // Tab Content
      when (selectedTab) {
        AdminTab.OVERVIEW -> {
          OverviewSection(
            submissions = submissions,
            mediaCount = mediaList.size,
            onDeleteSubmission = onDeleteSubmission,
            onClearAll = { showClearSubmissionsDialog = true }
          )
        }

        AdminTab.OTP_APPROVAL -> {
          OtpApprovalSection(
            submissions = submissions,
            onUpdateStatus = onUpdateStatus,
            onRegenerateOtp = onRegenerateOtp,
            onDeleteSubmission = onDeleteSubmission
          )
        }

        AdminTab.BANNER_CUSTOMIZE -> {
          BannerCustomizeSection(
            appConfig = appConfig,
            onSaveConfig = onUpdateAppConfig
          )
        }

        AdminTab.MEDIA_UPLOAD -> {
          MediaUploadSection(
            mediaList = mediaList,
            onAddMediaClick = { showAddMediaDialog = true },
            onDeleteMedia = onDeleteMedia,
            onClearAll = { showClearMediaDialog = true }
          )
        }
      }
    }
  }

  // Add Media Dialog
  if (showAddMediaDialog) {
    AddMediaDialog(
      onDismiss = { showAddMediaDialog = false },
      onConfirm = { title, category, description, iconType, colorHex ->
        onAddMedia(title, category, description, iconType, colorHex)
        showAddMediaDialog = false
      }
    )
  }

  // Clear Submissions Confirmation Dialog
  if (showClearSubmissionsDialog) {
    AlertDialog(
      onDismissRequest = { showClearSubmissionsDialog = false },
      title = { Text("Clear All Submissions?") },
      text = { Text("This will delete all giveaway claims and OTP records permanently from the database.") },
      confirmButton = {
        Button(
          onClick = {
            onClearAllSubmissions()
            showClearSubmissionsDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
        ) {
          Text("Delete All")
        }
      },
      dismissButton = {
        TextButton(onClick = { showClearSubmissionsDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }

  // Clear Media Confirmation Dialog
  if (showClearMediaDialog) {
    AlertDialog(
      onDismissRequest = { showClearMediaDialog = false },
      title = { Text("Clear All Media?") },
      text = { Text("This will delete all uploaded banners and announcements permanently.") },
      confirmButton = {
        Button(
          onClick = {
            onClearAllMedia()
            showClearMediaDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
        ) {
          Text("Delete All")
        }
      },
      dismissButton = {
        TextButton(onClick = { showClearMediaDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }
}

// ----------------------------------------------------
// OVERVIEW SECTION
// ----------------------------------------------------
@Composable
private fun OverviewSection(
  submissions: List<GiveawaySubmission>,
  mediaCount: Int,
  onDeleteSubmission: (GiveawaySubmission) -> Unit,
  onClearAll: () -> Unit
) {
  val pendingCount = submissions.count { it.status == "Pending" }
  val approvedCount = submissions.count { it.status == "Approved" }
  val totalDiamondsAllocated = (approvedCount * 20000)

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = "System Metrics & Live Status",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = WarriorBlueDeep
        )
      )
    }

    // Metric Cards Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        MetricCard(
          title = "Total Claims",
          value = "${submissions.size}",
          icon = Icons.Default.MilitaryTech,
          accentColor = WarriorBlue,
          modifier = Modifier.weight(1f)
        )
        MetricCard(
          title = "Pending OTP",
          value = "$pendingCount",
          icon = Icons.Default.HourglassTop,
          accentColor = WarriorDiamondGold,
          modifier = Modifier.weight(1f)
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        MetricCard(
          title = "Approved Claims",
          value = "$approvedCount",
          icon = Icons.Default.TaskAlt,
          accentColor = SuccessGreen,
          modifier = Modifier.weight(1f)
        )
        MetricCard(
          title = "Diamonds Sent",
          value = "${String.format("%,d", totalDiamondsAllocated)}",
          icon = Icons.Default.Diamond,
          accentColor = WarriorDiamondCyan,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Recent Submissions Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Recent Giveaway Submissions",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = WarriorBlueDeep
          )
        )
        if (submissions.isNotEmpty()) {
          TextButton(
            onClick = onClearAll,
            colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed)
          ) {
            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear All", modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Clear All", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    if (submissions.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceLight),
          border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "No giveaway claims recorded yet.",
              style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted)
            )
          }
        }
      }
    } else {
      items(submissions, key = { it.id }) { item ->
        SubmissionOverviewCard(
          submission = item,
          onDelete = { onDeleteSubmission(item) }
        )
      }
    }
  }
}

@Composable
private fun MetricCard(
  title: String,
  value: String,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, WarriorBlueBorder.copy(alpha = 0.5f)),
    modifier = modifier
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelSmall.copy(
            color = TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
          )
        )
        Box(
          modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(accentColor.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = title,
            tint = accentColor,
            modifier = Modifier.size(16.dp)
          )
        }
      }
      Text(
        text = value,
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Black,
          fontSize = 20.sp,
          color = WarriorBlueDeep
        )
      )
    }
  }
}

@Composable
private fun SubmissionOverviewCard(
  submission: GiveawaySubmission,
  onDelete: () -> Unit
) {
  val dateFormat = remember { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()) }
  val formattedDate = remember(submission.createdAt) { dateFormat.format(Date(submission.createdAt)) }

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
    border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(3.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = submission.playerName,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = WarriorBlueDeep
            )
          )
          Spacer(modifier = Modifier.width(8.dp))
          // Status Chip
          val (chipBg, chipText) = when (submission.status) {
            "Approved" -> SuccessContainer to SuccessGreen
            "Rejected" -> ErrorContainer to ErrorRed
            else -> WarriorBlueContainer to WarriorBlue
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(chipBg)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = submission.status,
              style = MaterialTheme.typography.labelSmall.copy(
                color = chipText,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              )
            )
          }
        }

        Text(
          text = "EUID: ${submission.euid} • Lvl: ${submission.level} • ${submission.mobileNumber}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextSecondary,
            fontSize = 12.sp
          )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "OTP: ",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
          )
          Text(
            text = submission.otpCode,
            style = MaterialTheme.typography.bodySmall.copy(
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              color = WarriorBlue,
              fontSize = 12.sp
            )
          )
          Text(
            text = " • $formattedDate",
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
          )
        }
      }

      // Delete button
      IconButton(
        onClick = onDelete,
        modifier = Modifier.testTag("delete_submission_overview_${submission.id}")
      ) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = "Delete Submission",
          tint = ErrorRed.copy(alpha = 0.8f)
        )
      }
    }
  }
}

// ----------------------------------------------------
// OTP APPROVAL SECTION
// ----------------------------------------------------
@Composable
private fun OtpApprovalSection(
  submissions: List<GiveawaySubmission>,
  onUpdateStatus: (GiveawaySubmission, String) -> Unit,
  onRegenerateOtp: (GiveawaySubmission) -> Unit,
  onDeleteSubmission: (GiveawaySubmission) -> Unit
) {
  var filterStatus by remember { mutableStateOf("All") }
  var searchQuery by remember { mutableStateOf("") }

  val filteredSubmissions = remember(submissions, filterStatus, searchQuery) {
    submissions.filter { item ->
      val matchesFilter = when (filterStatus) {
        "Pending" -> item.status == "Pending"
        "Approved" -> item.status == "Approved"
        "Rejected" -> item.status == "Rejected"
        else -> true
      }
      val matchesSearch = if (searchQuery.isBlank()) true else {
        item.playerName.contains(searchQuery, ignoreCase = true) ||
          item.euid.contains(searchQuery, ignoreCase = true) ||
          item.mobileNumber.contains(searchQuery, ignoreCase = true) ||
          item.otpCode.contains(searchQuery, ignoreCase = true)
      }
      matchesFilter && matchesSearch
    }
  }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Search Bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search by Player Name, EUID, OTP, or Mobile...") },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Search", tint = WarriorBlue)
        },
        trailingIcon = if (searchQuery.isNotEmpty()) {
          {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Default.Close, contentDescription = "Clear Search", tint = TextMuted)
            }
          }
        } else null,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = WarriorBlue,
          unfocusedBorderColor = WarriorBlueBorder,
          focusedContainerColor = SurfaceLight,
          unfocusedContainerColor = SurfaceLight
        ),
        modifier = Modifier.fillMaxWidth()
      )
    }

    // Filter Chips
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf("All", "Pending", "Approved", "Rejected").forEach { status ->
          FilterChip(
            selected = filterStatus == status,
            onClick = { filterStatus = status },
            label = {
              Text(
                text = status,
                fontWeight = if (filterStatus == status) FontWeight.Bold else FontWeight.Normal,
                fontSize = 12.sp
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = WarriorBlueContainer,
              selectedLabelColor = WarriorBlueDeep
            )
          )
        }
      }
    }

    // List of Submissions
    if (filteredSubmissions.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceLight),
          border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(36.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "No submissions matching '$filterStatus' filter.",
              style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted)
            )
          }
        }
      }
    } else {
      items(filteredSubmissions, key = { it.id }) { submission ->
        OtpApprovalCard(
          submission = submission,
          onApprove = { onUpdateStatus(submission, "Approved") },
          onReject = { onUpdateStatus(submission, "Rejected") },
          onRegenerate = { onRegenerateOtp(submission) },
          onDelete = { onDeleteSubmission(submission) }
        )
      }
    }
  }
}

@Composable
private fun OtpApprovalCard(
  submission: GiveawaySubmission,
  onApprove: () -> Unit,
  onReject: () -> Unit,
  onRegenerate: () -> Unit,
  onDelete: () -> Unit
) {
  val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()) }
  val dateStr = remember(submission.createdAt) { dateFormat.format(Date(submission.createdAt)) }

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, WarriorBlueBorder.copy(alpha = 0.6f)),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Header: Player Name + Delete Icon
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = submission.playerName,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              fontSize = 16.sp,
              color = WarriorBlueDeep
            )
          )
          Text(
            text = "Submitted: $dateStr",
            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 11.sp)
          )
        }

        // Delete button
        IconButton(
          onClick = onDelete,
          modifier = Modifier.testTag("delete_otp_item_${submission.id}")
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Submission",
            tint = ErrorRed
          )
        }
      }

      HorizontalDivider(color = DividerColor)

      // Details Grid
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text("EUID / Game ID", style = MaterialTheme.typography.labelSmall.copy(color = TextMuted))
          Text(submission.euid, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimary))
        }
        Column {
          Text("Level", style = MaterialTheme.typography.labelSmall.copy(color = TextMuted))
          Text("Lvl ${submission.level}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimary))
        }
        Column {
          Text("Mobile", style = MaterialTheme.typography.labelSmall.copy(color = TextMuted))
          Text(submission.mobileNumber, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimary))
        }
      }

      // 8-Digit OTP Box & Status
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(WarriorBlueContainer)
          .border(1.dp, WarriorBlueBorder, RoundedCornerShape(12.dp))
          .padding(horizontal = 14.dp, vertical = 10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "8-DIGIT OTP VERIFICATION CODE",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = WarriorBlueDark,
                fontSize = 9.5.sp,
                letterSpacing = 0.5.sp
              )
            )
            Text(
              text = submission.otpCode,
              style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                color = WarriorBlueDeep,
                letterSpacing = 2.sp
              )
            )
          }

          // Status Badge
          val (badgeBg, badgeTextColor) = when (submission.status) {
            "Approved" -> SuccessContainer to SuccessGreen
            "Rejected" -> ErrorContainer to ErrorRed
            else -> Color(0xFFFFF3CD) to Color(0xFF856404)
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(badgeBg)
              .padding(horizontal = 10.dp, vertical = 5.dp)
          ) {
            Text(
              text = submission.status.uppercase(),
              style = MaterialTheme.typography.labelSmall.copy(
                color = badgeTextColor,
                fontWeight = FontWeight.Black,
                fontSize = 11.sp
              )
            )
          }
        }
      }

      // Actions Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Approve Button
        Button(
          onClick = onApprove,
          colors = ButtonDefaults.buttonColors(
            containerColor = SuccessGreen,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(50),
          modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .testTag("btn_approve_otp_${submission.id}")
        ) {
          Icon(Icons.Default.Check, contentDescription = "Approve", modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Approve", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // Reject Button
        OutlinedButton(
          onClick = onReject,
          colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
          border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed),
          shape = RoundedCornerShape(50),
          modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .testTag("btn_reject_otp_${submission.id}")
        ) {
          Icon(Icons.Default.Close, contentDescription = "Reject", modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Reject", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // Regenerate OTP Button
        IconButton(
          onClick = onRegenerate,
          modifier = Modifier.testTag("btn_regen_otp_${submission.id}")
        ) {
          Icon(
            imageVector = Icons.Default.Autorenew,
            contentDescription = "Regenerate OTP Code",
            tint = WarriorBlue
          )
        }
      }
    }
  }
}

// ----------------------------------------------------
// MEDIA UPLOAD SECTION
// ----------------------------------------------------
@Composable
private fun MediaUploadSection(
  mediaList: List<AdminMedia>,
  onAddMediaClick: () -> Unit,
  onDeleteMedia: (AdminMedia) -> Unit,
  onClearAll: () -> Unit
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Action Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Promotional Media & Banners",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              color = WarriorBlueDeep
            )
          )
          Text(
            text = "Manage giveaway assets, proofs, and banners",
            style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary)
          )
        }

        Button(
          onClick = onAddMediaClick,
          colors = ButtonDefaults.buttonColors(containerColor = WarriorBlue),
          shape = RoundedCornerShape(50),
          modifier = Modifier.testTag("btn_upload_media_dialog")
        ) {
          Icon(Icons.Default.Add, contentDescription = "Upload", modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Upload", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    if (mediaList.isNotEmpty()) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          TextButton(
            onClick = onClearAll,
            colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed)
          ) {
            Icon(Icons.Default.DeleteSweep, contentDescription = "Delete All Media", modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Clear All Media", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    if (mediaList.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceLight),
          border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.AddPhotoAlternate,
              contentDescription = "No media",
              tint = TextMuted,
              modifier = Modifier.size(44.dp)
            )
            Text(
              text = "No promotional media uploaded yet.",
              style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted)
            )
            Button(
              onClick = onAddMediaClick,
              colors = ButtonDefaults.buttonColors(containerColor = WarriorBlue),
              shape = RoundedCornerShape(50)
            ) {
              Text("Upload First Asset")
            }
          }
        }
      }
    } else {
      items(mediaList, key = { it.id }) { media ->
        MediaItemCard(
          media = media,
          onDelete = { onDeleteMedia(media) }
        )
      }
    }
  }
}

@Composable
private fun MediaItemCard(
  media: AdminMedia,
  onDelete: () -> Unit
) {
  val iconVector = when (media.iconType) {
    "diamond" -> Icons.Default.Diamond
    "trophy" -> Icons.Default.EmojiEvents
    "shield" -> Icons.Default.Shield
    "announcement" -> Icons.Default.Campaign
    else -> Icons.Default.Image
  }

  val accentColor = Color(media.accentColorHex)
  val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
  val dateStr = remember(media.createdAt) { dateFormat.format(Date(media.createdAt)) }

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Header Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(accentColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = iconVector,
              contentDescription = media.title,
              tint = accentColor,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            // Category Badge
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(WarriorBlueContainer)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = media.category.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 9.5.sp,
                  color = WarriorBlueDeep
                )
              )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = media.title,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = WarriorBlueDeep
              ),
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }

        // Delete button
        IconButton(
          onClick = onDelete,
          modifier = Modifier.testTag("delete_media_${media.id}")
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Media",
            tint = ErrorRed
          )
        }
      }

      Text(
        text = media.description,
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextSecondary,
          fontSize = 12.5.sp,
          lineHeight = 17.sp
        )
      )

      HorizontalDivider(color = DividerColor)

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Uploaded: $dateStr",
          style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 11.sp)
        )
        Text(
          text = "Active in App Feed",
          style = MaterialTheme.typography.labelSmall.copy(
            color = SuccessGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          )
        )
      }
    }
  }
}

// ----------------------------------------------------
// ADD MEDIA DIALOG
// ----------------------------------------------------
@Composable
private fun AddMediaDialog(
  onDismiss: () -> Unit,
  onConfirm: (title: String, category: String, description: String, iconType: String, colorHex: Long) -> Unit
) {
  var title by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Banner") }
  var description by remember { mutableStateOf("") }
  var iconType by remember { mutableStateOf("diamond") }
  var selectedColorHex by remember { mutableStateOf(0xFF0061A4) }
  var titleError by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "Upload Giveaway Media",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
      )
    },
    text = {
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        OutlinedTextField(
          value = title,
          onValueChange = {
            title = it
            titleError = false
          },
          label = { Text("Media Title") },
          placeholder = { Text("e.g. 20,000 Diamonds Winner Announcement") },
          isError = titleError,
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        // Category Selection
        Text("Category:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          listOf("Banner", "Winner Proof", "Announcement").forEach { cat ->
            FilterChip(
              selected = category == cat,
              onClick = { category = cat },
              label = { Text(cat, fontSize = 11.sp) },
              modifier = Modifier.weight(1f)
            )
          }
        }

        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Description") },
          placeholder = { Text("Details about this media asset...") },
          minLines = 2,
          maxLines = 3,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        // Icon preset selection
        Text("Icon Graphic:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          val icons = listOf(
            "diamond" to Icons.Default.Diamond,
            "trophy" to Icons.Default.EmojiEvents,
            "shield" to Icons.Default.Shield,
            "announcement" to Icons.Default.Campaign
          )
          icons.forEach { (type, vector) ->
            IconButton(
              onClick = {
                iconType = type
                selectedColorHex = when (type) {
                  "diamond" -> 0xFF0061A4
                  "trophy" -> 0xFFFFB703
                  "shield" -> 0xFF1B873F
                  else -> 0xFFD81B60
                }
              },
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (iconType == type) WarriorBlueContainer else BackgroundLight)
                .border(
                  width = if (iconType == type) 2.dp else 1.dp,
                  color = if (iconType == type) WarriorBlue else DividerColor,
                  shape = CircleShape
                )
            ) {
              Icon(
                imageVector = vector,
                contentDescription = type,
                tint = if (iconType == type) WarriorBlue else TextMuted,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (title.trim().isEmpty()) {
            titleError = true
            return@Button
          }
          onConfirm(
            title.trim(),
            category,
            if (description.trim().isEmpty()) "Official promotional media asset for Free Warrior Giveaway." else description.trim(),
            iconType,
            selectedColorHex
          )
        },
        colors = ButtonDefaults.buttonColors(containerColor = WarriorBlue)
      ) {
        Text("Upload Media")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@Composable
fun BannerCustomizeSection(
  appConfig: AppConfig,
  onSaveConfig: (AppConfig) -> Unit,
  modifier: Modifier = Modifier
) {
  var appTitle by remember(appConfig.appTitle) { mutableStateOf(appConfig.appTitle) }
  var appSubtitle by remember(appConfig.appSubtitle) { mutableStateOf(appConfig.appSubtitle) }
  var bannerTitle by remember(appConfig.bannerTitle) { mutableStateOf(appConfig.bannerTitle) }
  var bannerSubtitle by remember(appConfig.bannerSubtitle) { mutableStateOf(appConfig.bannerSubtitle) }
  var bannerBadge by remember(appConfig.bannerBadge) { mutableStateOf(appConfig.bannerBadge) }
  var bannerImageUrl by remember(appConfig.bannerImageUrl) { mutableStateOf(appConfig.bannerImageUrl) }
  var isBannerActive by remember(appConfig.isBannerActive) { mutableStateOf(appConfig.isBannerActive) }
  var bannerColorHex by remember(appConfig.bannerColorHex) { mutableStateOf(appConfig.bannerColorHex) }
  var isSaved by remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header Info Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = WarriorBlueContainer),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(WarriorBlue),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = "Customize",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Banner & Title Customizer",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              color = WarriorBlueDeep
            )
          )
          Text(
            text = "Update app title, dynamic banner, images, and guarantee messages in real time.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextSecondary,
              fontSize = 12.sp
            )
          )
        }
      }
    }

    // Live Preview Card
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceLight),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, WarriorBlueBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Visibility,
              contentDescription = "Preview",
              tint = WarriorBlue,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "LIVE PREVIEW",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = WarriorBlue,
                letterSpacing = 1.sp
              )
            )
          }

          if (isBannerActive) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(SuccessContainer)
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = "BANNER ACTIVE",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = SuccessGreen,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                )
              )
            }
          } else {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(DividerColor)
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = "BANNER HIDDEN",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = TextMuted,
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                )
              )
            }
          }
        }

        // Preview App Title Banner
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundLight)
            .padding(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = appTitle.ifEmpty { "FREE WARRIOR GIVEAWAY" },
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              color = Color(bannerColorHex),
              fontSize = 17.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = appSubtitle.ifEmpty { "20,000 DIAMONDS GIVEAWAY" },
            style = MaterialTheme.typography.labelSmall.copy(
              color = TextSecondary,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            ),
            maxLines = 1
          )
        }

        // Preview Dynamic Banner Card (if active)
        if (isBannerActive) {
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(bannerColorHex).copy(alpha = 0.08f)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(bannerColorHex).copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(bannerColorHex).copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                  Text(
                    text = bannerBadge.ifEmpty { "OFFICIAL BOUNTY" },
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = Color(bannerColorHex),
                      fontWeight = FontWeight.Black,
                      fontSize = 10.sp
                    )
                  )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.HourglassTop,
                    contentDescription = "Speed",
                    tint = Color(bannerColorHex),
                    modifier = Modifier.size(12.dp)
                  )
                  Spacer(modifier = Modifier.width(3.dp))
                  Text(
                    text = "Within 1 Hour / 1 Day",
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = Color(bannerColorHex),
                      fontWeight = FontWeight.Bold,
                      fontSize = 10.sp
                    )
                  )
                }
              }

              Text(
                text = bannerTitle.ifEmpty { "DIAMOND AIRDROP & REDEEM EVENT" },
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Black,
                  fontSize = 14.sp,
                  color = WarriorBlueDeep
                )
              )

              Text(
                text = bannerSubtitle.ifEmpty { "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day." },
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextSecondary,
                  fontSize = 11.5.sp,
                  lineHeight = 16.sp
                )
              )
            }
          }
        }
      }
    }

    // App Branding Section
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceLight),
      border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Title,
            contentDescription = "Title",
            tint = WarriorBlue,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "App Branding & Main Title",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = WarriorBlueDeep
            )
          )
        }

        OutlinedTextField(
          value = appTitle,
          onValueChange = { appTitle = it },
          label = { Text("Global App Title") },
          placeholder = { Text("e.g. FREE WARRIOR GIVEAWAY") },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = WarriorBlue,
            unfocusedBorderColor = DividerColor
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_admin_app_title")
        )

        OutlinedTextField(
          value = appSubtitle,
          onValueChange = { appSubtitle = it },
          label = { Text("App Subtitle / Event Tagline") },
          placeholder = { Text("e.g. 20,000 DIAMONDS GIVEAWAY") },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = WarriorBlue,
            unfocusedBorderColor = DividerColor
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_admin_app_subtitle")
        )
      }
    }

    // Dynamic Promotional Banner Configuration
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceLight),
      border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Campaign,
              contentDescription = "Campaign",
              tint = WarriorBlue,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Promotional Banner Card",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = WarriorBlueDeep
              )
            )
          }

          Switch(
            checked = isBannerActive,
            onCheckedChange = { isBannerActive = it },
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = WarriorBlue
            ),
            modifier = Modifier.testTag("switch_banner_active")
          )
        }

        OutlinedTextField(
          value = bannerTitle,
          onValueChange = { bannerTitle = it },
          label = { Text("Banner Headline") },
          placeholder = { Text("e.g. DIAMOND AIRDROP & REDEEM EVENT") },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = WarriorBlue,
            unfocusedBorderColor = DividerColor
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_admin_banner_title")
        )

        OutlinedTextField(
          value = bannerSubtitle,
          onValueChange = { bannerSubtitle = it },
          label = { Text("Banner Message / Delivery Terms") },
          placeholder = { Text("Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day.") },
          minLines = 2,
          maxLines = 4,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = WarriorBlue,
            unfocusedBorderColor = DividerColor
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_admin_banner_subtitle")
        )

        // Quick Preset for Delivery Guarantee Message
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          TextButton(
            onClick = {
              bannerSubtitle = "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day."
            }
          ) {
            Text("Set Official Delivery Guarantee", fontSize = 12.sp, color = WarriorBlue)
          }
        }

        OutlinedTextField(
          value = bannerBadge,
          onValueChange = { bannerBadge = it },
          label = { Text("Badge Label") },
          placeholder = { Text("e.g. OFFICIAL BOUNTY") },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = WarriorBlue,
            unfocusedBorderColor = DividerColor
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_admin_banner_badge")
        )

        OutlinedTextField(
          value = bannerImageUrl,
          onValueChange = { bannerImageUrl = it },
          label = { Text("Banner Image / Proof URL (Optional)") },
          placeholder = { Text("https://example.com/banner.png") },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = WarriorBlue,
            unfocusedBorderColor = DividerColor
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_admin_banner_image_url")
        )

        // Theme Accent Color Picker
        Text(
          text = "Banner Theme Accent Color:",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimary)
        )
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          val colorOptions = listOf(
            0xFF0061A4 to "Deep Blue",
            0xFFFFB703 to "Amber Gold",
            0xFF1B873F to "Emerald Green",
            0xFFD81B60 to "Crimson Red",
            0xFF00B4D8 to "Cyan"
          )
          colorOptions.forEach { (colorHex, name) ->
            IconButton(
              onClick = { bannerColorHex = colorHex },
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(colorHex))
                .border(
                  width = if (bannerColorHex == colorHex) 3.dp else 1.dp,
                  color = if (bannerColorHex == colorHex) WarriorBlueDeep else Color.White,
                  shape = CircleShape
                )
            ) {
              if (bannerColorHex == colorHex) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = name,
                  tint = Color.White,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          }
        }
      }
    }

    // Action Buttons
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      OutlinedButton(
        onClick = {
          appTitle = "FREE WARRIOR GIVEAWAY"
          appSubtitle = "20,000 DIAMONDS GIVEAWAY"
          bannerTitle = "DIAMOND AIRDROP & REDEEM EVENT"
          bannerSubtitle = "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day."
          bannerBadge = "OFFICIAL BOUNTY"
          bannerImageUrl = ""
          isBannerActive = true
          bannerColorHex = 0xFF0061A4
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("btn_reset_banner_config")
      ) {
        Text("Reset Defaults", color = TextSecondary, fontSize = 13.sp)
      }

      Button(
        onClick = {
          val updated = appConfig.copy(
            appTitle = appTitle.trim().ifEmpty { "FREE WARRIOR GIVEAWAY" },
            appSubtitle = appSubtitle.trim().ifEmpty { "20,000 DIAMONDS GIVEAWAY" },
            bannerTitle = bannerTitle.trim().ifEmpty { "DIAMOND AIRDROP & REDEEM EVENT" },
            bannerSubtitle = bannerSubtitle.trim().ifEmpty { "Either you will get the redeem code, or you will get the diamond in your mailbox within an hour or a day." },
            bannerBadge = bannerBadge.trim().ifEmpty { "OFFICIAL BOUNTY" },
            bannerImageUrl = bannerImageUrl.trim(),
            isBannerActive = isBannerActive,
            bannerColorHex = bannerColorHex
          )
          onSaveConfig(updated)
          isSaved = true
          coroutineScope.launch {
            delay(2500)
            isSaved = false
          }
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isSaved) SuccessGreen else WarriorBlue
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .weight(1.3f)
          .height(48.dp)
          .testTag("btn_save_banner_config")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = if (isSaved) Icons.Default.Check else Icons.Default.Save,
            contentDescription = "Save",
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isSaved) "SAVED LIVE!" else "SAVE & APPLY",
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}


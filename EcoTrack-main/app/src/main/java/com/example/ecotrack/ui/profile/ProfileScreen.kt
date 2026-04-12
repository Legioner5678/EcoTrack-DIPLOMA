package com.example.ecotrack.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecotrack.domain.model.EcoPointEntity
import com.example.ecotrack.viewmodel.ProfileComposeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(viewModel: ProfileComposeViewModel = hiltViewModel()) {
    val userName by viewModel.userName.collectAsState()
    val city by viewModel.city.collectAsState()
    val ecoPoints by viewModel.ecoPoints.collectAsState()
    val actions by viewModel.habits.collectAsState() // existing DB table: eco_points

    var editOpen by remember { mutableStateOf(false) }
    var nameDraft by remember { mutableStateOf("") }
    var cityDraft by remember { mutableStateOf("") }

    LaunchedEffect(editOpen, userName, city) {
        if (editOpen) {
            nameDraft = userName
            cityDraft = city
        }
    }

    val streakDays = remember(actions) { calculateStreakDays(actions) }
    val mostCompletedHabit = remember(actions) { mostCompletedHabitTitle(actions) }
    val history = remember(actions) { actions.sortedByDescending { it.date } }

    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                .background(scheme.secondaryContainer)
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(scheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (userName.firstOrNull()?.uppercaseChar()?.toString() ?: "U"),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = scheme.onPrimaryContainer
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                text = userName.ifBlank { "Your name" },
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = null,
                                    tint = scheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = city.ifBlank { "Place of living" },
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    IconButton(onClick = { editOpen = true }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit profile",
                            tint = scheme.primary
                        )
                    }
                }

                // Points card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = scheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Eco Points", style = MaterialTheme.typography.labelLarge)
                            Text(
                                text = ecoPoints.toString(),
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = scheme.primary
                            )
                        }
                        AssistChip(
                            onClick = {},
                            label = { Text("Keep it up 🌿") }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Stats row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Streak",
                value = "$streakDays days",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Most completed",
                value = mostCompletedHabit,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "History",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(history, key = { it.id }) { item ->
                HistoryItem(entity = item)
            }
        }
    }

    if (editOpen) {
        EditProfileDialog(
            name = nameDraft,
            city = cityDraft,
            onNameChange = { nameDraft = it },
            onCityChange = { cityDraft = it },
            onDismiss = { editOpen = false },
            onSave = {
                if (nameDraft.isNotBlank()) viewModel.saveName(nameDraft.trim())
                viewModel.saveCity(cityDraft.trim())
                editOpen = false
            }
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.heightIn(min = 86.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.labelLarge)
            Text(
                text = value.ifBlank { "—" },
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun HistoryItem(entity: EcoPointEntity) {
    val sdf = remember { SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault()) }
    val dateStr = sdf.format(Date(entity.date))
    val scheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = scheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entity.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = dateStr,
                    style = MaterialTheme.typography.bodySmall,
                    color = scheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(scheme.primary)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "+${entity.points} 🌱",
                    style = MaterialTheme.typography.labelLarge,
                    color = scheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun EditProfileDialog(
    name: String,
    city: String,
    onNameChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit profile") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = onCityChange,
                    label = { Text("Place of living") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = { Button(onClick = onSave) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

private fun calculateStreakDays(items: List<EcoPointEntity>): Int {
    if (items.isEmpty()) return 0

    val dayKey = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val days = items.map { dayKey.format(Date(it.date)) }.toSet()

    val cal = Calendar.getInstance()
    var streak = 0
    while (true) {
        val key = dayKey.format(cal.time)
        if (days.contains(key)) {
            streak++
            cal.add(Calendar.DAY_OF_YEAR, -1)
        } else {
            break
        }
    }
    return streak
}

private fun mostCompletedHabitTitle(items: List<EcoPointEntity>): String {
    if (items.isEmpty()) return "—"
    return items
        .groupingBy { it.title.trim().lowercase(Locale.getDefault()) }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key
        ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        ?: "—"
}

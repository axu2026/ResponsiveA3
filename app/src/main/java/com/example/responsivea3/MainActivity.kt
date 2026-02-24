package com.example.responsivea3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.responsivea3.ui.theme.ResponsiveA3Theme

// custom data for an option type
data class Option(
    val label: String,
    val icon: ImageVector,
    val selected: Boolean
)

// custom data for a mail type
data class Mail(
    val from: String,
    val subject: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResponsiveA3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Responsive(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Responsive(modifier: Modifier = Modifier) {
    // placeholder data
    val mail = listOf(
        Mail(from = "John S", subject = "Hey, where are you right now?"),
        Mail(from = "Amazon", subject = "Package delivered at doorstep"),
        Mail(from = "Microsoft", subject = "Someone logged onto your account"),
        Mail(from = "Jane D", subject = "Inquiry about your availability"),
    )

    val options = listOf(
        Option(label = "Inbox", icon = Icons.Filled.Home, true),
        Option(label = "Account", icon = Icons.Filled.AccountCircle, false),
        Option(label = "Settings", icon = Icons.Filled.Settings, false),
    )

    // create a box with a constraint, check the width of the app and adjust accordingly
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        if (this.maxWidth > 600.dp) {
            Tablet(options, mail)
        } else {
            Phone(options, mail)
        }
    }
}

// phone width composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Phone(options: List<Option>, mail: List<Mail>) {
    val backgroundColor = MaterialTheme.colorScheme.surface

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mail") },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "back arrow"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.weight(0.9f)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    mail.forEach { email ->
                        Email(email.from, email.subject)
                    }
                }
            }
            Row(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxSize()
                    .background(backgroundColor),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEach { option ->
                    Icon(
                        option.icon,
                        contentDescription = "icon"
                    )
                }
            }
        }
    }
}

// tablet width composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tablet(options: List<Option>, mail: List<Mail>) {
    val backgroundColor = MaterialTheme.colorScheme.surface

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LeftNavBar(
            options = options,
            modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .background(backgroundColor)
                .padding(8.dp)
        )
        Box (Modifier.weight(0.75f)) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(4.dp)
                ) {
                    mail.forEach { email ->
                        Email(email.from, email.subject)
                    }
                }
            }
        }
    }
}

// card for each email
@Composable
fun Email(from: String, subject: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                text = from
            )
            Text(text = subject)
        }
    }
}

// custom navbar column for the tablet composable
@Composable
fun LeftNavBar(options: List<Option>, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Mail"
        )
        HorizontalDivider()
        FilledTonalButton(
            onClick = {},
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Compose")
        }
        options.forEach { option ->
            ListItem(
                headlineContent = {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            option.icon,
                            contentDescription = "icon"
                        )
                        Text(text = option.label)
                    }
                }
            )
        }
    }
}

@Preview(
    name = "ResponsiveTablet",
    widthDp = 840,
    heightDp = 1280
)

@Preview(showBackground = true)
@Composable
fun ResponsivePhone() {
    ResponsiveA3Theme {
        Responsive()
    }
}
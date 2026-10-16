package com.example.noteapp.noteui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.noteapp.data.Note
import com.example.noteapp.navigation.NavBarRoute
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopBar(
    onSearchClicked :()-> Unit={},
    onMenuClicked :()-> Unit={}
){
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title ={Text("ComposeNotes")},
        actions = {
            IconButton(
                onClick = onSearchClicked,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search notes"
                )
            }
             IconButton(
                onClick = {
                    expanded=true
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More options"
                )
            }
            DropdownMenu(
                expanded=expanded,
                onDismissRequest = {expanded=false}
            ) {
                DropdownMenuItem(
                    text = {Text("Setting")},
                    leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                    onClick = {
                        expanded=false
                    }
                )
                DropdownMenuItem(
                    text = {Text("About")},
                    leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                    onClick = onMenuClicked
                )

            }
        },
        windowInsets = WindowInsets.statusBars
    )

}



@Composable
fun NoteCard(
    note: Note,
    onClick:()-> Unit
){
    Card(
        onClick = onClick,
        modifier = Modifier.padding(2.dp).heightIn(200.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(horizontalArrangement =Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                ){
                if (!note.isLock){
                    Text(note.title,style= MaterialTheme.typography.titleMedium, maxLines = 1)

                }else{
                    Text(
                        if (note.title.isNotEmpty() )  note.title.take(1)+ ".hidden" else "Untitled.Hidden"

                        ,style= MaterialTheme.typography.titleMedium, maxLines = 1)

                }

                if (note.isLock){
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Locked note"
                    )
                }
            }

            Spacer(Modifier.height(4.dp))
            if (!note.isLock){
                val content=note.content
                val title =content.take(50)+ if (content.length>50) "...." else ""
                Text(title,style= MaterialTheme.typography.bodyMedium, maxLines = 5)

            }else{
                Text(
                    "Access is Restricted"

                    ,style= MaterialTheme.typography.bodyMedium, maxLines = 1)

            }

        }
    }
}


@Composable
fun LockToggle(
    isLocked: Boolean,
    onToggle :(Boolean)->Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ){
Text("Lock This note ")
        Switch(
            checked = isLocked,
            onCheckedChange =onToggle
        )


    }
    AnimatedVisibility(visible = isLocked) {
        Text(
            text = "This note will required a PIN a access",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }

}



@Composable
fun AnimatedEmptyState(modifier: Modifier= Modifier){
    Column(
        modifier =modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val scale by rememberInfiniteTransition().animateFloat(
            initialValue = 1f, targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    1000
                ),
                repeatMode = RepeatMode.Reverse

            )
        )

        Icon(
            imageVector = Icons.AutoMirrored.Outlined.StickyNote2,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "No notes",
            modifier= Modifier.size(64.dp).graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )

        )
        Spacer(Modifier.height(12.dp))
        Text("no Notes Yet . tap + to add note",
            style = MaterialTheme.typography.bodyMedium

        )
    }
}


data class NavigatorBarItem(
    val title :String,
    val  route : NavBarRoute,
    val routeClass: KClass<out NavBarRoute>,
    val icon : ImageVector
)


@Composable
fun NoteBottomNavBar(
    navController : NavController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    val items= listOf<NavigatorBarItem>(
        NavigatorBarItem(
            title= "Notes",
            route =  NavBarRoute.Notes,
            routeClass = NavBarRoute.Notes :: class,
            icon=Icons.Outlined.NoteAlt
        ),
                NavigatorBarItem(
                title= "ToDo",
        route = NavBarRoute.Todo,
                    routeClass = NavBarRoute.Todo :: class,
        icon=Icons.Outlined.Checklist
    )
    )
    NavigationBar {

items.forEach { item ->

    val isSelected = currentDestination
        ?.hierarchy
        ?.any { destination ->
            destination.hasRoute(item.routeClass)
        } == true

    NavigationBarItem(
        label = {
            Text(item.title)
        },
        icon = {
            Icon(item.icon,
                contentDescription = item.title

            )
        },
        onClick = {
            navController.navigate(item.route){
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        },
        selected = false
    )


}

    }

}
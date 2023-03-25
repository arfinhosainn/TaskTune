package com.example.tasktune.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasktune.navigation.Screens
import com.example.tasktune.presentation.common.BottomNavItem
import com.example.tasktune.presentation.common.BottomNavigationBar
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    var selectedBottomNavItem by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)




}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(
                    text = "This is the bottom sheet content",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        content = {
            // Your main content here
        }
    )
    LaunchedEffect(sheetState.isVisible) {
        if (sheetState.isVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
            onDismiss()
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewHomeScreen() {
//    HomeScreen()
//
//}


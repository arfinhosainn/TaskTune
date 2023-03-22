package com.example.tasktune.presentation.auth


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasktune.data.remote.authapi.AuthResult
import com.example.tasktune.navigation.Screens
import com.example.tasktune.presentation.auth.components.AuthUiEvent
import com.example.tasktune.ui.theme.FontPoppins
import com.example.tasktune.ui.theme.Outline
import com.example.tasktune.ui.theme.OutlineVariant


@Composable
fun SignUpScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Authorized -> {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.SignUpScreen.route) {
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, "You're not authorized", Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context, "An unknown error occurred", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 25.dp)
                        .background(MaterialTheme.colors.surface)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 90.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Start),
                            text = "Welcome!", style = TextStyle(
                                color = MaterialTheme.colors.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 40.sp,
                                fontFamily = FontPoppins
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            modifier = Modifier.align(Alignment.Start),
                            text = "Get more done in less time. Sign up \nfor our app now!",
                            style = TextStyle(
                                color = MaterialTheme.colors.surface,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                fontFamily = FontPoppins
                            )
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        OutlinedTextField(
                            value = state.signUpUsername,
                            onValueChange = { userName ->
                                viewModel.onEvent(AuthUiEvent.SignUpUsernameChanged(userName))
                            },
                            placeholder = {
                                Text(
                                    "User Name",
                                    style = TextStyle(fontFamily = FontPoppins)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = OutlineVariant,
                                focusedBorderColor = Outline
                            )
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        OutlinedTextField(
                            value = state.signUpPassword,
                            onValueChange = { password ->
                                viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(password))
                            },
                            placeholder = {
                                Text(
                                    "Password",
                                    style = TextStyle(fontFamily = FontPoppins)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = OutlineVariant,
                                focusedBorderColor = Outline
                            )
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(top = 8.dp, start = 5.dp),
                            text = "Forgot Password?",
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = FontPoppins
                            )
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            onClick = {
                                viewModel.onEvent(AuthUiEvent.SignUp)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        ) {
                            Text(
                                text = "Sign Up", style = TextStyle(
                                    fontFamily = FontPoppins
                                )
                            )
                        }
                        ClickableText(
                            text = AnnotatedString("Already have an account? Sign In"),
                            onClick = {
                                navController.navigate(Screens.SignInScreen.route)
                            },
                            style = MaterialTheme.typography.body2.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary,
                                fontFamily = FontPoppins,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

package com.ralphdugue.arcadephito.auth.presentation.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.auth.domain.AuthType
import com.ralphdugue.arcadephito.auth.domain.AuthenticationFields

@Composable
fun AuthFormLandscape(
    authEntity: AuthenticationFields,
    showLoading: Boolean = false,
    onSubmitClick: (authEntity: AuthenticationFields) -> Unit,
    onToggleClick: (authEntity: AuthenticationFields) -> Unit
) {
    if (showLoading) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    } else {

        var username by remember { mutableStateOf(authEntity.username) }
        var email by remember { mutableStateOf(authEntity.email) }
        var password by remember { mutableStateOf(authEntity.password) }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.padding(15.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.width(300.dp),
                        painter = painterResource(id = R.drawable.phito_logo),
                        contentDescription = stringResource(id = R.string.logo_message)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        if (authEntity.authType == AuthType.EMAIL_SIGN_UP) {
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = username ?: "",
                                singleLine = true,
                                onValueChange = { newValue ->
                                    username = newValue
                                },
                                label = { Text(text = stringResource(id = R.string.username_label)) },
                                placeholder = { Text(text = stringResource(id = R.string.username_placeholder)) },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email ?: "",
                            singleLine = true,
                            onValueChange = { newValue ->
                                email = newValue
                            },
                            label = { Text(text = stringResource(id = R.string.email_label)) },
                            placeholder = { Text(text = stringResource(id = R.string.email_placeholder)) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password ?: "",
                            singleLine = true,
                            onValueChange = { newValue ->
                                password = newValue
                            },
                            label = { Text(text = stringResource(id = R.string.password_label)) },
                            placeholder = { Text(text = stringResource(id = R.string.password_placeholder)) },
                            visualTransformation = if (passwordVisible) {
                                VisualTransformation.None
                            } else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description = if (passwordVisible) {
                                    stringResource(id = R.string.hide_password)
                                } else stringResource(id = R.string.show_password)

                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, contentDescription = description)
                                }
                            }
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onSubmitClick(
                                    AuthenticationFields(
                                        username = username,
                                        email = email,
                                        password = password,
                                        authType = authEntity.authType
                                    )
                                )
                            },
                            enabled = !password.isNullOrBlank()
                                    && !email.isNullOrBlank()
                        ) {
                            Text(
                                text = if (authEntity.authType == AuthType.EMAIL_SIGNIN) {
                                    stringResource(id = R.string.signin_button)
                                } else {
                                    stringResource(id = R.string.signup_button)
                                }
                            )
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onToggleClick(
                                    AuthenticationFields(
                                        email = username,
                                        password = password,
                                        authType = authEntity.authType
                                    )
                                )
                            }
                        ) {
                            Text(
                                text = if (authEntity.authType == AuthType.EMAIL_SIGN_UP) {
                                    stringResource(id = R.string.click_to_signin)
                                } else {
                                    stringResource(id = R.string.click_to_signup)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}




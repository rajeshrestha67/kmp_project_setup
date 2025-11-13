package dev.rajesh.mobile_banking.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.components.button.AppButton
import dev.rajesh.mobile_banking.components.dimens
import dev.rajesh.mobile_banking.components.hideKeyboardOnTap
import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.MobileTextField
import dev.rajesh.mobile_banking.components.textField.PasswordTextField
import dev.rajesh.mobile_banking.login.model.LoginScreenAction
import dev.rajesh.mobile_banking.login.model.LoginScreenState
import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    onNavigateToDashboard: () -> Unit
) {
    val loginViewModel: LoginViewModel = koinViewModel()
    val state by loginViewModel.state.collectAsStateWithLifecycle()

    val platformMessage: PlatformMessage = koinInject()

    LaunchedEffect(Unit) {
        loginViewModel.errorChannel.collect {
            platformMessage.showToast(it)
        }
    }

    LaunchedEffect(Unit) {
        loginViewModel.successChannel.collect {
            if (it) {
                onNavigateToDashboard()
            }
        }
    }
    LoginScreenContent(
        state = state,
        onAction = loginViewModel::onAction
    )
}


@Composable
fun LoginScreenContent(
    state: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().imePadding().hideKeyboardOnTap(
            focusManager = focusManager,
            keyboardController = keyboardController
        ),
        containerColor = MaterialTheme.colorScheme.background,
    ) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)

        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.dimens.small3)
            ) {

                AsyncImage(
                    modifier = Modifier.fillMaxWidth()
                        .height(
                            150.dp
                        ),
                    model = SharedRes.getRes("drawable/bank_banner.png"),
                    contentDescription = "bank_banner",
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 30.sp
                    ),
                    text = stringResource(SharedRes.Strings.welcome)
                )
                Spacer(Modifier.height(16.dp))

                MobileTextField(
                    modifier = Modifier.fillMaxWidth(), //.focusRequester(focusRequester),
                    label = stringResource(SharedRes.Strings.mobileNumber),
                    hint = stringResource(SharedRes.Strings.enterYourMobileNumber),
                    value = state.mobileNumber,
                    onValueChange = {
                        onAction(LoginScreenAction.OnMobileNumberChanged(it))
                    },
                    error = state.mobileNumberError,
                    onErrorStateChange = {
                        onAction(LoginScreenAction.OnMobileNumberError(it))
                    },
                    enabled = !state.isLoading,
                    rules = FormValidate.mobileNumberValidationRules,
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )

                Spacer(Modifier.height(16.dp))

                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(SharedRes.Strings.password),
                    hint = stringResource(SharedRes.Strings.enterYourPassword),
                    value = state.password,
                    onValueChange = {
                        onAction(LoginScreenAction.OnPasswordChanged(it))
                    },
                    error = state.passwordError,
                    onErrorStateChange = {
                        onAction(LoginScreenAction.OnPasswordError(it))
                    },
                    rules = FormValidate.passwordValidationRules,
                    imeAction = ImeAction.Send,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            onAction(LoginScreenAction.LoginClicked)
                        }
                    )
                )

                Spacer(Modifier.height(32.dp))

                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onAction(LoginScreenAction.LoginClicked)
                    },
                    isLoading = state.isLoading,
                    text = stringResource(SharedRes.Strings.login)
                )

            }
        }
    }
}

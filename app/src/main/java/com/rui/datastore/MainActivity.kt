package com.rui.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rui.datastore.ui.theme.DataStoreTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataStoreTheme {
                val scope = rememberCoroutineScope()
                val value = remember { mutableStateOf(value = "") }
                val textContext = remember { mutableStateOf(value = "") }
                LaunchedEffect(Unit) {
                    textContext.value = AppDataStorePrefs.testText.get()
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = textContext.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = Color.Green,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(all = 16.dp),
                            textAlign = TextAlign.Center
                        )
                        BorderedInputField(
                            value = value.value,
                            onValueChange = { value.value = it },
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    AppDataStorePrefs.testText.set(value.value)
                                }
                            }
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "存储输入内容"
                            )
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    textContext.value = AppDataStorePrefs.testText.get()
                                }
                            }
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "读取存储内容"
                            )
                        }
                    }

                }
            }
        }
    }
}


@Composable
private fun BorderedInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { },
        enabled = true,
        isError = false,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp), // 圆角控制
    )
}


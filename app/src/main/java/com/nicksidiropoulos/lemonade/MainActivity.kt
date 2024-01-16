package com.nicksidiropoulos.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicksidiropoulos.lemonade.ui.theme.LemonadeTheme

var squeezeCounter = 0

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Yellow,
                            titleContentColor = Color.Black,
                        ),
                        title = {
                            Text(stringResource(id = R.string.app_name))
                        }
                    )
                },
            ) { innerPadding ->
                Text(
                    modifier = Modifier.padding(innerPadding),
                    text = ""
                )
            }
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp() {
    var step by remember { mutableIntStateOf(1) }
    val imageResource: Int
    val stringResource: Int
    val stringDesc: Int

    when(step){
        1 -> {
            imageResource = R.drawable.lemon_tree
            stringResource = R.string.tap_lemon
            stringDesc = R.string.tree_desc
        }
        2 -> {
            imageResource = R.drawable.lemon_squeeze
            stringResource = R.string.squeeze_lemon
            stringDesc = R.string.lemon_desc
        }
        3 -> {
            imageResource = R.drawable.lemon_drink
            stringResource = R.string.drink_it
            stringDesc = R.string.glass_desc
        }
        else -> {
            imageResource = R.drawable.lemon_restart
            stringResource = R.string.empty_glass
            stringDesc = R.string.empty_desc
        }
    }
    Column (
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        //color = MaterialTheme.colorScheme.background
    ){
        Button(onClick = { step = calculateStep(step) }, modifier = Modifier.width(300.dp)){
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = stringResource(id = stringDesc))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = stringResource), fontSize = 20.sp)
    }
}


fun calculateStep(step: Int): Int {
    return when (step){
        1, 3 -> step + 1
        2 -> {
            when (squeezeCounter) {
                0 -> {
                    squeezeCounter = (2..4).random()
                    2
                }
                in 2..4 -> {
                    squeezeCounter--
                    2
                }
                else -> step + 1
            }
        }
        else -> {
            squeezeCounter = 0
            1
        }
        }
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        LemonApp()
    }
}
package main.practice.portfolio


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import main.practice.portfolio.ui.theme.PortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Column(modifier = Modifier.padding(vertical = 100.dp), verticalArrangement = Arrangement.spacedBy(15.dp) , horizontalAlignment = Alignment.CenterHorizontally){
                            Login()
                        }

                        Column (modifier = Modifier.fillMaxWidth(1f), verticalArrangement = Arrangement.spacedBy(15.dp) , horizontalAlignment = Alignment.CenterHorizontally) {
                            TextField1()
                            TextField2()
                        }


                        Column (modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 30.dp) ,verticalArrangement = Arrangement.spacedBy(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            simpleButton()
                        }
                    }
                }
            }
        }



@Composable
fun Login() {
    Text(
        text = "LOGIN",
        fontFamily = FontFamily.Monospace,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField1(){
    var text by remember { mutableStateOf("") }
    TextField(value = text ,
        onValueChange = { text = it },
        label = { Text(text = "Username")},
        leadingIcon = {
            Icon(Icons.Default.Email, contentDescription = "Email")
        },
        modifier = Modifier.width(350.dp).clip(CutCornerShape(3.dp))

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField2(){
    var text by remember { mutableStateOf("") }
    TextField(value = text ,
        onValueChange = { text = it },
        label = { Text(text = "Password")},
        visualTransformation = PasswordVisualTransformation(),
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = "Password" )
        },
        modifier = Modifier.width(350.dp).clip(CutCornerShape(3.dp))
    )
}



@Composable
fun simpleButton(){

    Button(modifier = Modifier.padding(16.dp), onClick = { /*TODO*/ }) {
        Text(text = "ENTER")
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PortfolioTheme {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.padding(vertical = 100.dp), verticalArrangement = Arrangement.spacedBy(15.dp) , horizontalAlignment = Alignment.CenterHorizontally){
                Login()
            }

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField1()
                TextField2()
            }

            Column (modifier = Modifier
                .fillMaxSize(1f)
                .padding(vertical = 30.dp) ,verticalArrangement = Arrangement.spacedBy(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                simpleButton()

            }
        }
    }
}

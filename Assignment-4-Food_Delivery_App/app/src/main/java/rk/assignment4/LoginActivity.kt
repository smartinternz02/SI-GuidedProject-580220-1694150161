package rk.assignment4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rk.assignment4.ui.theme.SmartInternzAssignment4Theme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : ComponentActivity() {

    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var googleClient : GoogleSignInClient
    lateinit var activityResult : ActivityResultLauncher<Intent>

    override fun onStart() {
        super.onStart()

        val user = firebaseAuth.currentUser
        if(user!=null)
        {
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerAct()
        setContent {
            SmartInternzAssignment4Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoginScreen({ signInGoogle() }, {username, passwd -> loginFirebase(username, passwd) },
                        {msg -> toastMaker(msg)}, {username, password -> signUp(username, password)})
                }
            }
        }
    }

    private fun toastMaker(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun registerAct()
    {
        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback{ result->

            val resultCode = result.resultCode
            val data = result.data

            if(resultCode == RESULT_OK && data!=null)
            {
                val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                firebaseGoogleSignIn(task)
            }
        })
    }
    private fun signInGoogle()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("658675783751-orravmla7hh0d3qlcqd5ntbehp8vrovm.apps.googleusercontent.com")
            .requestEmail().build()

        googleClient = GoogleSignIn.getClient(this,gso)
        signIn()
    }

    private fun signIn()
    {
        val signIntent : Intent = googleClient.signInIntent
        activityResult.launch(signIntent)

    }

    private fun loginFirebase(email : String, pass:String)
    {
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext,"Welcome", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(applicationContext,task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseGoogleSignIn(task: Task<GoogleSignInAccount>)
    {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            Toast.makeText(applicationContext,"Welcome to quiz game",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
            firebaseGoogleAc(account)
        }
        catch (e: ApiException)
        {
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    private fun firebaseGoogleAc(account : GoogleSignInAccount)
    {
        val authCred = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(authCred)
    }

    private fun signUp(username: String, password: String){
        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext,"Account created successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun LoginScreen(googleSignin: () -> Unit, firebaseSignIn: (String,String) -> Unit,
                toastMaker: (String)-> Unit, signUp: (String, String)-> Unit) {
    var username by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }
    var repasswd by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var UpasswordVisible by rememberSaveable { mutableStateOf(false) }
    var signInState by remember { mutableStateOf(true) }
    var greetText by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize(),) {
        Image(painter = painterResource(id = R.drawable.login_bg), contentDescription = "Food",
            modifier = Modifier
                .fillMaxWidth(2.0f)
                .fillMaxHeight(0.4f)
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 0.dp)
                .clip(CutCornerShape(4.dp))
                .height(190.dp).width(200.dp),
            contentScale = ContentScale.Crop)

        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)){
            Text(text = greetText,
                style = TextStyle(fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.Bold, color = Color(0xFF298405)),
                modifier = Modifier.padding(horizontal = 7.dp),
                textAlign = TextAlign.Center)

            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 14.dp, end = 14.dp, bottom = 18.dp),
                colors = CardDefaults.cardColors(Color(0xFFD2DE32))
            ) {
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround) {
                        Button(onClick = { signInState = true }, modifier = Modifier
                            .weight(1f),
                            colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                            Text(text = "Sign in", style = TextStyle(fontSize = 20.sp),)
                        }
                        Spacer(modifier = Modifier
                            .width(1.dp)
                            .height(44.dp)
                            .background(
                                color = Color(0xFF797676)
                            ))
                        Button(onClick = { signInState = false }, modifier = Modifier
                            .weight(1f),
                            colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                            Text(text = "Sign up", style = TextStyle(fontSize = 20.sp),)
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        if(signInState){
                            greetText = "Your Favorite Food at Your Favorite Place "
                            OutlinedTextField(value = username,
                                onValueChange = {username = it},
                                modifier = Modifier.padding(top = 16.dp),
                                placeholder = { Text(text = "Username")},
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF8623BD),
                                    unfocusedBorderColor = Color(0xFF9500FF)
                                )
                            )
                            OutlinedTextField(value = passwd,
                                onValueChange = {passwd = it},
                                placeholder = { Text(text = "Password")},
                                modifier = Modifier.padding(top = 13.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF8623BD),
                                    unfocusedBorderColor = Color(0xFF9500FF)
                                ),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    val image = if (passwordVisible)
                                        painterResource(id = R.drawable.baseline_visibility_24)
                                    else painterResource(id = R.drawable.baseline_visibility_off_24)

                                    val description = if (passwordVisible) "Hide password" else "Show password"

                                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                                        Icon(painter  = image, description)
                                    }
                                }
                            )

                            Button(onClick = {
                                if(username.isEmpty() || passwd.isEmpty()){toastMaker("Username or password empty")}
                                else{firebaseSignIn(username, passwd)}
                            }, modifier = Modifier
                                .padding(top = 15.dp)
                                .size(200.dp, 48.dp)
                                , colors = ButtonDefaults.buttonColors(Color.Transparent),
                                contentPadding = PaddingValues()
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(Color(0xFF43B118), Color(0xFFD6C41C)),
                                        )
                                    )
                                    .padding(0.dp),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = "Sign in", style = TextStyle(fontSize = 24.sp))
                                }
                            }

                            Button(onClick = { googleSignin() }, modifier = Modifier
                                .padding(top = 10.dp)
                                .size(150.dp, 48.dp)
                                , colors = ButtonDefaults.buttonColors(Color.Transparent),
                                contentPadding = PaddingValues()
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(
                                        color = Color.Black
                                    )
                                    .padding(0.dp),
                                    contentAlignment = Alignment.Center
                                ){
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(painter = painterResource(id = R.drawable.google_icon), contentDescription = "google icon",
                                            modifier = Modifier.size(25.dp))
                                        Text(text = "Google", style = TextStyle(fontSize = 18.sp),
                                            modifier = Modifier.padding(start = 5.dp))
                                    }
                                }
                            }
                        }
                        else{
                            greetText = "Sign up and Enjoy your Food and your Safe Place"
                            OutlinedTextField(value = username,
                                onValueChange = {username = it},
                                modifier = Modifier.padding(top = 16.dp),
                                placeholder = { Text(text = "Enter Username")},
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF8623BD),
                                    unfocusedBorderColor = Color(0xFF9500FF)
                                )
                            )
                            OutlinedTextField(value = passwd,
                                onValueChange = {passwd = it},
                                placeholder = { Text(text = "Enter password")},
                                modifier = Modifier.padding(top = 13.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF8623BD),
                                    unfocusedBorderColor = Color(0xFF9500FF)
                                ),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    val image = if (passwordVisible)
                                        painterResource(id = R.drawable.baseline_visibility_24)
                                    else painterResource(id = R.drawable.baseline_visibility_off_24)

                                    val description = if (passwordVisible) "Hide password" else "Show password"

                                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                                        Icon(painter  = image, description)
                                    }
                                }
                            )
                            OutlinedTextField(value = repasswd,
                                onValueChange = {repasswd = it},
                                placeholder = { Text(text = "Re-enter password")},
                                modifier = Modifier.padding(top = 13.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF8623BD),
                                    unfocusedBorderColor = Color(0xFF9500FF)
                                ),
                                visualTransformation = if (UpasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    val image = if (UpasswordVisible)
                                        painterResource(id = R.drawable.baseline_visibility_24)
                                    else painterResource(id = R.drawable.baseline_visibility_off_24)

                                    val description = if (UpasswordVisible) "Hide password" else "Show password"

                                    IconButton(onClick = {UpasswordVisible = !UpasswordVisible}){
                                        Icon(painter  = image, description)
                                    }
                                }
                            )

                            Button(onClick = {
                                if(username.isEmpty() || passwd.isEmpty() || repasswd.isEmpty()){toastMaker("Username or password empty")}
                                else{
                                    if(passwd != repasswd){toastMaker("Passwords don't match")}
                                    else{signUp(username, passwd)}
                                }
                            }, modifier = Modifier
                                .padding(top = 15.dp)
                                .size(200.dp, 48.dp)
                                , colors = ButtonDefaults.buttonColors(Color.Transparent),
                                contentPadding = PaddingValues()
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(Color(0xFF43B118), Color(0xFFD6C41C)),
                                        )
                                    )
                                    .padding(0.dp),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = "Sign Up", style = TextStyle(fontSize = 24.sp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    SmartInternzAssignment4Theme {
        LoginScreen({},{ _, _ ->  },{ _->  },{ _, _ ->  })
    }
}


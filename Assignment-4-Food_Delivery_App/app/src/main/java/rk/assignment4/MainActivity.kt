package rk.assignment4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import rk.assignment4.ui.theme.SmartInternzAssignment4Theme
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : ComponentActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartInternzAssignment4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen ({ logOut() }, {it -> orderIntent(num = it)})
                }
            }
        }
    }

    private fun orderIntent(num: Int){
        val intent = Intent(this@MainActivity, MenuActivity::class.java)
        intent.putExtra("menuNum", num)
        startActivity(intent)
    }

    private fun logOut(){
        firebaseAuth.signOut()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        Toast.makeText(applicationContext, "Logged out Successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(logOut : () -> Unit, orderNow: (Int)-> Unit) {
    Column(modifier = Modifier.fillMaxSize()){
        CenterAlignedTopAppBar(title = {
            Row(modifier = Modifier.height(56.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.foody_logo), contentDescription = "Logo",
                    modifier = Modifier.size(50.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(start = 80.dp)
                ){
                    Text(text = "Foody", style = TextStyle(fontFamily = FontFamily(Font((R.font.armageda_wide),
                        weight = FontWeight(1))), fontSize = 44.sp, color = Color(0xFFA2C579)),
                        modifier = Modifier.padding(start = 5.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(end = 5.dp, start = 0.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.baseline_logout_24), contentDescription = "Log out",
                            modifier = Modifier
                                .size(30.dp)
                                .align(alignment = Alignment.CenterVertically)
                                .clickable { logOut() })
                    }
                }
            }
        }
        )
        var imageList = arrayListOf<Int>(R.drawable.coffee_bg, R.drawable.dominos_bg,R.drawable.kfc_bg,
            R.drawable.burgerking_bg, R.drawable.biryani_bg, R.drawable.paneer_bg)
        var restList = arrayListOf<String>("Cafe coffee day","Domino's pizza","KFC","Burger king","Biryani house"
            ,"Royal cuisine")
        var descList = arrayListOf<String>("ccd","dominos","kfc","burger king","biryani","royal restaurant")
        var cuisineList = arrayListOf<String>("North Indian", "South Indian")
        Column (modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())){

            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 5.dp)) {
                    Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp))
                    Text(text = "  TRENDING  ", color = Color(0xFF8F8F8F), style = TextStyle(fontSize = 18.sp))
                    Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp))
                }

                var ranOne = Random.nextInt(0..imageList.size-1)
                var ranTwo = Random.nextInt(0..imageList.size-1)
                var ranCuisine = Random.nextInt(0..1)
                var ranCuisineTwo = if(ranCuisine==1) 0 else 1
                while (ranOne == ranTwo){ranTwo = Random.nextInt(0..imageList.size-1)}

                recommendation(bgImage = imageList[ranOne], bgDesc = descList[ranOne], restName = restList[ranOne],
                    cuisineList = cuisineList[ranCuisine], {orderNow(ranOne)})
                recommendation(bgImage = imageList[ranTwo], bgDesc = descList[ranTwo], restName = restList[ranTwo],
                    cuisineList = cuisineList[ranCuisineTwo], {orderNow(ranTwo)})
            }

            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 30.dp)) {
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp))
                Text(text = "  ALL  RESTAURANTS  ", color = Color(0xFF8F8F8F), style = TextStyle(fontSize = 18.sp))
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp))
            }

            hotelCard(bgImage = imageList[0], bgDesc = descList[0], restName = restList[0], cuisineList = cuisineList[0]) {
                orderNow(0)
            }
            hotelCard(bgImage = imageList[1], bgDesc = descList[1], restName = restList[1], cuisineList = cuisineList[1]) {
                orderNow(1)
            }
            hotelCard(bgImage = imageList[2], bgDesc = descList[2], restName = restList[2], cuisineList = cuisineList[1]) {
                orderNow(2)
            }
            hotelCard(bgImage = imageList[3], bgDesc = descList[3], restName = restList[3], cuisineList = cuisineList[0]) {
                orderNow(3)
            }
            hotelCard(bgImage = imageList[4], bgDesc = descList[4], restName = restList[4], cuisineList = cuisineList[1]) {
                orderNow(4)
            }
            hotelCard(bgImage = imageList[5], bgDesc = descList[5], restName = restList[5], cuisineList = cuisineList[0]) {
                orderNow(5)
            }

            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)) {
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp))
                Text(text = "  MORE RESTAURANTS COMING SOON  ", color = Color(0xFF8F8F8F), style = TextStyle(fontSize = 18.sp))
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartInternzAssignment4Theme {
        HomeScreen({},{})
    }
}

private fun rating(): Float {
    val randomValue = Random.nextFloat() * (5.0 - 3.0) + 3.0
    return "%.1f".format(randomValue).toFloat()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun hotelCard(bgImage: Int, bgDesc: String, restName: String, cuisineList: String, clickFunction: ()->Unit){
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 14.dp, top = 26.dp),
            colors = CardDefaults.cardColors(Color(0xFFD2DE32)),
            onClick = {clickFunction()}
        ) {
            Column {
                Image(painter = painterResource(id = bgImage), contentDescription = bgDesc,
                    modifier = Modifier.height(200.dp), contentScale = ContentScale.Crop)

                Column(modifier = Modifier.padding( end = 10.dp, top = 10.dp, bottom = 5.dp)) {
                    Row (modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)){
                        Text(text = restName, style = TextStyle(fontFamily = FontFamily(Font((R.font.poppins_bold),
                            weight = FontWeight(1))), fontSize = 24.sp,))
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterVertically)){
                            Row (modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End){
                                Box(modifier = Modifier
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .background(Color(0xFF2A9E00))
                                    .size(50.dp, 26.dp)){
                                    Row {
                                        Text(text = rating().toString(), style = TextStyle(fontSize = 18.sp),
                                            color = Color.White, modifier = Modifier.padding(start = 5.dp))
                                        Image(painter = painterResource(id = R.drawable.baseline_star_24),
                                            contentDescription = "Star", modifier = Modifier
                                                .size(28.dp)
                                                .padding(start = 1.dp, end = 5.dp)
                                                .align(Alignment.CenterVertically))
                                    }
                                }
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = cuisineList, style = TextStyle(fontSize = 15.sp,
                            fontFamily = FontFamily(Font((R.font.poppins_regular), weight = FontWeight(1)))),
                            color = Color(0xFF9B9B9B), modifier = Modifier.padding(start = 10.dp, end = 8.dp)
                        )

                        Image(painter = painterResource(id = R.drawable.baseline_circle_24), contentDescription = "circle",
                            modifier = Modifier
                                .size(14.dp)
                                .padding(bottom = 3.dp))

                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp), verticalAlignment = Alignment.CenterVertically){
                            Image(painter = painterResource(id = R.drawable.timer_pic), contentDescription = "Watch",
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(bottom = 3.dp))
                            Text(text = "25-35 min", style = TextStyle(fontSize = 15.sp,
                                fontFamily = FontFamily(Font((R.font.poppins_regular), weight = FontWeight(1)))),
                                color = Color(0xFF9B9B9B))
                        }
                    }
                }
            }
        }
}

@Composable
fun recommendation(bgImage: Int, bgDesc: String, restName: String, cuisineList: String, orderNow: () -> Unit){

    Card(modifier = Modifier
        .fillMaxWidth(0.92f)
        .padding(top = 14.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color(0xFFD2DE32))) {
        Row {
            Image(painter = painterResource(id = bgImage),
                contentDescription = bgDesc, contentScale = ContentScale.Crop,
                modifier = Modifier.size(70.dp))
            Column(modifier = Modifier.padding(start = 10.dp, top = 5.dp)) {
                Text(text = restName, style = TextStyle(fontSize = 16.sp), color = Color.Black)
                Text(text = cuisineList, style = TextStyle(fontSize = 15.sp), color = Color(0xFFD2DE32)
                )
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterVertically)){
                Button(onClick = { orderNow() }, modifier = Modifier
                    .size(78.dp, 36.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
                    , colors = ButtonDefaults.buttonColors(Color(0xFFD2DE32)),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE93545))
                ) {
                    Text(text = "ORDER", color = Color(0xFFE93545), style = TextStyle(fontSize = 15.sp,
                        fontWeight = FontWeight.Bold))
                }
            }
        }
    }
}
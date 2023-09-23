package rk.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import rk.assignment4.ui.theme.SmartInternzAssignment4Theme
import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.random.nextInt


class CartActivity : ComponentActivity() {
    
    private lateinit var cartItems : HashMap<String, Int>
    private var hotel by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hotel = intent.getIntExtra("imageID", 0)
        cartItems = intent.getSerializableExtra("cartList") as HashMap<String, Int>

        val subT = subCal(cartItems)
        val gst = gstCal(subT)
        val delCharge = delCal(subT)

        setContent {
            SmartInternzAssignment4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Cart(subT, gst, delCharge, hotel)
                }
            }
        }
    }
    private fun subCal(item: HashMap<String, Int>) : Int{
        var temp = 0
        for (items in item){
            temp += items.value
        }
        return temp
    }
    private fun gstCal(subT : Int) : Float{
        return String.format("%.1f", subT*0.05).toFloat()
    }
    private fun delCal(subT: Int): Int {
        if(subT == 0)return 0
        return Random.nextInt(30..100)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(subTotal: Int, gst: Float, delCharge: Int, hotel: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(title = {
            Row(
                modifier = Modifier.height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.foody_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(end = 45.dp)
                ) {
                    Text(
                        text = "Foody", style = TextStyle(
                            fontFamily = FontFamily(
                                Font(
                                    (R.font.armageda_wide),
                                    weight = FontWeight(1)
                                )
                            ), fontSize = 44.sp, color = Color(0xFFA2C579)
                        ),
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Your Cart",
                style = TextStyle(
                    fontSize = 46.sp,color = Color(0xFF016A70),
                    fontFamily = FontFamily(Font((R.font.poppins_regular), weight = FontWeight(1))),
                    fontWeight = FontWeight.Bold
                )
            )
            Image(painter = painterResource(id = hotel), contentDescription = "Hotel Pic",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.3f)
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 0.dp)
                    .clip(CutCornerShape(4.dp)),
                    contentScale = ContentScale.Crop)
            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp))
                Text(text = "  ORDER SUMMARY  ", color = Color(0xFF8F8F8F), style = TextStyle(fontSize = 20.sp))
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp))
            }
            card(subTotal, gst, delCharge)
            Column(modifier = Modifier.fillMaxHeight().padding(bottom = 10.dp),
                verticalArrangement = Arrangement.Center) {
                Text(text = "Enjoy Your Food", style = TextStyle(fontSize = 26.sp, fontFamily = FontFamily(
                    Font((R.font.poppins_regular), weight = FontWeight(1))
                ), fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Composable
fun card(subTotal: Int, gst: Float, delCharge: Int){
    Card(modifier = Modifier
        .fillMaxWidth(0.95f)
        .padding(top = 25.dp,),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFFFDD))
    ) {
        Column(modifier = Modifier.padding(horizontal = 13.dp)) {
            Row(modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()) {
                Text(text = "Subtotal", style = TextStyle(fontSize = 23.sp, fontFamily = FontFamily(
                    Font((R.font.roboto_light), weight = FontWeight(1))
                ), fontWeight = FontWeight.Bold))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Text(text = subTotal.toString(), style = TextStyle(fontSize = 23.sp, fontFamily = FontFamily(
                        Font((R.font.roboto_light), weight = FontWeight(1))
                    ), fontWeight = FontWeight.Bold))
                }
            }
            Row(modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.baseline_account_balance_24), contentDescription = "Gov",
                    modifier = Modifier.size(21.dp))
                Text(text = "GST", style = TextStyle(fontSize = 17.sp, fontFamily = FontFamily(
                    Font((R.font.roboto_regular), weight = FontWeight(1))
                )), modifier = Modifier.padding(start = 6.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Text(text = gst.toString(), style = TextStyle(fontSize = 17.sp, fontFamily = FontFamily(
                        Font((R.font.roboto_light), weight = FontWeight(1))
                    ), fontWeight = FontWeight.Bold))
                }
            }
            Row(modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.baseline_directions_bike_24), contentDescription = "Gov",
                    modifier = Modifier.size(21.dp))
                Text(text = "Delivery fees", style = TextStyle(fontSize = 17.sp, fontFamily = FontFamily(
                    Font((R.font.roboto_regular), weight = FontWeight(1))
                )), modifier = Modifier.padding(start = 6.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Text(text = delCharge.toString(), style = TextStyle(fontSize = 17.sp, fontFamily = FontFamily(
                        Font((R.font.roboto_light), weight = FontWeight(1))
                    ), fontWeight = FontWeight.Bold))
                }
            }

            Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier.padding(top = 20.dp))

            Row(modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()) {
                Text(text = "Grand Total", style = TextStyle(fontSize = 24.sp, fontFamily = FontFamily(
                    Font((R.font.roboto_light), weight = FontWeight(1))
                ), fontWeight = FontWeight.Bold))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Text(text = "${subTotal+gst+delCharge}", style = TextStyle(fontSize = 24.sp, fontFamily = FontFamily(
                        Font((R.font.roboto_light), weight = FontWeight(1))
                    ), fontWeight = FontWeight.Bold))
                }
            }
            Button(onClick = {  }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                , colors = ButtonDefaults.buttonColors(Color(0xFFD2DE32)),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFFD2DE32))
            ) {
                Text(text = "PLACE ORDER", color = Color(0xFFE93545), style = TextStyle(fontSize = 20.sp,
                    fontWeight = FontWeight.Bold), modifier = Modifier.padding(start = 2.dp, end = 2.dp))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    SmartInternzAssignment4Theme {
        Cart(0,0f,0,0)
    }
}
package rk.assignment4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rk.assignment4.ui.theme.SmartInternzAssignment4Theme
import kotlin.random.Random
import kotlin.random.nextInt

class MenuActivity : ComponentActivity() {
    lateinit var cartItems : MutableMap<String, Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartItems = HashMap()
        val num = intent.getIntExtra("menuNum", 0)

        setContent {
            SmartInternzAssignment4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    menuScreen(num, { name, price -> addToCart(name, price) }, {num -> openCart(num)})
                }
            }
        }
    }
    private fun addToCart(itemName: String, price: Int){
        cartItems[itemName] = price
    }
    private fun openCart(num: Int){
        val cart = HashMap(cartItems)
        val intent = Intent(this@MenuActivity, CartActivity::class.java)
        intent.putExtra("cartList", cart)
        intent.putExtra("imageID", num)
        startActivity(intent)
    }
}

@Composable
fun menuScreen(num: Int, addtoCart: (String, Int)-> Unit, openCart: (Int) -> Unit) {

    var itemCount by remember { mutableStateOf(0) }

    var imageList = arrayListOf<Int>(R.drawable.coffee_bg, R.drawable.dominos_bg,R.drawable.kfc_bg,
        R.drawable.burgerking_bg, R.drawable.biryani_bg, R.drawable.paneer_bg)
    var restList = arrayListOf<String>("Cafe coffee day","Domino's pizza","KFC","Burger king","Biryani house"
        ,"Royal cuisine")
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = imageList[num]), contentDescription = "Hotel Pic",
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.3f)
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 0.dp)
                .clip(CutCornerShape(4.dp))
                .height(200.dp).width(200.dp),
                contentScale = ContentScale.Crop)
        Row (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(top = 6.dp)){
            Text(text = restList[num], style = TextStyle(fontSize = 30.sp, fontFamily = FontFamily(
                Font((R.font.poppins_regular), weight = FontWeight(1))), fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 10.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp).clickable { openCart(imageList[num]) }){
                    Image(painter = painterResource(id = R.drawable.baseline_shopping_cart_24), contentDescription = "Cart",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(80.dp, 40.dp))
                    Box(modifier = Modifier.align(Alignment.BottomEnd)){
                        Row (modifier = Modifier.padding(end = 12.dp, bottom = 5.dp)){
                            Text(text = itemCount.toString(), style = TextStyle(fontSize = 22.sp),
                                color = Color(0xFF618264), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        Column (modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally){
            val menuId = R.array.Menu0 + num
            val menuArray = stringArrayResource(id = menuId)
            var i = 0
            val map = mutableMapOf<String, ArrayList<Int>>()
            map["menu0"] = arrayListOf(R.drawable.ccdmenu0,R.drawable.ccdmenu1,R.drawable.ccdmenu2,R.drawable.ccdmenu3,
                R.drawable.ccdmenu4,R.drawable.ccdmenu5)
            map["menu1"] = arrayListOf(R.drawable.dominosmenu0,R.drawable.dominosmenu1,R.drawable.dominosmenu2,R.drawable.dominosmenu3,
                R.drawable.dominosmenu4,R.drawable.dominosmenu5)
            map["menu2"] = arrayListOf(R.drawable.kfcmenu0,R.drawable.kfcmenu1,R.drawable.kfcmenu2,R.drawable.kfcmenu3,
                R.drawable.kfcmenu4,R.drawable.kfcmenu5)
            map["menu3"] = arrayListOf(R.drawable.bkmenu0,R.drawable.bkmenu1,R.drawable.bkmenu2,R.drawable.bkmenu3,
                R.drawable.bkmenu4,R.drawable.bkmenu5)
            map["menu4"] = arrayListOf(R.drawable.birmenu0,R.drawable.birmenu0,R.drawable.birmenu2,R.drawable.birmenu3,
                R.drawable.birmenu4,R.drawable.birmenu5)
            map["menu5"] = arrayListOf(R.drawable.hotmenu0,R.drawable.hotmenu1,R.drawable.hotmenu2,R.drawable.hotmenu3,
                R.drawable.hotmenu4,R.drawable.hotmenu5)

            var imgArray = map["menu$num"]
            while(i<6){
                cardMaker(imgArray?.get(i) ?: R.drawable.timer_pic,menuArray[i]) {name,price->
                    itemCount++
                    addtoCart(name, price)
                }
                i++
            }
            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp))
                Text(text = "  END  ", color = Color(0xFF8F8F8F), style = TextStyle(fontSize = 18.sp))
                Divider(color = Color(0xFFD5D5D5), thickness = 1.dp, modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp))
            }
        }

    }
}

@Composable
fun cardMaker(imageId: Int, itemName: String, itemCounter: (String, Int) -> Unit ){
    val price = makePrice()
    Card(modifier = Modifier
        .fillMaxWidth(0.95f)
        .wrapContentHeight()
        .padding(top = 15.dp,),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFFFDD))
    ) {
        Column(modifier = Modifier
            .fillMaxSize()) {
            Row (modifier = Modifier){
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxHeight()) {
                    Image(painter = painterResource(id = imageId), contentDescription = "Food pic",
                        modifier = Modifier.size(96.dp), contentScale = ContentScale.Crop)
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.60f),
                        verticalArrangement = Arrangement.Center) {
                        Text(text = itemName, style = TextStyle(fontFamily = FontFamily(Font((R.font.roboto_regular),
                            weight = FontWeight(3))), fontSize = 22.sp),
                            modifier = Modifier.padding(start = 10.dp), maxLines = 2, overflow = TextOverflow.Ellipsis)
                        Text(text = "₹ "+ price.toString(), style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.padding(start = 10.dp, top = 8.dp))
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically),) {
                    Button(onClick = { itemCounter(itemName, price) }, modifier = Modifier
                        .size(100.dp, 38.dp)
                        .padding(end = 10.dp)
                        .align(Alignment.CenterEnd)
                        , colors = ButtonDefaults.buttonColors(Color(0xFFD2DE32)),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFE93545))
                    ) {
                        Text(text = "ADD", color = Color(0xFFE93545), style = TextStyle(fontSize = 19.sp,
                            fontWeight = FontWeight.Bold), modifier = Modifier.padding(start = 2.dp, end = 2.dp))
                    }
                }
            }
        }
    }
}

private fun makePrice() : Int{
    var num = Random.nextInt(200..600)
    return num
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SmartInternzAssignment4Theme {
        menuScreen(0,{_,_ ->},{})
    }
}
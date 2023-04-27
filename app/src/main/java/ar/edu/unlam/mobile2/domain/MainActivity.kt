package ar.edu.unlam.mobile2.domain


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TabRowDefaults

import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material.TabRow
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile2.R
import ar.edu.unlam.mobile2.data.Articulo
import ar.edu.unlam.mobile2.data.ArticuloRepository
import ar.edu.unlam.mobile2.data.Tabs_item
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tabs_Principal()
            //AppContainer(ArticuloRepository.articulos)
        }
    }
}


@Preview
@Composable
fun MyFilterList() {

}

@Preview(showSystemUi = true)
@Composable
fun MyApp() {
    AppContainer(ArticuloRepository.articulos)
}

@Composable
fun AppContainer(articulos: List<Articulo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        items(articulos) { articulo ->
            Cards(articulo)
        }
    }
}


@Composable
fun Cards(articulo: Articulo) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .padding(10.dp)
    ) {
        ItemsNews(articulo)
    }
}

@Composable
fun MyText(text: String) {
    Text(text = text)
}

@Composable
fun ItemsNews(articulo: Articulo) {
    Column(modifier = Modifier.padding(10.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "NewsBackground",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        MyText(text = articulo.topic)
        MyText(text = articulo.titulo)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Tabs_Principal() {
    val tabs = listOf(
        Tabs_item.item_general,
        Tabs_item.item_politica,
        Tabs_item.item_muscia
    )
    val pagerState = rememberPagerState()
    Column() {
        Tabs(tabs, pagerState)
        Tabs_content(tabs, pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<Tabs_item>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        tabs.forEachIndexed { index, tabsItem ->
            androidx.compose.material3.LeadingIconTab(
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                icon = {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = tabsItem.icon),
                        contentDescription = ""
                    )
                },
                text = { Text(tabsItem.title) })

        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Tabs_content(tabs: List<Tabs_item>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}
/*override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
}*/

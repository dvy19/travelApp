package com.example.travelguide.user.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelguide.SessionManager
import com.example.travelguide.city.City
import com.example.travelguide.city.CityRepository
import com.example.travelguide.city.CityResponse
import com.example.travelguide.city.CityViewModel
import com.example.travelguide.city.CityViewModelFactory
import com.example.travelguide.city.GetAllCityState
import com.example.travelguide.place.Place
import com.example.travelguide.place.PlaceRepository
import com.example.travelguide.place.PlaceViewModel
import com.example.travelguide.place.PlaceViewModelFactory
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.travelguide.place.GetAllPlaceState
import androidx.compose.ui.graphics.Brush
import com.example.travelguide.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mainNavController: NavController
)
{

    val context= LocalContext.current

    val sessionManager=SessionManager(context)

    val repo=CityRepository(sessionManager)

    val placeRepo= PlaceRepository(sessionManager)
    val placeViewModel: PlaceViewModel=viewModel(
        factory = PlaceViewModelFactory(placeRepo)
    )

    val viewModel: CityViewModel=viewModel(
        factory= CityViewModelFactory(repo)
    )

    var selectedCategory by remember {
        mutableStateOf(categories.first())
    }


    val token=sessionManager.getAuthToken()

    LaunchedEffect(Unit) {

        token?.let {

            Log.d("TOKEN", token.toString())


            viewModel.fetchAllCity()
            placeViewModel.fetchAllPlaces()
        }
    }


    val getCityState by viewModel.getCityState.collectAsState()
    val getAllPlaceState by placeViewModel.getPlaceState.collectAsState()



    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()



    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(
                modifier = Modifier.width(240.dp),

                ) {

                NavigationDrawerItem(
                    label = {
                        Text("Reviews Added")
                    },
                    selected = false,
                    onClick = {

                        mainNavController.navigate(Screens.PersonalReviewScreen.route)

                    }
                )

                NavigationDrawerItem(
                    label = {
                        Text("Saved Places")
                    },
                    selected = false,
                    onClick = {

                        //mainNavController.navigate( "phone")

                    }
                )

                NavigationDrawerItem(
                    label = {
                        Text("Log Out")
                    },
                    selected = false,
                    onClick = {

                        // mainNavController.navigate("delete_account")

                    }
                )

                NavigationDrawerItem(
                    label = {
                        Text("Close")
                    },
                    selected = false,
                    onClick = {

                        scope.launch{
                            drawerState.close()
                        }

                        // mainNavController.navigate("delete_account")

                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("HomeScreen")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {

                                    drawerState.open()

                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                Icons.Default.Message,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(
                        innerPadding
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Featured Cities Section
                item {

                    when (getCityState) {
                        is GetAllCityState.Idle -> {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Idle / Initializing...")
                            }
                        }

                        is GetAllCityState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is GetAllCityState.Success -> {

                            val cities = (getCityState as GetAllCityState.Success).cities

                            FeaturedCitiesSection(
                                cities = cities,
                                onCityClick = {
                                    mainNavController.navigate("cityDetail/${it.id}")

                                },
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )
                        }

                        is GetAllCityState.Error -> {

                            Text("Error")
                        }
                    }
                }



                item {

                    CategoryFilterSection(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = {
                            selectedCategory = it
                        }
                    )

                }


                item {

                    Text(
                        text = "Popular Places",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                }

                when (getAllPlaceState) {

                    is GetAllPlaceState.Idle -> {
                        item {
                            Text("Idle")
                        }

                    }

                    is GetAllPlaceState.Loading -> {

                        item {
                            Text("Loading")
                        }
                    }


                    is GetAllPlaceState.Success -> {

                        val places =
                            (getAllPlaceState as GetAllPlaceState.Success).places

                        items(
                            items = places,
                            key = { it.id }
                        ) { place ->

                            PlaceCardItem(
                                place = place,
                                onClick = {
                                    mainNavController.navigate("placeDetail/${place.id}")

                                }
                            )

                        }

                    }

                    is GetAllPlaceState.Error -> {

                        item {
                            Text("Failed to load places")
                        }

                    }

                    else -> {}

                }
            }
        }

    }}



data class PlaceCategory(
    val id: Int,
    val name: String
)

val categories = listOf(
    PlaceCategory(1, "All"),
    PlaceCategory(2, "Parks"),
    PlaceCategory(3, "Temples"),
    PlaceCategory(4, "Monuments"),
    PlaceCategory(5, "Museums"),
    PlaceCategory(6, "Historical"),
    PlaceCategory(7, "Food"),
    PlaceCategory(8, "Markets")
)

@Composable
fun CategoryCard(
    category: PlaceCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = category.name,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            color = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun CategoryFilterSection(
    categories: List<PlaceCategory>,
    selectedCategory: PlaceCategory?,
    onCategorySelected: (PlaceCategory) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(categories) { category ->

            CategoryCard(
                category = category,
                isSelected = selectedCategory?.id == category.id,
                onClick = {
                    onCategorySelected(category)
                }
            )

        }

    }
}


// Premium Light Theme Palette
val TravelBgLight = Color(0xFFFAFAFA)       // Soft off-white for homescreen
val CardSurfaceLight = Color(0xFFF3F4F6)   // Subtle light gray for card base
val TextPrimary = Color(0xFF1F2937)        // Deep charcoal (softer than pure black)
val AccentTeal = Color(0xFF0F766E)

@Composable
fun CityCard(
    city: City,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp), // Clean, modern radiuses
        colors = CardDefaults.cardColors(containerColor = CardSurfaceLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Flat, minimalist look
        modifier = modifier
            .width(90.dp)
            .height(80.dp)// Made smaller for a sleek, high-density homescreen look
            .aspectRatio(0.75f) // Elegant portrait ratio
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Destination Image
            AsyncImage(
                model = city.image,
                contentDescription = "Destination ${city.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Soft scrim gradient to ensure text readability without heavy black overlays
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.1f),
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 100f
                        )
                    )
            )

            // Text content overlaid neatly at the bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.3.sp
                    ),
                    color = Color.White,
                    maxLines = 1
                )

                Text(
                    text = city.state,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun FeaturedCitiesSection(
    cities: List<City>,
    onCityClick: (City) -> Unit,
   // onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Container column dynamic height based on smaller component footprints
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TravelBgLight)
            .padding(vertical = 12.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Explore Destinations",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.2).sp
                ),
                color = TextPrimary
            )
            TextButton(
                onClick = {  },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "See All",
                    color = AccentTeal,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal Scrollable List
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cities) { city ->
                CityCard(
                    city = city,
                    onClick = { onCityClick(city) }
                )
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceCardItem(
    place: Place,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp), // Fixed height for a balanced, premium row layout
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. Image Section with Aspect Ratio Block
            Box(
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = place.image_url,
                    contentDescription = place.name,
                    modifier = Modifier.fillMaxWidth()
                )

                // Subtle Category Badge overlaid on the top-left of the image
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = place.category_name,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // 2. Text Details Section
            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Name Title
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // City / Location Subtitle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Location Pin",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = place.city_name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewHome(){
    HomeScreen(mainNavController = rememberNavController())
}

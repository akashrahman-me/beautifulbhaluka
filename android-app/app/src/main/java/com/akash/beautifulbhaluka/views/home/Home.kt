package com.akash.beautifulbhaluka.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akash.beautifulbhaluka.views.home.components.Carousel
import com.akash.beautifulbhaluka.views.home.components.LinkCard
import com.akash.beautifulbhaluka.views.home.data.linkSections

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun Home(navController: NavHostController? = null) {
    val scrollState = rememberScrollState()

    Column {
        // MAIN CONTENT
        Box(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 8.dp)

        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Carousel()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    linkSections.forEach { section ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = section.name,
                                style = MaterialTheme.typography.titleLarge,
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                            val itemWidth =
                                (screenWidth - (16.dp) - 20.dp * 2) / 3 // padding + gaps

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                section.values.forEach { link ->
                                    LinkCard(
                                        navController = navController,
                                        link = link.link,
                                        text = link.label,
                                        icon = link.icon,
                                        modifier = Modifier.width(itemWidth - 6.dp) // force equal width
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }

}


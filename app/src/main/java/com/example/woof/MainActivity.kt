/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.woof

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.woof.data.Dog
import com.example.woof.data.dogs
import com.example.woof.ui.theme.WoofTheme


const val TAG = "main"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                WoofApp()
            }
        }
    }
}

@Composable
fun WoofTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colors.primary)
            .fillMaxWidth(),
        verticalAlignment = CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_woof_logo),
            contentDescription = null,
            modifier = modifier
                .size(64.dp)
                .padding(8.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun WoofApp() {

    Scaffold(topBar = { WoofTopBar() }) {

        LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
            items(items = dogs, key = { dog -> dog.id }
            ) { dog ->
                DogItem(dog = dog, expanded = dog.expanded, onExpanded = {
                    Log.i(TAG, "index num is : ${dogs.indexOf(dog)}")
                    Log.i(TAG, "dog id is : ${dog.id}")
                        dog.expanded = !dog.expanded
                })
            }

        }
    }
}

@Composable
fun DogItem(dog: Dog, modifier: Modifier = Modifier, expanded: Boolean, onExpanded: () -> Unit) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(), elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        ) {
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age, modifier = Modifier.weight(1f))
                DogIconButton(expanded = expanded, onClick = onExpanded)
            }
            Log.i(TAG, "$expanded")
            if (expanded) {
                DogHobby(
                    dog.hobbies
                )
            }
        }
    }
}


@Composable
fun DogIcon(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(64.dp)
            .clip(RoundedCornerShape(50)),
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        contentDescription = null
    )
}

@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(dogName), style = MaterialTheme.typography.h2
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            style = MaterialTheme.typography.body1
        )
    }
}


@Composable
fun DogHobby(@StringRes dogHobby: Int, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.padding(
            start = 16.dp, top = 8.dp, bottom = 16.dp, end = 16.dp
        )
    ) {
        Text(
            text = stringResource(id = R.string.about), style = MaterialTheme.typography.h3
        )
        Text(
            text = stringResource(id = dogHobby), style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun DogIconButton(
    expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {

    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess,
            contentDescription = stringResource(id = R.string.expand_button_content_description),
            tint = MaterialTheme.colors.secondary,
        )
    }
}

fun checkItemIsId(dog: Dog, list: List<Dog>) {
    list.find { (it.id == dog.id) }?.let {}
}



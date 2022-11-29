package com.sc941737.lib.ui_theme

import android.os.SystemClock
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/** Reusable composables */

@Composable
fun DefaultDivider() = Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.alpha(0.25f))

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    allCaps: Boolean = true,
    debounced: Boolean = true,
    onClick: () -> Unit,
) = Button(
    onClick = if (debounced) onClick else { debounced { onClick() } },
    enabled = enabled,
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = Dimensions.large, vertical = Dimensions.medium),
) {
    val formattedText = if (allCaps) text.uppercase() else text
    Text(text = formattedText, textAlign = TextAlign.Center)
}

@Composable
fun DefaultBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_button_content_description),
        )
    }
}

@Composable
fun DefaultToolbarTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.White,
        modifier = Modifier.padding(Dimensions.medium),
    )
}

@Composable
fun DefaultToolbar(
    canNavigateBack: Boolean = true,
    onClickBack: (() -> Unit)? = null,
    @StringRes titleRes: Int,
    actions: @Composable (RowScope.() -> Unit) = {},
) {
    DefaultToolbar(
        title = stringResource(titleRes),
        onClickBack = onClickBack,
        canNavigateBack = canNavigateBack,
        actions = actions,
    )
}

@Composable
fun DefaultToolbar(
    canNavigateBack: Boolean = true,
    onClickBack: (() -> Unit)? = null,
    title: String,
    actions: @Composable (RowScope.() -> Unit) = {},
) {
    val defaultNavIcon: @Composable (() -> Unit) = { DefaultBackButton(onClick = onClickBack ?: {}) }
    val navIconVisible = canNavigateBack && onClickBack != null
    val navIcon: @Composable (() -> Unit)? = if (navIconVisible) defaultNavIcon else null
    TopAppBar(
        navigationIcon = navIcon,
        backgroundColor = MaterialTheme.colors.primary,
        title = { DefaultToolbarTitle(title = title) },
        actions = actions,
    )
}



@Composable
fun SearchToolbar(
    searchTerm: String?,
    onSearchTermChanged: (String) -> Unit,
    onCancelSearchClicked: () -> Unit,
    placeholderText: String = "",
) {
    TopAppBar(
        content = {
            TextField(
                value = searchTerm ?: "",
                onValueChange = onSearchTermChanged,
                placeholder = {
                    Text(
                        text = placeholderText,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,
                        modifier = Modifier.alpha(0.5f),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.medium)
                    .weight(1f),
            )
            IconButton(onClick = onCancelSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_search_content_description),
                    tint = Color.White,
                )
            }
        },
        contentPadding = PaddingValues(Dimensions.medium),
        modifier = Modifier.wrapContentHeight(),
    )
}

fun debounced(
    elapsed: Long = 500L,
    block: () -> Unit,
): () -> Unit {
    val timeBefore = AtomicLong(0)
    return inner@{
        val timeNow = SystemClock.elapsedRealtime()
        if (timeNow - timeBefore.get() < elapsed) return@inner
        timeBefore.set(timeNow)
        block()
    }
}

interface IViewOption {
    @get:StringRes
    val titleStringId: Int
}

@Composable
fun ViewTabList(selectedViewTab: Int, viewTabs: List<IViewOption>, onSelectViewTab: (Int) -> Unit) {
    ViewTabList(
        selectedViewTab = selectedViewTab,
        viewTabs = viewTabs.map { stringResource(it.titleStringId) },
        onSelectViewTab = onSelectViewTab,
    )
}

@JvmName("ViewTabList1")
@Composable
fun ViewTabList(selectedViewTab: Int, viewTabs: List<String>, onSelectViewTab: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = selectedViewTab,
        contentColor = MaterialTheme.colors.primary,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        viewTabs.forEachIndexed { index, tabTitle ->
            Tab(
                selected = selectedViewTab == index,
                onClick = { onSelectViewTab(index) },
                text = {
                    Text(
                        text = tabTitle.uppercase(),
                        maxLines = 1,
                        fontSize = 12.sp,
                    )
                },
            )
        }
    }
}

@Composable
fun <T> Flow<T>.collectAsEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    block: (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onEach(block).flowOn(context).launchIn(this)
    }
}
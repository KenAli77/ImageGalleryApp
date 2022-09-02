package ken.projects.imagegalleryapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import ken.projects.imagegalleryapp.R
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.ui.navigation.Screens
import ken.projects.imagegalleryapp.ui.theme.Cyan
import ken.projects.imagegalleryapp.ui.theme.Purple
import ken.projects.imagegalleryapp.ui.viewmodel.ImageViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: ImageViewModel,
    navHostController: NavHostController,
) =
    with(viewModel) {

        var query by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        Scaffold(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {

                SearchWidget(
                    text = query,
                    onTextChanged = { query = it },
                    onSearchClicked = {
                        searchImages(query)
                        keyboardController?.hide()
                    }
                )

                if (queryImageState.loading) {

                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Purple,
                            strokeWidth = 5.dp,

                            )
                    }


                }

                queryImageState.images?.let {

                    StaggeredGridView(images = it, onImageClick = { img ->
                        setImageDetail(img)
                        navHostController.navigate(Screens.Details.route)

                    })


                }


            }
        }

    }


@Composable
fun SearchWidget(
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        elevation = 10.dp,
        color = Cyan
    ) {

        TextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_text), modifier = Modifier.alpha(
                        0.6f,
                    )
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "search icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty())
                        onTextChanged("")

                }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "search icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked(text)
                focusManager.clearFocus()
            }),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = Color.White
            )

        )

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaggeredGridView(
    images: List<PhotoItem>,
    onImageClick: (PhotoItem) -> Unit,
    onHoldClick: ((PhotoItem) -> Unit)? = null
) {

    Column(

        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            CustomStaggeredVerticalGrid(
                numColumns = 2,
                modifier = Modifier.padding(0.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                images.forEach { img ->

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(

                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GlideImage(
                                imageModel = img.url,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier

                                    .combinedClickable(
                                        onClick = { onImageClick(img) },
                                        onLongClick = {
                                            if (onHoldClick != null) {
                                                onHoldClick(img)
                                            }
                                        }
                                    )

                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(250.dp))
            }
        }


    }
}

@Composable
fun CustomStaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {

    Layout(

        content = content,
        modifier = modifier
    ) { measurable, constraints ->

        val columnWidth = (constraints.maxWidth / numColumns)
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val columnHeights = IntArray(numColumns) { 0 }
        val placeables = measurable.map { measurable1 ->

            val column = shortestColumn(columnHeights)
            val placeable = measurable1.measure(itemConstraints)
            columnHeights[column] += placeable.height
            placeable

        }

        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val columnYPointers = IntArray(numColumns) { 0 }

            placeables.forEach { placeable ->

                val column = shortestColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )

                columnYPointers[column] += placeable.height
            }
        }
    }
}


private fun shortestColumn(columnHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE

    var columnIndex = 0

    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }
    return columnIndex
}


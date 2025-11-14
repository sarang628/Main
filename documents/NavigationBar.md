내비게이션 바는 목적지 화면으로 전환할 수 있는 기능 제공.

작은 화면을 쉬운 전환으로 효율적 사용. 

이전 화면 상태관리 중요하다.


## 목적지 정의

- enum 클래스로 정의
- enum에 속성을 정의하여 아이콘, 타이틀, 그외 내비게이션 시 참조할 만한 항목들을 정의
- enum 클래스 명의 suffix에 Destination을 붙인다. ex)TopLevelDestination, MainDestination
- enum 값 (목적지 명)은 대문자 snake case를 사용한다. ex) FOR_YOU, SONGS
- nia 예제에서는 목적지를 object로 생성하는데 왜 그렇게 만드는지 [참고](https://developer.android.com/guide/navigation/design#compose)


공식 페이지 예제
``` kotlin
enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    SONGS("songs", "Songs", Icons.Default.MusicNote, "Songs"),
    ALBUM("album", "Album", Icons.Default.Album, "Album"),
    PLAYLISTS("playlist", "Playlist", Icons.Default.PlaylistAddCircle, "Playlist")
}
```

Now In Android 코드
``` kotlin
/**
 * Type for the top level destinations in the application. Contains metadata about the destination
 * that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 * @param baseRoute The highest ancestor of this destination. Defaults to [route], meaning that
 * there is a single destination in that section of the app (no nested destinations).
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    FOR_YOU(
        selectedIcon = NiaIcons.Upcoming,
        unselectedIcon = NiaIcons.UpcomingBorder,
        iconTextId = forYouR.string.feature_foryou_title,
        titleTextId = R.string.app_name,
        route = ForYouRoute::class,
        baseRoute = ForYouBaseRoute::class,
    ),
    BOOKMARKS(
        selectedIcon = NiaIcons.Bookmarks,
        unselectedIcon = NiaIcons.BookmarksBorder,
        iconTextId = bookmarksR.string.feature_bookmarks_title,
        titleTextId = bookmarksR.string.feature_bookmarks_title,
        route = BookmarksRoute::class,
    ),
    INTERESTS(
        selectedIcon = NiaIcons.Grid3x3,
        unselectedIcon = NiaIcons.Grid3x3,
        iconTextId = searchR.string.feature_search_interests,
        titleTextId = searchR.string.feature_search_interests,
        route = InterestsRoute::class,
    ),
}
```

## NavHost에 목적지 등록은 어떻게?

각 [목적지]마다 아래 속성들을 만든다.
- enum.entries를 사용하여 enum에서 정의한 순서로 내비게이션바에 항목들을 등록한다. (공홈예제, Now In Android는 개별로 등록)
- [목적지]Navigation.kt 파일을 만든다.
- @Serializable object [목적지] 정의한다.
- NavController.navigateTo[목적지] 확장 함수 정의 - NavController에서 해당 목적지 이동 시 사용
- NavGraphBuilder.[목적지]Screen  확장 함수 정의 - NavHost에 목적지 등록 시 사용 

공식 페이지 예제
```
NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.SONGS -> SongsScreen()
                    Destination.ALBUM -> AlbumScreen()
                    Destination.PLAYLISTS -> PlaylistScreen()
                }
            }
        }
    }
```

Now In Android 코드
```
/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun NiaNavHost(
    appState: NiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = ForYouBaseRoute,
        modifier = modifier,
    ) {
        forYouSection(
            onTopicClick = navController::navigateToTopic,
        ) {
            topicScreen(
                showBackButton = true,
                onBackClick = navController::popBackStack,
                onTopicClick = navController::navigateToTopic,
            )
        }
        bookmarksScreen(
            onTopicClick = navController::navigateToInterests,
            onShowSnackbar = onShowSnackbar,
        )
        searchScreen(
            onBackClick = navController::popBackStack,
            onInterestsClick = { appState.navigateToTopLevelDestination(INTERESTS) },
            onTopicClick = navController::navigateToInterests,
        )
        interestsListDetailScreen()
    }
}
```

Now In Android FeedNavigation.kt
``` 
@Serializable
data object Feed // route to Feed screen

fun NavController.navigateToFeed(navOptions: NavOptions) = navigate(route = Feed, navOptions)

fun NavGraphBuilder.feedScreen(
    padding : PaddingValues                             = PaddingValues(0.dp),
    feed    : @Composable (onChat: () -> Unit) -> Unit  = {},
    state   : MainScreenState
) {
    composable<Feed> {
        val coroutineScope = rememberCoroutineScope()
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray)
        ) {
            feed.invoke {
                coroutineScope.launch {
                    state.goChat()
                }
            }
        }
    }
}
```

## 목적지 선택 및 이동

- @Serializable object [목적지] 을 route 로 등록한 상태
- 화면 이동 파라미터는 enum으로 정의한 MainDestination 타입으로 처리
- when 절을 사용하여 enum에 맞는 화면을 매칭 시킨다.

```
/**
 * UI logic for navigating to a top level destination in the app. Top level destinations have
 * only one copy of the destination of the back stack, and save and restore state whenever you
 * navigate to and from it.
 *
 * @param topLevelDestination: The destination the app needs to navigate to.
 */
fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
    trace("Navigation: ${topLevelDestination.name}") {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            FOR_YOU -> navController.navigateToForYou(topLevelNavOptions)
            BOOKMARKS -> navController.navigateToBookmarks(topLevelNavOptions)
            INTERESTS -> navController.navigateToInterests(null, topLevelNavOptions)
        }
    }
}
```

```
fun NavController.navigateToMainDestination(
    destination     : MainDestination = MainDestination.FEED,
){
    val topLevelNavOptions = navOptions {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

    when (destination){
        MainDestination.FEED        -> navigateToFeed(topLevelNavOptions)
        MainDestination.FEED_GRID   -> navigateToFeedGrid(topLevelNavOptions)
        MainDestination.ADD         -> {}
        MainDestination.FIND        -> navigateToFind(topLevelNavOptions)
        MainDestination.PROFILE     -> navigateToProfile(topLevelNavOptions)
    }
}
```

## Now In Android 는 다른 Scaffold 사용
Navigationd에서 아이콘을 클릭 했을 때, 화면을 이동하는 코드를 찾을 수 없어서
어떻게 동작하는지 보다 샘플 코드인 것을 확인
```
NiaNavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val hasUnread = unreadDestinations.contains(destination)
                val selected = currentDestination
                    .isRouteInHierarchy(destination.baseRoute)
                item(
                    selected = selected,
                    onClick = { appState.navigateToTopLevelDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedIcon,
                            contentDescription = null,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = null,
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) },
                    modifier = Modifier
                        .testTag("NiaNavItem")
                        .then(if (hasUnread) Modifier.notificationDot() else Modifier),
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
    )
```

## 현재 경로 관리는?

- 이전 경로 값을 따로 추가해 관리. previousDestination 
- 현재 경로 값이 null일 경우 이전 경로 값을 사용.
- navController.currentBackStackEntryFlow 으로 현재 경로를 수집한다.
- navController.currentBackStackEntryFlow 이 수집 되는 경로 type은 NavHost에서 등록한 타입으로 반환
- Now In Android와 가이드는 Object로 경로를 등록하기 권장.
- 현재 선택된 경로의 값이 무엇인지 파악하기 어려움.
- NavDestination hasRoute 함수를 이용해 object로 등록한 경로를 현재 선택된 경로를 구하는데 사용.

```
private val previousDestination = mutableStateOf<NavDestination?>(null)
val currentDestination: NavDestination?
    @Composable get() {
        // Collect the currentBackStackEntryFlow as a state
        val currentEntry = navController.currentBackStackEntryFlow
            .collectAsState(initial = null)

        // Fallback to previousDestination if currentEntry is null
        return currentEntry.value?.destination.also { destination ->
            if (destination != null) {
                previousDestination.value = destination
            }
        } ?: previousDestination.value
    }
    
@Composable
fun isSelected(destination: MainDestination) : Boolean {
    return currentDestination
        .isRouteInHierarchy(destination.baseRoute)
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } == true
```


## 참고

[Navigation bar] (https://developer.android.com/develop/ui/compose/components/navigation-bar)

[Navigation with Compose] (https://developer.android.com/develop/ui/compose/navigation)

[Navigation and the back stack] (https://developer.android.com/guide/navigation/backstack)
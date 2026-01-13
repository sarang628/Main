## 기능

### [BottomNavigationView와 NavigationComponent 사용](./documents/NavigationBar.md)

- BottomNavigationView와 NavigationComponent를 연결.
- 최적의 탐색과 백스택 기능 적용.
- 내비게이션 가이드 문서 이해 필요.

내비게이션 구현 코드
```kotlin
@Composable
fun MainNavHost(
    padding             : PaddingValues,
    state               : MainScreenState                               = rememberMainScreenState(),
    feed                : @Composable (onChat: () -> Unit) -> Unit      = {},
    feedGrid            : @Composable () -> Unit                        = {},
    profile             : @Composable () -> Unit                        = {},
    alarm               : @Composable () -> Unit                        = {},
    find                : @Composable () -> Unit                        = {},
) {
    NavHost(
        navController       = state.navController,
        startDestination    = Feed,
        modifier            = Modifier.fillMaxSize()
    ) {
        feedScreen(padding, feed, state)
        feedGridScreen(padding, feedGrid)
        profileScreen(padding, profile)
        findScreen(padding, find)
        alarmScreen(padding, alarm)
    }
}
```
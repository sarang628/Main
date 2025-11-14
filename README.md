# 메인 모듈 (Main Module)

피드, 그리드 형식 피드, 리뷰 추가, 음식점 찾기, 프로필  5가지 화면을 제공하는 모듈

하위 모듈들의 최신 버전을 취합, 유기적으로 잘 동작하는지 확인하는 모듈

## 스크린샷

<img src="screenshots/Screenshot_20220809_094527.png" width="500px"/>

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


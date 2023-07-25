# 토랑 메인화면 모듈
최상단에서 4개의 화면을 제어하는 화면입니다. BottomNavigationView와 Jetpack Navigation
라이브러리를 활용하여 만들었습니다.

이 화면의 주요 구현내용입니다.
 * 피드, 맛집찾기, 알림, 프로필 4개의 화면을 포함합니다.
 * BottomNavigationView 는 https://material.io/components/bottom-navigation/android 문서를 참고하였습니다.
 * Jetpack NavigationComponent 는 https://developer.android.com/guide/navigation/navigation-ui 문서를 참고하였습니다.
 * NavigationComponent

참고: 메인 Activity에서 MainFragment를 로드 할 때에도 FragmentContainerView와 navGraph를 사용해야
탐색이 정상적으로 동작합니다.

## 스크린샷
<img src="screenshots/Screenshot_20220809_094527.png" width="500px"/>

[![Video Label](https://i9.ytimg.com/vi/la73aBBfSik/mq2.jpg?sqp=CPT8xpcG&rs=AOn4CLDFKFPsgwJYXTxN7d3ewBDgfu8DTQ)](https://youtu.be/la73aBBfSik)

## 특징

### BottomNavigationView와 NavigationComponent 사용
BottomNavigationView와 NavigationComponent를 연결하면 자동으로 최적의 탐색과 백스택 기능이 적용됩니다.
위 기능을 구현하는데에는 설정해야하는 사항이 많으므로 충분히 여유를 가지고 하나씩 문서를 확인해야합니다.

### 각 화면 모듈을 취합하여 실제 서비스를 제공 하는 모듈
```
implementation "com.github.sarang628:Feed:$feedVersion"
implementation "com.github.sarang628:Alarm:$alarmVersion"
implementation "com.github.sarang628:Theme:$themeVersion"
implementation "com.github.sarang628:Profile:$profileVersion"
implementation "com.github.sarang628:finding:$findingVersion"
implementation "com.github.sarang628:MyReview:$myReviewVersion"
implementation "com.github.sarang628:Navigation:$navigationModuleVersion"
implementation "com.github.sarang628:TorangDetail:$restaurantVersion"
implementation 'com.github.sarang628:JsonToObjectGenerator:960fe9fece
```


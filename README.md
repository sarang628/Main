# 토랑 (음식점 찾기 서비스)

## 적용기술
- 플랫폼 Android<br>
- 언어 Kotlin<br>

## 개발 특징
- 화면단위 라이브러리 모듈화
- 이미지 선택 라이브러리 개발

## 라이브러리
- Jetpack(빠른 개발)
- Retrofit(Http 통신)
- Room(데이터베이스)
- Hilt(의존성 주입)

### Jetpack
- UI 라이브러리 JetpackCompose
- Navigation 라이브러리 NavigationComnent
- Compose Permission

### 화면단위 라이브러리 모듈화
- 프로젝트 개발 시 화면이 20개 이상 넘어가기 시작하면 빌드속도가 느려져 개발시 어려움
- 보통 앱을 계속 빌드하고 확인하며 개발을 하기때문에 화면을 단위로 나눠서 개발해보기로 결정
- 장점
  - 화면단위로 빠르게 빌드하고 확인 할 수 있음
  - 화면에서 필요로하는 최소한의 데이터와 인터페이스만 포함하도록하여 좀 더 객체지향적으로 개발 가능
- 단점
  - 공통 라이브러리 적용 시 해당 라이브러리가 변경되면 모든 화면 모듈을 업데이트해줘야해서 이를 분리하는 작업을 함.
  - 처음 시도해보는 방식으로 시행착오가 많고. 초기개발 시 잦은 라이브러리 업데이트로 개발 외 작업공수가 많이 듬.


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
```

## 메인 시작 로직 요청


## 로그인 기능 요청
로그인 로직 : Design 모듈 LoginLogic
로그인 로직 구현 : Login 모듈

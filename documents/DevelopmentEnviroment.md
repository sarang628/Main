Main/app/src/main/java/com/sarang/torang/di
git submodule add https://github.com/sarang628/repository.git
git submodule add https://github.com/sarang628/report_di

## 개발 환경

- **개발 대상** Android<br>
- **개발 언어** Kotlin<br>

## 개발 특징

- 화면 단위 라이브러리 모듈화
- 이미지 선택 라이브러리 개발

## 라이브러리

- Jetpack(빠른 개발)
- Retrofit(Http 통신)
- Room(데이터베이스)
- Hilt(의존성 주입)
## 개발환경 설정

### Jetpack

- UI 라이브러리 JetpackCompose
- Navigation 라이브러리 NavigationComnent
- Compose Permission

### 화면단위 라이브러리 모듈화

- 프로젝트 개발 시 화면이 20개 이상 넘어가기 시작하면 빌드 속도가 느려져 개발시 어려움
- 보통 앱을 계속 빌드하고 확인하며 개발을 하기 때문에 화면을 단위로 나눠서 개발해 보기로 결정
- 장점
    - 화면 단위로 빠르게 빌드하고 확인 할 수 있음
    - 화면에서 필요로 하는 최소한의 데이터와 인터페이스만 포함하도록 하여 객체지향적으로 개발 가능
- 단점
    - 공통 라이브러리 적용 시 해당 라이브러리가 변경되면 모든 화면 모듈을 업데이트를 해줘야해서 이를 분리하는 작업을 함.
    - 처음 시도해보는 방식으로 시행착오가 많고. 초기개발 시 잦은 라이브러리 업데이트로 개발 외 작업공수가 많이 듬.
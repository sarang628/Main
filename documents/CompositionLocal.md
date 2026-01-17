# [CompositionLocal](https://developer.android.com/develop/ui/compose/compositionlocal)

- CompositionLocal은 **도구** 이다.
- CompositionLocal은 **데이터를 아래로 통과 시키는** 도구 이다.
- CompositionLocal은 **암시적으로 컴포지션을 통해** 데이터를 아래로 통과 시키는 도구 이다.

Q. 컴포지션을 통해 통과 시킨다는 의미를 모르겠음.
-> 아래 key terms에 composition은 ui tree 나 ui hierarchy와 같은 의미로 쓸 수 있다고 한다.

## [Introduction to CompositionLocal](https://developer.android.com/develop/ui/compose/compositionlocal?utm_source=android-studio-app&utm_medium=app#intro)

- 일반적으로 컴포즈는 트리형태의 UI로 데이터를 파라미터로 아래로 내려보낸다. 이것은 컴포저블의 의존성을 명시하게 한다.
- 하지만 널리쓰이는 파라미터의 경우 (예: 색상, 스타일 등) 많은 컴포저블에 명시해줘야 하기때문에 번거로울 수 있다.
- 이를 해결하기 위해 **CompositionLocal**을 도입
- **트리 범위의 이름 있는 객체(tree-scoped named objects)를 생성**하게 해준다.
- **UI트리에 암시적으로 데이터를 내려주는** 트리 범위의 이름 있는 객체(tree-scoped named objects)를 생성하게 해준다.
- UI의 특정 노드에서 CompositionLocal 요소들을 제공하면 그 자손들은 파라미터로 받지 않고 바로 선언해서 사용 가능
- CompositionLocal의 대표적인 예로 MaterialTheme를 들 수 있음
- CompositionLocal은 UI트리에서 특정 범위로 범주된다. 원하면 트리의 레벨별로 다르게 적용도 가능
- CompositionLocal 생성시 주로 prefix로 Local을 붙임
- CompositionLocal은 composable의 동작을 어렵게 만들 수 있다.
- source of truth가 명확하지 않으므로 디버깅하기 더 어려울 수 있다.


Key terms
 - Composition = UI tree = UI hierarchy
 - composition은 컴포저블 함수의 call graph의 기록.
 - UI tree or UI hierarchy는 composition 프로세스로에서 만들어진 LayoutNode의 트리


## [CompositionLocal 쓸지 고려 방법](https://developer.android.com/develop/ui/compose/compositionlocal?utm_source=android-studio-app&utm_medium=app#deciding)
- CompositionLocal은 좋은 기본값이여야 한다.
- 해당 트리 범주안에 있는 어떤 자식들이라도 사용될 수 있어야 한다. 오직 몇 몇을 위해서만 설정하는것은 좋지 않음. 
# [CompositionLocal](https://developer.android.com/develop/ui/compose/compositionlocal)

CompositionLocal은 **도구** 이다.

CompositionLocal은 **데이터를 아래로 통과 시키는** 도구 이다.

CompositionLocal은 **암시적으로 컴포지션을 통해** 데이터를 아래로 통과 시키는 도구 이다.

Q. 컴포지션을 통해 통과 시킨다는 의미를 모르겠음.

# 컴포지션 소개

일반적으로 컴포즈는 트리형태의 UI로 데이터를 파라미터로 아래로 내려보낸다.
이것은 컴포저블의 의존성을 명시하게 한다.

하지만 널리쓰이는 파라미터의 경우 (예: 색상, 스타일 등) 많은 컴포저블에 명시해줘야 하기때문에 번거로울 수 있다.

이를 해결하기 위해 **CompositionLocal**을 도입

**트리 범위의 이름 있는 객체(tree-scoped named objects)를 생성**하게 해준다.

**UI트리에 암시적으로 데이터를 내려주는** 트리 범위의 이름 있는 객체(tree-scoped named objects)를 생성하게 해준다.

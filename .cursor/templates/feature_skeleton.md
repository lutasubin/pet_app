# Feature skeleton (Screen Pet)

Tạo feature mới `feature/<name>/`:

```
feature/<name>/
├── di/
│   └── <Name>BindModule.kt      # @Binds repository
├── domain/
│   ├── model/
│   ├── repository/              # interface
│   └── usecase/                 # *UseCase.kt + test
├── data/
│   └── DataStore<Name>Repository.kt
└── presentation/
    ├── <Name>Route.kt
    ├── <Name>Screen.kt
    ├── <Name>ViewModel.kt
    └── <Name>UiState.kt
```

## Snippet checklist

- [ ] `@HiltViewModel` + inject use cases only
- [ ] `*UiState` immutable, 1 StateFlow
- [ ] Route: `collectAsStateWithLifecycle`, pass callbacks to Screen
- [ ] Strings trong `res/values/strings.xml`
- [ ] Cập nhật `docs/feature_status.md` và `AGENTS.md` nếu feature lớn

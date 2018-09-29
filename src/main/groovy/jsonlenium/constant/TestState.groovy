package jsonlenium.constant

enum TestState {
    OK(true),
    NG(false),
    CONDITIONAL_NG(false),
    IGNORE(false),
    FATAL(false),
    INFO(true)

    private final boolean value
    TestState(boolean value) { this.value = value }

    def value() { value }
}

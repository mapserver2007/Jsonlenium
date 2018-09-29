package jsonlenium.constant

enum EventWeight {
    UI_TEST(2),
    REST_API_TEST(1),
    META(1),
    CATALYST(10),
    K3C(10),
    BROWSER_ERROR(5),
    CLICK(30),
    PREVENT_DEFAULT_CLICK(35),
    INPUT(5),
    BROWSER_RESTART(50)

    private final int value
    EventWeight(int value) { this.value = value }

    def value() { value }
}

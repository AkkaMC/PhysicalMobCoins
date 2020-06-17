package store.jseries.jhoppers.utils.enums;

import lombok.Getter;

public enum Skin {

    VILLAGER("608bdb53c55fef32a0658e1c7966614af0bff6091249b8fe3b77a0275da82e43"),
    PLUS("2b9f2d4e87a25db4b5ee2f2f1077d7edbec864d991d1fd2feeb08b7841e"),
    X("6f3e8b8f3d98e9322a5a5982efe022635db160a66697b1a11a6d2848a7e06e"),
    REDSTONE_BLOCK("bb78fa5defe72debcd9c76ab9f4e114250479bb9b44f42887bbf6f738612b"),
    REDSTONE_LAMP("a919dd72e38cec369c6508686896ccb84100fd027c4f60a681d16a7640329cc"),
    BOOKSHELF("7f6bf958abd78295eed6ffc293b1aa59526e80f54976829ea068337c2f5e8"),
    BRICK("ce25bc42d41149198c8c523c23920737b9cedda9a99eda53f3232f48964");

    @Getter
    private String url;

    Skin(String id) {
        url = id;
    }

}

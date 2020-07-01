package store.jseries.pmc.utils;

import lombok.Getter;

public enum Skin {

    ONE("4ad0476e8671696af3a8949afa2a814b9bdde65eccd1a8b593aeeff5a0318d"),
    TWO("6b42c4d7efc516fe343d21f149eed17840dd151eb94fbf6dd8d66ca8377dcef7"),
    THREE("1cf4fd6ee8dd9e46a8aa2026bd6d8f2f1984b9e6bca7e3f7dc312333675ab00e"),
    FOUR("3bb612eb495ede2c5ca5178d2d1ecf1ca5a255d25dfc3c254bc47f6848791d8"),
    FIVE("2513d5d588af9c9e98dbf9ea57a7a1598740f21bcce133b9f9aacc67d4faa"),
    SIX("643b80ba12753122719a71cae38222e345225a65f8aad16ae21430fec1a3"),
    SEVEN("fdaebdce8cf35ba9bdb90705635058752030c0be4e658987b75c8ae653301c08"),
    EIGHT("a24f3c846d552cbdc366d8751dd4bfabde60a3adad535c3620b1a0af5d3f553a"),
    NINE("20f2316e455ac3c3fa2e0ed52f305cc4bc363abaff1553bb7170b0dc8368abc1"),
    TEN("f5612dc7b86d71afc1197301c15fd979e9f39e7b1f41d8f1ebdf8115576e2e"),
    ELEVEN("14caafd233d3afd4b6f2132c63a694d012bad6d923316b3aa5c3768fee3339"),
    TWELVE("928e692d86e224497915a39583dbe38edffd39cbba457cc95a7ac3ea25d445"),
    THIRTEEN("31d568e16be6c79d674f97ac1e949f8a8f03e3837b6f0b56a539bfc337f1ebd"),
    FOURTEEN("42e87089f9329b54c9a5965625354107c7f96b3054f1def8ceea2b90ce6f8d"),
    FIFTEEN("49392a2bfa1c4a795bad101797cd54077910c55c1fa8ae55b679e95d2c6e860f"),
    CUSTOM("");

    @Getter
    private String link;

    Skin(String url) {
        link = url;
    }

}

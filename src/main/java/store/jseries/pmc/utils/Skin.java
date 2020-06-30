package store.jseries.pmc.utils;

import lombok.Getter;

public enum Skin {

    PLUS("69b861aabb316c4ed73b4e5428305782e735565ba2a053912e1efd834fa5a6f"),
    ONE("4ad0476e8671696af3a8949afa2a814b9bdde65eccd1a8b593aeeff5a0318d"),
    TWO("1295f02dcfc732b35897efd41c1a9382a03c97ca75ad59524eae3c169f2c"),
    THREE("22868ec6dbdbb6becf698da136e7a6cd28e19314796ce26a3f67d6ab656eb219"),
    FOUR("3bb612eb495ede2c5ca5178d2d1ecf1ca5a255d25dfc3c254bc47f6848791d8"),
    FIVE("2513d5d588af9c9e98dbf9ea57a7a1598740f21bcce133b9f9aacc67d4faa"),
    SIX("25d2f31ba162fe6272e831aed17f53213db6fa1c4cbe4fc827f3963cc98b9"),
    SEVEN("fdaebdce8cf35ba9bdb90705635058752030c0be4e658987b75c8ae653301c08"),
    EIGHT("a24f3c846d552cbdc366d8751dd4bfabde60a3adad535c3620b1a0af5d3f553a"),
    NINE("20f2316e455ac3c3fa2e0ed52f305cc4bc363abaff1553bb7170b0dc8368abc1"),
    TEN("f5612dc7b86d71afc1197301c15fd979e9f39e7b1f41d8f1ebdf8115576e2e"),
    ELEVEN("14caafd233d3afd4b6f2132c63a694d012bad6d923316b3aa5c3768fee3339"),
    TWELVE("928e692d86e224497915a39583dbe38edffd39cbba457cc95a7ac3ea25d445"),
    THIRTEEN("31d568e16be6c79d674f97ac1e949f8a8f03e3837b6f0b56a539bfc337f1ebd"),
    FOURTEEN("42e87089f9329b54c9a5965625354107c7f96b3054f1def8ceea2b90ce6f8d");

    @Getter
    private String link;

    Skin(String url) {
        link = url;
    }

}
package store.jseries.jhoppers.utils.files;

import lombok.Getter;

public class Placeholder {

    @Getter
    private String id;
    @Getter
    private String value;

    public Placeholder(String identifier, String replaced) {
        if(!identifier.startsWith("%"))
            identifier = "%" + identifier;
        if(!identifier.endsWith("%"))
            identifier += "%";
        id = identifier;
        value = replaced;
    }

}

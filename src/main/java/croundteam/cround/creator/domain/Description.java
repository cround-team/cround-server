package croundteam.cround.creator.domain;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.exception.InvalidDescriptionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Description {

    private String description;

    private Description(String description) {
        this.description = description;
    }

    public static Description create(String description) {
        validateDescription(description);
        return new Description(description);
    }

    private static void validateDescription(String description) {
        if(Objects.isNull(description) || description.isBlank()) {
            throw new InvalidDescriptionException(ErrorCode.EMPTY_DESCRIPTION);
        }
    }
}

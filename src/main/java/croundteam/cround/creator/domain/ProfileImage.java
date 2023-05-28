package croundteam.cround.creator.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    private String profileImage;

    private ProfileImage(String profileImage) {
        validatePath();
        this.profileImage = profileImage;
    }

    public static ProfileImage create(String profileImage) {
        return new ProfileImage(profileImage);
    }

    private void validatePath() {

    }
}

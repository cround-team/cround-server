package croundteam.cround.shortform.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortFormUrl {

    @Column(name = "short_form_url", nullable = false)
    private String formUrl;

    private ShortFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public static ShortFormUrl create(String formUrl) {
        return new ShortFormUrl(formUrl);
    }

    public String getShortFormUrl() {
        return formUrl;
    }
}

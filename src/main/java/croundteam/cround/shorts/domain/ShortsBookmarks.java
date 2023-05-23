package croundteam.cround.shorts.domain;

import croundteam.cround.common.InvalidBookmarkException;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.domain.bookmark.ShortsBookmark;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsBookmarks {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shorts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortsBookmark> shortsBookmarks = new ArrayList<>();

    public void addBookmark(Shorts shorts, Member member) {
        ShortsBookmark bookmark = ShortsBookmark.of(shorts, member);
        validateBookmark(bookmark);
        shortsBookmarks.add(bookmark);
    }

    private void validateBookmark(ShortsBookmark bookmark) {
        if(shortsBookmarks.contains(bookmark)) {
            throw new InvalidBookmarkException(ErrorCode.DUPLICATE_BOOKMARK);
        }
    }

    public int getShortsBookmarks() {
        return shortsBookmarks.size();
    }
}

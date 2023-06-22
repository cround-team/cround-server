package croundteam.cround.shortform.domain;

import croundteam.cround.board.exception.InvalidBookmarkException;
import croundteam.cround.bookmark.domain.ShortFormBookmark;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
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
public class ShortFormBookmarks {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shortForm", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortFormBookmark> shortsBookmarks = new ArrayList<>();

    public void addBookmark(ShortForm shortForm, Member member) {
        ShortFormBookmark bookmark = new ShortFormBookmark(shortForm, member);
        validateBookmark(bookmark);
        shortsBookmarks.add(bookmark);
    }

    public void removeBookmark(ShortForm shortForm, Member member) {
        ShortFormBookmark bookmark = new ShortFormBookmark(shortForm, member);
        shortsBookmarks.remove(bookmark);
    }

    private void validateBookmark(ShortFormBookmark bookmark) {
        if(shortsBookmarks.contains(bookmark)) {
            throw new InvalidBookmarkException(ErrorCode.DUPLICATE_BOOKMARK);
        }
    }

    public boolean isBookmarkedBy(ShortForm shortForm, Member member) {
        ShortFormBookmark bookmark = new ShortFormBookmark(shortForm, member);
        return shortsBookmarks.contains(bookmark);
    }

    public int getShortFormBookmarks() {
        return shortsBookmarks.size();
    }
}

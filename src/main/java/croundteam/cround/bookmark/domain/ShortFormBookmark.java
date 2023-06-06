package croundteam.cround.bookmark.domain;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shortform.domain.ShortForm;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "short_form_bookmark")
@EqualsAndHashCode(of = {"shortForm", "member"})
public class ShortFormBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shorts_id", foreignKey = @ForeignKey(name = "fk_short_form_bookmark_to_short_form"))
    private ShortForm shortForm;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_short_form_bookmark_to_member"))
    private Member member;

    public ShortFormBookmark(ShortForm shortForm, Member member) {
        this.shortForm = shortForm;
        this.member = member;
    }
}

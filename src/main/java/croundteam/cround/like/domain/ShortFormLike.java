package croundteam.cround.like.domain;

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
@Table(name = "short_form_like")
@EqualsAndHashCode(of = {"shortForm", "member"})
public class ShortFormLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "short_form_id", foreignKey = @ForeignKey(name = "fk_short_form_to_board"))
    private ShortForm shortForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_short_form_like_to_member"))
    private Member member;

    private ShortFormLike(ShortForm shortForm, Member member) {
        this.shortForm = shortForm;
        this.member = member;
    }

    public static ShortFormLike of(ShortForm shortForm, Member member) {
        return new ShortFormLike(shortForm, member);
    }
}

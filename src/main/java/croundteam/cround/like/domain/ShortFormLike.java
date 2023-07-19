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
@Table(name = "short_form_like",
        uniqueConstraints = @UniqueConstraint(
                name = "short_form_like_short_form_and_member_composite_unique",
                columnNames = {"shorts_id", "member_id"}))
@EqualsAndHashCode(of = {"shortForm", "member"})
public class ShortFormLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shorts_id", foreignKey = @ForeignKey(name = "fk_short_form_to_board"))
    private ShortForm shortForm;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_short_form_like_to_member"))
    private Member member;

    public ShortFormLike(ShortForm shortForm, Member member) {
        this.shortForm = shortForm;
        this.member = member;
    }
}

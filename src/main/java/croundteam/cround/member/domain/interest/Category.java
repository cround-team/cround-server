package croundteam.cround.member.domain.interest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

@AllArgsConstructor
@Getter
public enum Category {
    ART("예술"),
    COOK("요리"),
    BEAUTY("뷰티"),
    STUDY("학습"),
    FASHION("패션"),
    GAME("게임"),
    MUSIC("음악"),
    FINANCE("금융");

    @Column(name = "category_name")
    private String name;
}

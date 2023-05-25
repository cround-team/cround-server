package croundteam.cround.creator.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CustomPageRequest {

    private final int DEFAULT_SIZE = 10;

    private int page;

    public PageRequest toPageRequest() {
        return PageRequest.of(page, DEFAULT_SIZE);
    }
}

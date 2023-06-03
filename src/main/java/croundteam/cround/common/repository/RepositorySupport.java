package croundteam.cround.common.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class RepositorySupport {

    public static <T> Slice<T> convertToSliceFrom(int page, List<T> list, Pageable pageable) {
        boolean hasNext = false;
        if(list.size() == page + 1) {
            list.remove(page);
            hasNext = true;
        }
        return new SliceImpl<>(list, pageable, hasNext);
    }
}

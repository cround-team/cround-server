package croundteam.cround.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static String convertToFormat(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("MM월 dd일 HH:mm").format(localDateTime);
    }
}

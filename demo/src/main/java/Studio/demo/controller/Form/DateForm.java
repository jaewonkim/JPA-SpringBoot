package Studio.demo.controller.Form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
public class DateForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;
}

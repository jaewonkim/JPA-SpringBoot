package Studio.demo.controller.Form;

import Studio.demo.domain.PartTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
public class ReservationForm {

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    private PartTime partTime;
}

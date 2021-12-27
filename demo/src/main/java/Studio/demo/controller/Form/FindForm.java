package Studio.demo.controller.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FindForm {
    @NotEmpty(message = "Do not empty the name box")
    private String name;
    @NotEmpty(message = "Do not empty the phoneNumber box")
    private String phoneNumber;
}

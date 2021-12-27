package Studio.demo.controller.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "Do not empty the id box")
    private String id;
    @NotEmpty(message = "Do not empty the name box")
    private String name;
    @NotEmpty(message = "Do not empty the phoneNumber box")
    private String phoneNumber;
    @NotEmpty(message = "Do not empty the password box")
    private String password;
}

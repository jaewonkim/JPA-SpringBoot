package Studio.demo.controller.Form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class EditForm {
    private String name;
    private String phoneNumber;
    private String password;
}
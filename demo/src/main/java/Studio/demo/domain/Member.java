package Studio.demo.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String name;

    private String phoneNumber;


    @OneToMany(mappedBy = "member",fetch = FetchType.EAGER)
    private List<Reservation> reservationList;

    protected Member(){}

    @Builder
    public Member(String id, String name, String phoneNumber, String password){
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void update(String name,String phoneNumber,String password){
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}

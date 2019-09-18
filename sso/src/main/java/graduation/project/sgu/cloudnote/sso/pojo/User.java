package graduation.project.sgu.cloudnote.sso.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String name;

    private String pwd;

    private String phone;

    private String email;

    private Integer gender;

    private String sculpture;

    private Integer status;


}
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

    private Integer gender;

    private Integer isAdmin;

    private Integer status;


}
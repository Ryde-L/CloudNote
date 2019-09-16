package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Integer id;

    private String name;

    private String pwd;

    private String phone;

    private String email;

    private Integer gender;

    private String sculpture;

    private Integer status;

    private List<NoteBook> noteBookList;

    public User(Integer id, String name, String pwd, String phone, String email, Integer gender, String sculpture, Integer status) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.sculpture = sculpture;
        this.status = status;
    }
}
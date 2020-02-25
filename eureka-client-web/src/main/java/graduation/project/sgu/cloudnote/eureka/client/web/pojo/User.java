package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Integer id;

    private String name;

    private String pwd;

    private Integer gender;

    private String sculpture;

    private Integer status;

    private List<NoteBook> noteBookList;

}
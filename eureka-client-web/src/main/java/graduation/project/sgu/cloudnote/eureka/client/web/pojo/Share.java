package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Share {
    private Integer id;

    private Integer noteId;

    private String link;

    private Integer ishaspwd;

    private String pwd;

    private Integer limitType;

    private Integer limitContent;

    private Date createTime;

    private Integer status;

}
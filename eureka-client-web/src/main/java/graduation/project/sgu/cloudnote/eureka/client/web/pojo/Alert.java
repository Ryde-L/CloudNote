package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    private Integer id;

    private Integer noteId;

    private Integer userId;

    private Date alertTime;

    private Date createTime;

}
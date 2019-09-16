package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecycleBin {
    private Integer id;

    private Integer noteId;

    private Integer noteBookId;

    private String noteTitle;

    private Date throwAwayTime;

    private Date clearTime;

}
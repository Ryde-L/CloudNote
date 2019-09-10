package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteContent {
    private Integer id;

    private Integer noteId;

    private String content;

}
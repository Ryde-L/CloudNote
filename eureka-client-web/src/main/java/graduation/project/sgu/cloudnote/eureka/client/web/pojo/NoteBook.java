package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NoteBook {
    private Integer id;

    private Integer userId;

    private String title;

    private Integer deletable;

    private List<Note> noteList;

    public NoteBook(Integer id, Integer userId, String title, Integer deletable) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.deletable = deletable;
    }
}
package graduation.project.sgu.cloudnote.eureka.client.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private Integer id;

    private Integer noteBookId;

    private String title;

    private NoteBook noteBook;

    private String content;

    public Note(Integer id, Integer noteBookId, String title) {
        this.id = id;
        this.noteBookId = noteBookId;
        this.title = title;
    }
}
package code.DTO;

import code.Domain.Board.BoardEntity;
import code.Domain.User.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardDTO
{
    private int boardNo;
    private String dateTime;

    // 제목과 내용, 댓글
    @Size(min = 0, max = 50)
    @NotEmpty(message = "제목을 확인해주세요.")
    private String title;

    @NotEmpty(message = "내용을 확인해주세요.")
    private String content;
    private String coments;

    // 좋아요와 조회수
    private int likes;
    private int views;

    private int writerNo;
    private String writerName;

    public BoardEntity toEntity()
    {
        UserEntity user = new UserEntity().builder().userNo(writerNo).name(writerName).build();

        return new BoardEntity().builder()
                .boardNo(boardNo)
                .dateTime(dateTime)
                .title(title)
                .content(content)
                .coments(coments)
                .likes(likes)
                .views(views)
                .writer(user)
                .build();

    }

}

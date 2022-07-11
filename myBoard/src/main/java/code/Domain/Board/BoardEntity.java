package code.Domain.Board;

import code.Domain.User.UserEntity;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/*
    ORM : 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)해주는 것을 말한다. JPA는 자바의 표준 ORM이다.
    JPA를 통해 DB를 자동으로 연결하고 쓸 수 있다.

    엔티티는 테이블에서 꺼낸 데이터다.
    엔티티만 갖고는 DB를 참조할 수가 없다.
    리포지토리를 꼭 만들어줘라.

 */

@Entity                     // Entity 임을 알려주는 어노테이션
@Table(name="Board")        // 테이블 명
@Builder                    // 파라미터를 이용하여 빌더 패턴을 만들어줌(따로 공부)
@Getter                     // private 변수들을 대상으로 Get 함수를 만들어준다.
@Setter                     // private 변수들을 대상으로 Set 함수를 만들어준다.
@AllArgsConstructor         // 모든 변수들을 인수로 갖는 생성자를 만들어준다.
@NoArgsConstructor          // 디폴트 생성자를 만들어준다.
@EqualsAndHashCode          // Equals(내용이 같나?)와 Hash(아예 같나?) 함수를 만들어준다.
public class BoardEntity
{
    @Id // 기본 키 (primary key)
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // 생성할 때마다 값이 1씩 증가(기본키니까)
    private int boardNo;
    private String dateTime;

    // 제목과 내용, 댓글
    private String title;
    @Column(length = 5000)
    private String content;
    private String coments;

    // 좋아요와 조회수
    private int likes;
    private int views;
    private String likers; // 중복 좋아요 방지

    @ManyToOne(cascade = CascadeType.DETACH) // 외래 키에 해당하는 것. 작성자 하나(one) 당 여러 개(many)의 게시물을 올릴 수 있음.
    @Nullable
    private UserEntity writer;

    // 조회수를 올려준다. 조회수는 중복해서 올릴 수 있음.
    public int addViews()
    {
        views++;
        return views;
    }

    // 추천수를 올려준다. 만약, 이미 추천했다면(식별자:유저번호) 추천에 실패한다.
    public boolean addLikes(int liker)
    {
        String str = Integer.toString(liker);
        if(likers == null) likers = new String();   // 추천을 누른 사람이 없다면, 배열을 따로 만들어준다.

        // 하나라도 문자열이 같다면 false 반환.
        if(Arrays.stream(likers.split("|")).anyMatch(s->s.equals(str))) //문자열 비교는 꼭 .equals()로 해라..
            return false;
        else
        {
            likers += str + "|";
            likes++;
            return true;
        }
    }

}

package code.Service;

import code.Domain.Board.BoardEntity;
import code.Domain.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService
{
    @Autowired
    final BoardRepository boardRepository;

    // 게시물 생성
    public boolean Create(BoardEntity board)
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowStr = now.format(formatter);

        board.setDateTime(nowStr);

        boardRepository.save(board);

        return true;
    }

    // 게시물을 조회한다.
    public JSONObject Search(int page, int mode, String key, String keyword)
    {
        JSONObject object = new JSONObject();
        // 페이징 시작
        Pageable pageable = PageRequest.of(page, 20,  Sort.by(Sort.Direction.DESC,"board_no"));
        Page<BoardEntity> result = null;

        if(key.equals("title")) result = boardRepository.findByTitle(pageable, keyword, mode);
        else if(key.equals("content")) result = boardRepository.findByContent(pageable, keyword, mode);
        else if(key.equals("no")) result = boardRepository.findByBoardNo(pageable, Integer.parseInt(keyword), mode);
        //else if(key.equals("userName")) result = boardRepository.findByContent(keyword, mode);
        else result = boardRepository.findByTitle(pageable, keyword, mode);
        //////////////////////////////////////////////////////////////////////////////////

        //페이징
        int btncount= 5;
        int startbtn = (page/btncount) * btncount+1;
        int endbtn = startbtn + btncount -1;

        if(endbtn > result.getTotalPages()) endbtn = result.getTotalPages();
        //////////////////////////////////////////////////////////////////////////////////

        // JSON에 페이지 정보를 입력
        JSONArray jsonArray = new JSONArray();
        for(BoardEntity entity : result ){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("boardNo", entity.getBoardNo());
            jsonObject.put("title", entity.getTitle());
            jsonObject.put("likes", entity.getLikes());
            jsonObject.put("views", entity.getViews());
            jsonObject.put("dateTime", entity.getDateTime());
            jsonObject.put("writer", entity.getWriter().getName());

            jsonArray.put(jsonObject);
        }

        object.put( "startbtn" , startbtn );       //  시작 버튼
        object.put( "endhtn" , endbtn );         // 끝 버튼
        object.put( "totalpages" , result.getTotalPages() );  // 전체 페이지 수
        object.put( "data" , jsonArray );  // 리스트를 추가

       return object;
    }

    // 게시물의 상세 정보를 열람한다.
    public JSONObject BoardInfo(int boardNo)
    {
        JSONObject object = new JSONObject();

        // 번호로 불러오기
        BoardEntity b = boardRepository.findByBoardNo(boardNo);

        // 만약 게시물이 조회되지 않았다면 false를 반환
        if(b != null) {
            object.put("flag", "true");
        }
        else
        {
            object.put("flag", "false");
            return object;
        }

        b.addViews(); // 열람했으니, 조회수를 1 올려준다.
        boardRepository.save(b); // 조회수 저장

        // JSON 오브젝트에 정보를 담아주는 단계
        object.put("title", b.getTitle());
        object.put("content", b.getContent());
        object.put("likes", b.getLikes());
        object.put("views", b.getViews());
        object.put("dateTime", b.getDateTime());
        object.put("writer", b.getWriter().getName());
        object.put("writerNo", b.getWriter().getUserNo());

        // 코멘트는 조금 더 설계 후 제대로 사용할 예정.
        // 가공해서 JSON 배열로 프론트에 전달할 예정. 당장에는 사용X
        //object.put("coments", b.getComents());

        return object;
    }

    // 게시물의 추천 수를 늘려준다.
    public JSONObject BoardLike(int writerNo, int boardNo)
    {
        BoardEntity b = boardRepository.findByBoardNo(boardNo);

        JSONObject object = new JSONObject();

        if(b.addLikes(writerNo))
        {   //flag(성공 여부), likes(추천 수)
            object.put("likes", b.getLikes());
            object.put("flag", "success");
            boardRepository.save(b);
        }
        else
        {   // 이미 추천했다면 flag에 메시지를 담아 보내자.
            object.put("flag", "이미 추천했습니다.");
        }

        return object;
    }

    public String Update(int boardNo, String title, String content)
    {
        BoardEntity entity = boardRepository.findByBoardNo(boardNo);

        if(entity == null) return "수정할 수 없습니다.";

        entity.setTitle(title);
        entity.setContent(content);

        try
        {
            boardRepository.save(entity);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Success";
    }

}


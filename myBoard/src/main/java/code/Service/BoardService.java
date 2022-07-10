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

    public boolean Create(BoardEntity board)
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowStr = now.format(formatter);

        board.setDateTime(nowStr);

        boardRepository.save(board);

        return true;
    }

    public String Search(int page, int mode, String key, String keyword)
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

       return object.toString();
    }

}


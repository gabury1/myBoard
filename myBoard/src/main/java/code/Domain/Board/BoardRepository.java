package code.Domain.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    리포지토리는 DB와 직접 연결되는 주체이다.
    쿼리.. 도 작성할 수 있는 듯?? <- 당연히 된다
 */

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity , Integer>
{
    @Query(value="select * from board", nativeQuery = true)
    Page<BoardEntity> findAll(Pageable p);
    @Query(value="select * from board where board_no = :no", nativeQuery = true)
    BoardEntity findByBoardNo(@Param("no") int no);
    @Query(value="select * from board where board_no = :no AND likes >= :likes", nativeQuery = true)
    Page<BoardEntity> findByBoardNo(Pageable p, @Param("no") int no, @Param("likes") int likes);
    @Query(value="select * from board where title like %:title% AND likes >= :likes", nativeQuery = true)
    Page<BoardEntity> findByTitle(Pageable p, @Param("title") String title, @Param("likes") int likes);
    @Query(value="select * from board where content like %:content% AND likes>= :likes", nativeQuery = true)
    Page<BoardEntity> findByContent(Pageable p, @Param("content") String content,  @Param("likes") int likes);


    //List<BoardEntity> findByWrite();  // 외래키 검색 찾아보셈
}

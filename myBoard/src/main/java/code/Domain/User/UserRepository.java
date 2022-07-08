package code.Domain.User;

import code.Domain.Board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
    리포지토리는 DB와 직접 연결되는 주체이다.
    제발.. 쿼리명은 필드명으로 해주세요오오오
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>
{
    // findBy + 필드명(제발 필드명은 소문자로 해주세요.)
    //Optional<UserEntity> findByName(String name);
    UserEntity findById(String Id);
}

//   JpaRepository      [ CRUD ]
// 1. findAll() : 모든 엔티티 호출
// 3. findbyId( pk값 ) : 해당 pk의 엔티티 호출
// 3. save( 엔티티 ) : 해당 엔티티를 DB 레코드 추가
// 4. delete( 엔티티 ) : 해당 엔티티를 삭제 처리
// ?????????? 수정은 업다 ~~~ [ 매핑된 엔티티는 JPA 자동감지 지원 ]
// 엔티티를 수정하면 자동으로 DB 수정된다.
//래찬펌



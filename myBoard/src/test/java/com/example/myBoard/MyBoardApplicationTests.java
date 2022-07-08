package com.example.myBoard;

import code.DTO.BoardDTO;
import code.DTO.UserDTO;
import code.Domain.Board.BoardEntity;
import code.Service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class MyBoardApplicationTests {

	@Autowired
	BoardService boardService;

	@Test
	void testBoard()
	{
		for(int i = 0; i < 300; i++)
		{

		}


	}

}

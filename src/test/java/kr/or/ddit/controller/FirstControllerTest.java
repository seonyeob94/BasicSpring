package kr.or.ddit.controller;

import kr.or.ddit.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//해당 클래스를 스프링 부트와 연동해 테스트. 이렇게 하면
//  테스트 코드에서 스프링 부트가 관리하는 다양한 객체를 주입받을 수 있음
@SpringBootTest
class FirstControllerTest {

    //DI
    @Autowired
    ArticleRepository articleRepository;

    //index() 메서드가 테스트 코드임을 선언
    @Test
    void index() {
        //1. 예상 데이터

        //2. 실제 데이터


        //3. 비교 및 검증

    }
}
package kr.or.ddit.service.impl;

import kr.or.ddit.entity.Article;
import kr.or.ddit.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//해당 클래스를 스프링 부트와 연동해 테스트. 이렇게 하면
//테스트 코드에서 스프링 부트가 관리하는 다양한 객체를 주입받을 수 있음

@SpringBootTest
class ArticleServiceImplTest {
    @Autowired
    private ArticleService articleService;

    //해당 메서드가 테스트 코드임을 선언
    @Test
    void findAll() {
        //1. 예상 데이터
        //예상 데이터 객체로 저장
        Article a = new Article(1L,"개똥이의 여행", "즐거운 여행");
        Article b = new Article(2L,"개똥이의 여행2", "즐거운 여행2");
        Article c = new Article(3L,"개똥이의 여행3", "즐거운 여행3");

        //Arrays.asList() 메서드로 합친 정적 리스트를 새 ArrayList로 만들어 expected에 저장함.
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));
        System.out.println("expected : " + expected );

        //2. 실제 데이터
        // 실제 데이터는 articleService.index() 메서드를 호출해 그 결과를
        //  List<Article> 타입의 articles에 받아옴.
        // 모든 게시글을 조회 요청하고 그 결과로 반환되는 게시글의 묶음을 받아 옴
        List<Article> articles = this.articleService.findAll();
        System.out.println("articles : " +articles);
        //3. 비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    //상세 글 로직이 잘 되는지 테스트
    @Test
    void findById() {
        //1. 예상 데이터
        //예상 데이터는 존재하지 않는 id인 -1을 조회한다고 가정해보자
        Long id = 2L;//2번글(2	즐거운 여행22	개똥이의 여행2)

        //예상 데이터 저장
        //이 경우 DB에서 조회되는 내용이 없어 null을 반환할 것이므로 expected 객체에 null을 저장함
        Article expected = new Article(id, "개똥이의 여행2", "즐거운 여행2");
        System.out.println("expected : " + expected);

        //2. 실제 데이터
        Article article =  this.articleService.findById(id);
        System.out.println("articles : " + article);

        //3. 비교 및 검증
        //실제 데이터와 예상 데이터의 값 null은 toString() 메서드를 호출할 수 없으므로
        //  첫 번째와 두 번째 전달값은 expected와 article을 사용함
        assertEquals(expected.toString(), article.toString());
    }
}
package kr.or.ddit.api;

import kr.or.ddit.dto.ArticleForm;
import kr.or.ddit.entity.Article;
import kr.or.ddit.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
REST 컨트롤러 : JSON이나 텍스트 같은 데이터 반환
일반 컨트롤러 : 뷰 페이지(jsp, mustache, thymeleaf) 반환(forwarding)
 */
@RequestMapping("/api")
@Slf4j
@RestController
public class FirstApiController {

    /*
    요청URI : /api/hello
    요청파라미터 :
    요청방식 : get
     */
    @GetMapping("/hello")
    public String  hello(){
        return "hello world!";
    }

    //(의존성 주입)
    @Autowired
    private ArticleRepository articleRepository;

    /* 글 목록
    요청URI : /api/articles
    요청파라미터 :
    요청방식 : get
     */
    @GetMapping("/articles")
    public List<Article> index(){
        //메서드 수행 결과로 Article 묶음을 반환하므로 반환형이 List<Article>임
        //.findAll() 메서드 : DB에 저장된 모든 Article을 가져와 반환 함.
        List<Article> articleList = this.articleRepository.findAll();
        log.info("index->articleList : " + articleList);

        //데이터 응답
        return articleList;
    }

    /* 단일(하나의) 게시글 조회
     요청URI : /api/articles/2
     경로변수 : id
     요청방식 : GET

     private Long id;
      */
    //RestController이므로 ResponseBody 애너테이션이 생략됨
    @GetMapping("/articles/{id}")
    public Article show(@PathVariable(value="id") Long id){
        Article article = this.articleRepository.findById(id).orElse(null);
        log.info("show->article : " + article);
        //데이터 응답
        return article;
    }

    /*
    요청URI : /articles/update
    요청파라미터 : JSON Stirng{id=2,title=개똥이개똥이,content=즐거워즐거워}
    요청방식 : post

    URL 요청 접수
    매개변수로 DTO 받아 오기
     */
    @PostMapping("/articles/update")
    public Article update(@RequestBody ArticleForm form){
        //{id=2, title=개똥이의 여행22, content=즐거운 여행22}
        log.info("update->form : " + form);
        //        1. DTO를 엔티티로 변환
        //DTO(form)를 엔티티(articleEntity)로 변환
        Article articleEntity = form.toEntity();
        //articleEntity{id=2, title=개똥이의 여행22, content=즐거운 여행22}
        log.info("update->articleEntity : " + articleEntity);

        //        2. 엔티티를 DB에 저장
        //2-1. DB에서 기존 데이터 가져오기(검증)
        Article target = this.articleRepository.findById(articleEntity.getId()).orElse(null);
        log.info("update->target : " + target);

        //2-2. 기존 데이터 값을 갱신하기
        //엔티티를 DB에 저장(갱신)
        if(target != null){//검증완료
            //articleEntity{id=2, title=개똥이의 여행22, content=즐거운 여행22}
            this.articleRepository.save(articleEntity);
        }

        //   데이터
        //articleEntity{id=2, title=개똥이의 여행22, content=즐거운 여행22}
        return target;
    }

}

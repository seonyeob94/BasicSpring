package kr.or.ddit.api;

import kr.or.ddit.entity.Article;
import kr.or.ddit.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package kr.or.ddit.controller;


import kr.or.ddit.dto.ArticleForm;
import kr.or.ddit.entity.Article;
import kr.or.ddit.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/*
애너테이션(annotation)이란?
소스 코드에 추가해 사용하는 메타 데이터의 일종.
메타 데이터는 프로그램에서 처리해야 할 데이터가 아니라 컴파일 및 실행 과정에서 코드를 어떻게
처리해야 할지 알려 주는 추가 정보. 자바에서 어노테이션은 앞에 @기호를 붙여 사용.
 */
//스프링 프레임워크에서 이 클래스는 특별한 클래스임을 알려줌
//자바빈(객체)으로 등록한 후 메모리에 미리 new를 해서 업로드 함
//시기-서버가 기동했을 때
//console참에 썰을 풀어보자
@Slf4j
@Controller
public class FirstController {

    //리파지터리 객체 선언
    //의존성 주입(Dependency Injection)-DI
    @Autowired
    private ArticleRepository articleRepository;


    //메서드 작성
    @GetMapping("/hi")
    public ModelAndView niceToMeetYou(){
        //Model : 데이터(String, int, List, Map, Vo..)
        //View : 뷰의 경로
        ModelAndView mav = new ModelAndView();
        //View : mustache, jsp, thymeleaf
        mav.setViewName("greetings");

        //model 객체가 "선엽" 값을 "username"속성에 연결해 웹 브라우저로 보냄
        mav.addObject("username","선엽");
        mav.addObject("myfriend","홍길동");
       //greetings.mustache 파일을 반환
        return mav;
    }
    //요청URI : /bye
    //요청파라미터 :
    //요청방식 : get
    //URL 요청 접수
    //1. 컨트롤러는 골뱅이Controller 내부에 골뱅이GetMapping 어노테이션을 통해
    // 클라이언트의 요청을 받음
    @GetMapping("/bye")
    public String seeYouNext(Model model){//2. 메서드 작성 : /bye 요청을 처리
        //3. goodbye.mustache  변환
        //반환값은 요청에 따라 보여 줄 뷰 템플릿 페이지를 적음
        //model.addObject("속성","값")
        model.addAttribute("nickname","김유신");

        // mav.setViewName("goodbye")
        // /template/goobye.mustache를 포워딩
        return "goodbye";
    }

    /*
    요청URI : /articles/new
    요청파라미터 :
    요청방식 : get
    */
    //2. URL 요청 접수
    //뷰 페이지를 보여 주기 위해 newArticleForm() 메서드를 추가함
    @GetMapping("/articles/new")
    public String  newArticleForm(){//3. 메서드 생성
        //4. 반환값 작성 : 뷰 페이지의 이름을 적음.
        // articles 디렉터리를 만들고 new.mustache 뷰 페이지를 추가했으므로
        // 파일 경로까지 포함해서 입력
        //뷰리졸버 조립 : /template/ + articles/new + .mustache
        // (해석)new_mustache.java -> (컴파일)mew_mustache.class
        // (처리완료)html => 포워딩
        return "articles/new";
    }

    /*
    요청URI : /articles/create
    요청파라미터 : request{title=개똥이의 모험,content=즐거운 모험}
    요청방식 : post
     */
    // GetMapping 대신 PostMapping 애너테이션을 사용함.
    // 뷰 페이지에서 폼 데이터를 post 방식으로 전송했으므로 컨트롤러에서 받을 때도
    // PostMapping으로 받음. 이 때 괄호 안에는 받는 URL 주소를 넣음.
    // new.mustache에서 <form> 태그에 action="/articles/create"로 설정했음을 기억하자
    @PostMapping("/articles/create")   
    public String createArticle(ArticleForm form){
        log.info("createArticle에 왔다");

        //폼에서 전송한 데이터를 매개변수로 받아옴.
        // DTO로 만든 클래스 이름이 ArticleForm이므로
        // ArticleForm 타입의 form 객체를 매개변수로 선언
        //폼에서 전송한 데이터가 DTO에 잘 담겼는지 확인하기 위해 출력문을 추가
        //로깅을 사용하기 위해 클래스 명 위에 Slf4j를 사용함.
        log.info("createArticle->form : " + form);//DTO

        //1. DTO(ArticleForm)를 엔티티(Article)로 변환
        Article article = form.toEntity();
        log.info("createArticle->article : " + article);//Entity

        //2. 리파지터리로 엔티티를 DB에 저장(method)
        //article 엔티티를 H2 DBMS에 insert 된 후 그 행을 객체형태로 반환(그 행 자체)
        //vs MyBATIS의 경우 return type은 int 타입 반환(행의 수에 초점)
        Article saved = this.articleRepository.save(article);
        //Article(id=1, title=개똥이의 모험1, content=즐거운 모험1)
        log.info("createArticle->saved : " + saved);//Entity
        System.out.println("createArticle->saved : " + saved);//Entity

        //기존 : get방식으로 /articles/new URL을 재요청
        //해당 글이 잘 등록됐음을 알려 주는 상세 페이지가 나올 수 있도록 리다이렉트 개념을 적용해보자
        return "redirect:/articles/"+saved.getId();
    }

    //데이터 조회 요청 접수
    /*
    요청 URI : /articles/1
    경로(Path) 변수(Variable) : id
    요청 방식 : get
     */
    @GetMapping("/articles/{id}")
    public String show(@PathVariable(value = "id") Long id,
                       Model model){//매개변수로 id 받아오기
        //id를 잘 받았는지 확인하는 로그 찍기
        log.info("show->id : {}", id);
        log.info("show->id : " + id);
        //1. id를 조회해 데이터 가져오기
        // findById()는 JPA의 CrudRepository가 제공하는 메서드로, 특정 엔티티의 id 값을 기준으로
        //  데이터를 찾아 Optional 타입으로 반환.
        // orElse(null) : id 값으로 데이터를 찾을 때 해당 id 값이 없으면 null을 반환.
        // 데이터를 조회한 결과, 값이 있으면 articleEntity 변수에 값을 넣고 없으면
        //  null을 저장
        //articleEntity{id=1,content=소똥이,title=개똥이}
        Article articleEntity = this.articleRepository.findById(id).orElse(null);// 1번글
        log.info("show->articleEntity : " + articleEntity);
        //2. 모델에 데이터 등록하기
        // article이라는 이름으로 value인 articleEntity 객체 추가
        model.addAttribute("article", articleEntity);
        //3. 뷰 페이지 반환하기
        // 뷰 페이지는 articles라는 디렉터리 안에 show라는 파일이 있다는 의미
        //forwarding : mustache
        return "articles/show";
    }

    /* 글 목록
    요청URI : /articles
    요청파라미터 :
    요청방식 : get
     */
    @GetMapping("/articles")
    public String index(Model model){//index() 메소드의 매개변수로 : model 객체를 받아옴
        //1. 모든 데이터 가져오기
        //findAll() 메서드의 반환 데이터 타입은 Iterable. List라서 불일치
        //첫째, 캐스팅(형변환). Iteratable, Collection, LList 인터페이스의 상하 관계는 Iteratable이 가장 상위 인터페이스
        // Iteratable이 가장 상위 인터페이스
        ArrayList<Article> articleEntityList = this.articleRepository.findAll();
        //2. 모델에 데이터 등록하기(쌍쌍 쉼 쌍쌍)
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정하기
        // articles 디렉터리 안에 index.mustache 파일이 뷰 페이지로 설정
        //forwarding : mustache
        return "articles/index";
    }
}

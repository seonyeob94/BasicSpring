package kr.or.ddit.controller;


import kr.or.ddit.dto.ArticleForm;
import kr.or.ddit.entity.Article;
import kr.or.ddit.repository.ArticleRepository;
import kr.or.ddit.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private ArticleService ariticleService;

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

    //URL 요청 접수
    /*
     요청URI : /articles/3/edit
    경로변수(PathVariable) : id
    요청방식 : get
     */
    //URL 주소에 있는 id를 받아오는 것이므로 데이터 타입 앞에 @PathVariable 애너테이션을 추가
    //메서드 생성 및 뷰 페이지 설정
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable(value = "id") Long id, Model model){
        log.info("eidt->id" + id);
        //H2 DB(공유, 저장, 통합, 운영)MS에서 수정할 데이터 가져오기
        //DB에서 수정할 데이터 가져오기
        //DB에서 데이터를 가져올 때는 리파지터리를 이용.
        //만약 데이터를 찾지 못하면 null을 반환, 데이터를 찾았다면 Article 타입의 articleEntity로 작성함
        Article articleEntity = this.articleRepository.findById(id).orElse(null);
        //모델에 데이터 등록하기
        //articleEntity를 article로 등록
        model.addAttribute("article",articleEntity);

        //뷰 페이지 설정
        // /templates/articles/edit.mustache 포워딩
        return "articles/edit";

    }


    /*
    요청URI : /articles/update
    요청파라미터 : request{id=2,title=개똥이개똥이,content=즐거워즐거워}
    요청방식 : post

    URL 요청 접수
    매개변수로 DTO 받아 오기
     */
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info("update->form : " + form);
        //        1. DTO를 엔티티로 변환
        //DTO(form)를 엔티티(articleEntity)로 변환
        Article articleEntity = form.toEntity();
        log.info("update->articleEntity : " + articleEntity);

        //        2. 엔티티를 DB에 저장
        //2-1. DB에서 기존 데이터 가져오기(검증)
        Article target = this.articleRepository.findById(articleEntity.getId()).orElse(null);
        log.info("update->target : " + target);

        //2-2. 기존 데이터 값을 갱신하기
        //엔티티를 DB에 저장(갱신)
        if(target != null){//검증완료
            this.articleRepository.save(articleEntity);
        }
        //        3. 수정 결과 페이지로 리다이렉트(상세 보기) : 새로운 URI를 재요청
        return "redirect:/articles/"+articleEntity.getId();
    }

    /* 글 삭제
    요청URI : /articles/3/delete
    경로변수 : id(Article 타입의 엔티티의 기본키-식별자)
    요청방식 : get

    @PathVariable 어노테이션을 사용하여 URL경로의 id 값을 Long 타입의 id 매개변수로 가져옵니다
    RediractAttributes는 리다이렉트 시 데이터를 전송하기 위해 사용됩니다
     */
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes rttr){
        // 로그: 삭제 요청이 수신되었음을 기록합니다.
        log.info("삭제요청이 들어옴. id" + id);

        //1) 삭제할 대상 가져오기
        // articleRepository를 사용하여 전달받은 id로 Article 엔티티를 데이터베이스에서 조회합니다.
        // 만약 해당 id의 엔티티가 존재하지 않으면 null을 반환합니다.
        // 로그: 조회된 삭제 대상 엔티티 정보를 기록합니다.
        Article target = this.articleRepository.findById(id).orElse(null);
        log.info("delete->target : " + target);

        //2) 대상 엔티티 삭제하기
        //삭제할 대상이 있는지 확인
        // 조회된 엔티티(target)가 null이 아닌지, 즉 삭제할 대상이 존재하는지 확인합니다.
        if(target != null) {
            //delete() 메서드로 대상 삭제
            // articleRepository의 delete() 메소드를 호출하여 데이터베이스에서 해당 엔티티를 삭제합니다.
            this.articleRepository.delete(target);
            // RedirectAttributes 객체에 "msg"라는 이름으로 "삭제했습니다"라는 메시지를 추가합니다.
            // 이 메시지는 리다이렉트된 페이지에서 한 번만 사용할 수 있는 일회성 데이터입니다.
            // 이해비법 : redirect 시 상용되는 model(forwarding 시 사용)이라고 생각해도 됨
            rttr.addFlashAttribute("msg","삭제했습니다");

        }
        //3) 결과 페이지로 리다이렉트하기
        // 글 목록 페이지(/articles)로 리다이렉트(새로운 URi를 재요청)합니다.
        return "redirect:/articles";
    }

}

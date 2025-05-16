package kr.or.ddit.controller;

import kr.or.ddit.dto.LprodForm;
import kr.or.ddit.entity.Lprod;
import kr.or.ddit.repository.LprodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Controller
public class LprodController {

    //DI(Dependency Injection)
    @Autowired
    private LprodRepository lprodRepository;

    @GetMapping("/lprod/new")
    public String newArticleForm(){

        return "lprod/new";
    }
    /*
    DTO 이외의 매개변수로 request객체 안에 있는 파라미터를 받아보자
    스프링프레임워크에서 '1'라는 숫자형 문자 파라미터값을 int 타입의 매개변수로
        자동형변환하여 받을 수 있음
    요청URI : /lprod/create
    요청파라미터 : request{lprodId=1,lprodGu=P101,lprodNm=컴퓨터제품}
    요청방식 : post
     */
    @PostMapping("/lprod/create")
    public String createLprod(LprodForm form,
                              Long lprodId, String lprodGu, String lprodNa,
                              Map<String,Object> map){

        log.info("createLprod->form : " + form);
        log.info("createLprod->lprodId : " + lprodId);
        log.info("createLprod->lprodGu : " + lprodGu);
        log.info("createLprod->lprodNa : " + lprodNa);
        log.info("createLprod->map : " + map);

        //1. DTO를 엔티티로 변환
        Lprod lprod = form.toEntity();
        log.info("createLprod->lprod : " + lprod);

        //2. 리파지터리로 엔티티를 DB에 저장
        Lprod saved = this.lprodRepository.save(lprod);
        log.info("createLprod->saved : " + saved);


        return "redirect:/lprod/"+saved.getLprodId();
    }
    /*
    요청URI : /lprod/createAjax
    요철파라미터 : JSON String{"lprodId":"1","lprodGu":"P101","lprodNa":"컴퓨터제품"}
    요청방식 : post
     */
    @ResponseBody
    @PostMapping("/lprod/createAjax")
    public String createLprodAjax(@RequestBody LprodForm form){
        log.info("createLprod->form : " + form);

        //데이터
        return "success";
    }

    /*
    요청 URI : /lprod/1
    요청 파라미터 :
    요청 방식 : get
     */
    @GetMapping("/lprod/{lprodId}")
    public String show(@PathVariable(value = "lprodId") Long lprodId,
                       Model model){
        //lprodId를 잘 받았는지 확인하는 로그 찍기
        log.info("show->lprodId : {}", lprodId);
        log.info("show->lprodId : " + lprodId);

        //1. lprodId를 조회해 데이터 가져오기
        // findById()는 JPA의 CrudRepository가 제공하는 메서드로, 특정 엔티티의 lprodId 값을 기준으로
        //  데이터를 찾아 Optional 타입으로 반환.
        //orElse(null) : lprodId 값으로 데이터를 찾을 때 해당 lprodId 값이 없으면 null을 반환.
        // 데이터를 조회한 결과, 값이 있으면 lprodEntity 변수에 값을 넣고 없으면
        //  null을 저장
        Lprod lprodEntity = this.lprodRepository.findById(lprodId).orElse(null);
        log.info("show->lprodEntity : " + lprodEntity);
        //2. 모델에 데이터 등록하기
        //lprod이라는 이름으로 value인 lprodEntity 객체 추가
        model.addAttribute("lprod", lprodEntity);
        //3. 뷰 페이지 반환하기
        // 뷰 페이지는 lprod라는 디렉터리 안에 show라는 파일이 있다는 의미
        return "lprod/show";

    }
    /* 글 목록
    요청URI : /lprod
    요청파라미터 :
    요청방식 : get
     */
    @GetMapping("/lprod")
    public String index(Model model){
        //1. 모든 데이터 가져오기
        //findAll() 메서드의 반환 데이터 타입은 Iterable. List라서 불일치
        //첫째, 캐스팅(형변환). Iteratable, Collection, LList 인터페이스의 상하 관계는 Iteratable이 가장 상위 인터페이스
        // Iteratable이 가장 상위 인터페이스
        ArrayList<Lprod> lprodEntityList=this.lprodRepository.findAll();
        //2. 모델에 데이터 등록하기(쌍쌍 쉼 쌍쌍)

        model.addAttribute("lprodList",lprodEntityList);
        //3. 뷰 페이지 설정하기
        // articles 디렉터리 안에 index.mustache 파일이 뷰 페이지로 설정
        //forwarding : mustache
        return "lprod/index";
    }

    //URL 요청 접수
    /*
     요청URI : /lprod/3/edit
    경로변수(PathVariable) : lprodId
    요청방식 : get
     */
    //URL 주소에 있는 id를 받아오는 것이므로 데이터 타입 앞에 @PathVariable 애너테이션을 추가
    //메서드 생성 및 뷰 페이지 설정
    @GetMapping("/lprod/{lprodId}/edit")
    public String edit(@PathVariable(value = "lprodId") Long lprodId, Model model){
        log.info("eidt->lprodId" + lprodId);
        //H2 DB(공유, 저장, 통합, 운영)MS에서 수정할 데이터 가져오기
        //DB에서 수정할 데이터 가져오기
        //DB에서 데이터를 가져올 때는 리파지터리를 이용.
        //만약 데이터를 찾지 못하면 null을 반환, 데이터를 찾았다면 Lprod 타입의 articleEntity로 작성함
        Lprod lprodEntity = this.lprodRepository.findById(lprodId).orElse(null);
        //모델에 데이터 등록하기
        //articleEntity를 article로 등록
        model.addAttribute("lprod",lprodEntity);

        //뷰 페이지 설정
        // /templates/lprod/edit.mustache 포워딩
        return "lprod/edit";

    }


    /*
    요청URI : /lprod/update
    요청파라미터 : request{id=2,title=개똥이개똥이,content=즐거워즐거워}
    요청방식 : post

    URL 요청 접수
    매개변수로 DTO 받아 오기
     */
    @PostMapping("/lprod/update")
    public String update(LprodForm form){
        log.info("update->form : " + form);
        //        1. DTO를 엔티티로 변환
        //DTO(form)를 엔티티(articleEntity)로 변환
        Lprod lprodEntity = form.toEntity();
        log.info("update->lprodEntity : " + lprodEntity);

        //        2. 엔티티를 DB에 저장
        //2-1. DB에서 기존 데이터 가져오기(검증)
        Lprod target = this.lprodRepository.findById(lprodEntity.getLprodId()).orElse(null);
        log.info("update->target : " + target);

        //2-2. 기존 데이터 값을 갱신하기
        //엔티티를 DB에 저장(갱신)
        if(target != null){//검증완료
            this.lprodRepository.save(lprodEntity);
        }
        //        3. 수정 결과 페이지로 리다이렉트(상세 보기) : 새로운 URI를 재요청
        return "redirect:/lprod/"+lprodEntity.getLprodId();
    }

    /* 글 삭제
    요청URI : /lprod/3/delete
    경로변수 : lprodId(Lprod 타입의 엔티티의 기본키-식별자)
    요청방식 : get

    @PathVariable 어노테이션을 사용하여 URL경로의 lprodId 값을 Long 타입의 lprodId 매개변수로 가져옵니다
    RediractAttributes는 리다이렉트 시 데이터를 전송하기 위해 사용됩니다
     */
    @GetMapping("/lprod/{lprodId}/delete")
    public String delete(@PathVariable(value = "lprodId") Long lprodId,
                         RedirectAttributes rttr){
        // 로그: 삭제 요청이 수신되었음을 기록합니다.
        log.info("삭제요청이 들어옴. id" + lprodId);

        //1) 삭제할 대상 가져오기
        // articleRepository를 사용하여 전달받은 id로 Article 엔티티를 데이터베이스에서 조회합니다.
        // 만약 해당 id의 엔티티가 존재하지 않으면 null을 반환합니다.
        // 로그: 조회된 삭제 대상 엔티티 정보를 기록합니다.
        Lprod target = this.lprodRepository.findById(lprodId).orElse(null);
        log.info("delete->target : " + target);

        //2) 대상 엔티티 삭제하기
        //삭제할 대상이 있는지 확인
        // 조회된 엔티티(target)가 null이 아닌지, 즉 삭제할 대상이 존재하는지 확인합니다.
        if(target != null) {
            //delete() 메서드로 대상 삭제
            // articleRepository의 delete() 메소드를 호출하여 데이터베이스에서 해당 엔티티를 삭제합니다.
            this.lprodRepository.delete(target);
            // RedirectAttributes 객체에 "msg"라는 이름으로 "삭제했습니다"라는 메시지를 추가합니다.
            // 이 메시지는 리다이렉트된 페이지에서 한 번만 사용할 수 있는 일회성 데이터입니다.
            // 이해비법 : redirect 시 상용되는 model(forwarding 시 사용)이라고 생각해도 됨
            rttr.addFlashAttribute("msg","삭제했습니다");

        }
        //3) 결과 페이지로 리다이렉트하기
        // 글 목록 페이지(/lprod)로 리다이렉트(새로운 URi를 재요청)합니다.
        return "redirect:/lprod";
    }


}

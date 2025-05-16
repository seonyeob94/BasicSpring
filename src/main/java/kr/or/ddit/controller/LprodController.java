package kr.or.ddit.controller;

import kr.or.ddit.dto.LprodForm;
import kr.or.ddit.entity.Lprod;
import kr.or.ddit.repository.LprodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


}

package kr.or.ddit.api;

import kr.or.ddit.dto.LprodForm;
import kr.or.ddit.entity.Lprod;
import kr.or.ddit.repository.LprodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//RestController : 데이터를 응답

@Slf4j
@RestController
public class LprodApiController {

    //DI / IoC(제어의 역전)
    @Autowired
    LprodRepository lprodRepository;
    //비동기 목록 출력
    /*
    요청URI : /api/lprod
    요청파라미터 :
    요청방식 : post
    */
    //RestController에 의해서 ResponseBody 에너테이션 생략
    @PostMapping("/api/lprod")
    public List<Lprod> lprods(){
        List<Lprod> lprodList = this.lprodRepository.findAll();
        log.info("lprods->lprodList : " + lprodList);
        //데이터
        return lprodList;
    }
    /*
    요청URI : /api/lprod/1
    요청파라미터 : lprodId
    요청방식 : get
    */
    @GetMapping("/api/lprod/{lprodId}")
    public Lprod show(@PathVariable(value = "lprodId")Long lprodId){
        log.info("show->lprodId : " + lprodId);
        //SELECT LPROD_ID, LPROD_GU, LPROD_NM FROM LPROD WHERE LPROD_ID = 2;
        Lprod lprodEntity = this.lprodRepository.findById(lprodId).orElse(null);
        log.info("show->lprodEntity : " + lprodEntity);
        //데이터 응답(JSON String)
        return lprodEntity;
    }

    /*
    요청URI : /lprod/update
    요청파라미터 : JSON Stirng{id=2,title=개똥이개똥이,content=즐거워즐거워}
    요청방식 : post

    URL 요청 접수
    매개변수로 DTO 받아 오기
     */
    @PostMapping("/api/lprod/update")
    public Lprod update(@RequestBody LprodForm form){
        //{id=2, title=개똥이의 여행22, content=즐거운 여행22}
        log.info("update->form : " + form);
        //        1. DTO를 엔티티로 변환
        //DTO(form)를 엔티티(articleEntity)로 변환
        Lprod lprodEntity = form.toEntity();
        //articleEntity{id=2, title=개똥이의 여행22, content=즐거운 여행22}
        log.info("update->lprodEntity : " + lprodEntity);

        //        2. 엔티티를 DB에 저장
        //2-1. DB에서 기존 데이터 가져오기(검증)
        Lprod target = this.lprodRepository.findById(lprodEntity.getLprodId()).orElse(null);
        log.info("update->target : " + target);

        //2-2. 기존 데이터 값을 갱신하기
        //엔티티를 DB에 저장(갱신)
        if(target != null){//검증완료
            //articleEntity{id=2, title=개똥이의 여행22, content=즐거운 여행22}
            this.lprodRepository.save(lprodEntity);
        }

        //   데이터
        //articleEntity{id=2, title=개똥이의 여행22, content=즐거운 여행22}
        return target;
    }
}

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
    요청파라미터 : JSON Stirng{lprodId=2,title=p102,content=전자제품22}
    요청방식 : post

    URL 요청 접수
    매개변수로 DTO 받아 오기
     */
    @PostMapping("/api/lprod/update")
    public Lprod update(@RequestBody LprodForm form){
        //{lprodId=2,title=p102,content=전자제품22}
        log.info("update->form : " + form);
        //        1. DTO를 엔티티로 변환
        //DTO(form)를 엔티티(lprodEntity)로 변환
        Lprod lprodEntity = form.toEntity();
        //lprodEntity{lprodId=2,title=p102,content=전자제품22}
        log.info("update->lprodEntity : " + lprodEntity);

        //        2. 엔티티를 DB에 저장
        //2-1. DB에서 기존 데이터 가져오기(검증)
        Lprod target = this.lprodRepository.findById(lprodEntity.getLprodId()).orElse(null);
        log.info("update->target : " + target);

        //2-2. 기존 데이터 값을 갱신하기
        //엔티티를 DB에 저장(갱신)
        if(target != null){//검증완료
            //lprodEntity{lprodId=2,title=p102,content=전자제품22}
            this.lprodRepository.save(lprodEntity);
        }

        //   데이터
        //lprodEntity{lprodId=2,title=p102,content=전자제품22}
        return target;
    }


    /* 글 삭제
    요청URI : /lprod/delete
    요청파라미터 : JSON String{lprodId:2}
    요청방식 : post
     */
    @PostMapping("/api/lprod/delete")
    public Lprod delete(@RequestBody Long lprodId){
        // 로그: 삭제 요청이 수신되었음을 기록합니다.
        //log.info("삭제요청이 들어옴. articleForm" + lprodForm);
        //Long lprodId = lprodForm.getLprodId();
        log.info("삭제요청이 들어옴. lprodId" + lprodId);

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


        }
        //데이터
        return target;
    }
}

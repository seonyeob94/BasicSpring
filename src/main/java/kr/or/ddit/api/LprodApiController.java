package kr.or.ddit.api;

import kr.or.ddit.entity.Lprod;
import kr.or.ddit.repository.LprodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

package kr.or.ddit.dto;

import kr.or.ddit.entity.Lprod;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class LprodForm {
    //프로퍼티
    private Long lprodId;

    private String lprodGu;

    private String lprodNa;

    //기본생성자
    //public LprodForm() {}


    // getter/setter 메소드
    public Long getLprodId() {
        return lprodId;
    }

    public void setLprodId(Long lprodId) {
        this.lprodId = lprodId;
    }

    public String getLprodGu() {
        return lprodGu;
    }

    public void setLprodGu(String lprodGu) {
        this.lprodGu = lprodGu;
    }

    public String getLprodNa() {
        return lprodNa;
    }

    public void setLprodNa(String lprodNa) {
        this.lprodNa = lprodNa;
    }



    public Lprod toEntity() {
        Lprod lprod = new Lprod(null, this.lprodGu, this.lprodNa);
        return lprod;
    }
}

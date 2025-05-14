package kr.or.ddit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Lprod {
    @Id
    @GeneratedValue
    private Long lprodId;

    @Column
    private String lprodGu;

    @Column
    private String lprodNa;



    //public Lprod() {}

    /*public Lprod(Long lprodId, String lprodGu, String lprodNa) {
        this.lprodId = lprodId;
        this.lprodGu = lprodGu;
        this.lprodNa = lprodNa;
    }*/
}

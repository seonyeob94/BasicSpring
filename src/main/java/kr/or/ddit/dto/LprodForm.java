package kr.or.ddit.dto;

public class LprodForm {
    //프로퍼티
    private int lprodId;

    private String lprodGu;

    private String lprodNa;

    //기본생성자
    public LprodForm() {}


    // getter/setter 메소드
    public int getLprodId() {
        return lprodId;
    }

    public void setLprodId(int lprodId) {
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

    //toString()
    @Override
    public String toString() {
        return "LprodForm{" +
                "lprodId=" + lprodId +
                ", lprodGu='" + lprodGu + '\'' +
                ", lprodNa='" + lprodNa + '\'' +
                '}';
    }
}

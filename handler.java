A. 데이터 클래스 (ResultData)
public class ResultData {
    private String sentence;
    private int count;
    private boolean isPass;

    // Setter들
    public void setSentence(String sentence) { this.sentence = sentence; }
    public void setCount(int count) { this.count = count; }
    public void setPass(boolean isPass) { this.isPass = isPass; }

    // 문자열을 하나로 모아서 반환하는 역할 (toString 오버라이딩)
    @Override
    public String toString() {
        if (isPass) {
            return "결과: [" + sentence + ", pass] (검증개수: " + count + ")";
        } else {
            return "결과: [검증 실패] (현재개수: " + count + "/4)";
        }
    }
}

B. 핸들러 클래스 (FinalHandler)
public class FinalHandler {
    public void display(ResultData data) {
        // data.toString()이 자동으로 호출되거나 직접 호출하여 출력
        System.out.println("--- 처리 결과 전송 ---");
        System.out.println(data.toString()); 
    }
}

C. 분석 및 실행 클래스 (Main)


    public class Main {
    public static void main(String[] args) {
        // 1. 객체 생성
        ResultData result = new ResultData();
        
        // 2. Setter로 데이터 주입 (Analyzer가 할 일)
        result.setSentence("오늘 사과 = 홍천 사과, 배 = 먹골 배...");
        result.setCount(4);
        result.setPass(true);
        
        // 3. 핸들러로 객체 전달
        FinalHandler handler = new FinalHandler();
        handler.display(result);
    }
}

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



//연습용 
import java.util.StringTokenizer;

/**
 * [1. DTO 클래스] 데이터를 담는 바구니 역할을 합니다.
 * Setter로 조각 데이터를 받고, toString으로 문장을 조립합니다.
 */
class ResultData {
    private String originalSentence;
    private int passCount;
    private boolean isAllPassed;

    // Setter: 분석기(Analyzer)가 데이터를 채울 때 사용
    public void setOriginalSentence(String originalSentence) {
        this.originalSentence = originalSentence;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

    public void setAllPassed(boolean allPassed) {
        this.isAllPassed = allPassed;
    }

    // Getter: 핸들러가 개별 정보가 필요할 때 사용
    public boolean isAllPassed() {
        return isAllPassed;
    }

    /**
     * 핵심 로직: Setter로 모은 조각들을 하나의 문장으로 조립하여 반환함
     */
    @Override
    public String toString() {
        if (isAllPassed) {
            // 모든 품종이 확인되면 원본 문장에 ", pass"를 붙여서 완성
            return originalSentence + ", pass";
        } else {
            return "검증 실패 (통과된 품종 수: " + passCount + "/4)";
        }
    }
}

/**
 * [2. Handler 클래스] 최종 결과물을 전달받아 처리(출력)합니다.
 */
class FinalHandler {
    public void handle(ResultData data) {
        System.out.println("\n[Handler 수신 결과]");
        // data.toString()을 호출하여 조립된 문장을 출력함
        System.out.println("최종 메시지: " + data.toString());
        
        if (!data.isAllPassed()) {
            System.out.println("알림: 모든 품종이 일치하지 않아 전송이 제한되었습니다.");
        }
    }
}

/**
 * [3. Analyzer 클래스] 메인 로직을 수행합니다.
 */
public class WordDataAnalyzer {
    // 카테고리별 품종 배열
    private static final String[] APPLE = {"홍천 사과", "홍옥", "부사"};
    private static final String[] PEAR = {"먹골 배", "신고 배", "원황"};
    private static final String[] PEACH = {"천도복숭아", "백도", "황도"};
    private static final String[] STRAWBERRY = {"금실 딸기", "설향", "장희"};

    public static void main(String[] args) {
        // 입력 데이터
        String input = "오늘 사과 = 홍천 사과, 배 = 먹골 배, 복숭아 = 천도복숭아, 딸기 = 금실 딸기";
        
        // 결과 담을 객체(바구니)와 핸들러 준비
        ResultData resultObj = new ResultData();
        FinalHandler handler = new FinalHandler();
        
        int passCount = 0;

        // 과정 1: StringTokenizer로 문장 분리 (구분자: ,)
        StringTokenizer st = new StringTokenizer(input, ",");

        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            
            // 과정 2: '=' 뒤의 품종명 추출
            StringTokenizer stInner = new StringTokenizer(pair, "=");
            if (stInner.countTokens() >= 2) {
                stInner.nextToken(); // '=' 앞부분 버림
                String variety = stInner.nextToken().trim(); // 품종명 추출

                // 과정 3: 배열에 존재하는지 검사
                if (checkVariety(variety)) {
                    passCount++;
                }
            }
        }

        // 과정 4: Setter를 사용하여 데이터를 객체에 담음 (조립 준비)
        resultObj.setOriginalSentence(input);
        resultObj.setPassCount(passCount);
        resultObj.setAllPassed(passCount == 4);

        // 과정 5: 완성된 객체를 핸들러에게 전달 (연결 지점)
        handler.handle(resultObj);
    }

    // 배열 포함 여부 확인 메서드
    private static boolean checkVariety(String variety) {
        return isExist(APPLE, variety) || isExist(PEAR, variety) || 
               isExist(PEACH, variety) || isExist(STRAWBERRY, variety);
    }

    private static boolean isExist(String[] arr, String target) {
        for (String s : arr) {
            if (s.equals(target)) return true;
        }
        return false;
    }
}

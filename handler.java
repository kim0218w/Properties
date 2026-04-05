import java.util.StringTokenizer;

// 1. 결과를 전달받아 처리하는 클래스 (전달 대상)
class ResultHandler {
    /**
     * Handler의 역할:
     * - 데이터 수신: 분석이 완료된 최종 결과물을 전달받음.
     * - 로직 분리: 분석 로직(Checker)과 출력/저장 로직을 분리함 (관심사 분리).
     * - 후속 작업: DB 저장, UI 출력, 네트워크 전송 등 실제 액션을 수행함.
     */
    public void handleFinalData(String processedMessage) {
        System.out.println("\n[Handler] 최종 데이터 수신 완료:");
        System.out.println(">>> " + processedMessage);
    }
}

// 2. 문장을 분석하고 검증하는 메인 클래스
public class WordDataAnalyzer {
    // 카테고리별 품종 데이터 (배열)
    private static final String[] APPLE = {"홍천 사과", "홍옥", "부사"};
    private static final String[] PEAR = {"먹골 배", "신고 배", "원황"};
    private static final String[] PEACH = {"천도복숭아", "백도", "황도"};
    private static final String[] STRAWBERRY = {"금실 딸기", "설향", "장희"};

    public static void main(String[] args) {
        // 입력 문장
        String inputSentence = "오늘 사과 = 홍천 사과, 배 = 먹골 배, 복숭아 = 천도복숭아, 딸기 = 금실 딸기";
        
        ResultHandler handler = new ResultHandler();
        int passCount = 0;

        // 과정 1: ','를 기준으로 문장을 나눔
        StringTokenizer st = new StringTokenizer(inputSentence, ",");

        while (st.hasMoreTokens()) {
            String segment = st.nextToken(); // " 사과 = 홍천 사과"

            // 과정 2: '='를 기준으로 품종 추출
            StringTokenizer stInner = new StringTokenizer(segment, "=");
            if (stInner.countTokens() >= 2) {
                stInner.nextToken(); // '=' 앞부분 무시
                String varietyName = stInner.nextToken().trim(); // 품종명 추출 및 공백 제거

                // 과정 3: 4가지 배열에 존재하는지 확인
                if (isValidVariety(varietyName)) {
                    passCount++;
                }
            }
        }

        // 과정 4: 모든 품종이 확인(4개)되면 결과 생성 및 전달
        if (passCount == 4) {
            String finalOutput = inputSentence + ", pass"; // 원본 + 추가 문구
            handler.handleFinalData(finalOutput);
        } else {
            System.out.println("검증 실패: 일치하지 않는 품종이 포함되어 있습니다. (Count: " + passCount + ")");
        }
    }

    // 품종 존재 여부 체크 헬퍼 메서드
    private static boolean isValidVariety(String name) {
        return isPresent(APPLE, name) || isPresent(PEAR, name) || 
               isPresent(PEACH, name) || isPresent(STRAWBERRY, name);
    }

    private static boolean isPresent(String[] arr, String target) {
        for (String s : arr) {
            if (s.equals(target)) return true;
        }
        return false;
    }
}

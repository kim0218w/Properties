# Properties
## 외부 변수 파일을 읽고 consol에 진행 상황을 저장하는 파일이다.
###  1. PropLoad
  - 해당 Method에서 파일을 읽어오는 코드를 만들었다.
  - properties 파일에 들어갈 항목은 Dir_write(작업된 log 내용이 적히는 경로), level_value(level의 등급)
    
### 2. Filewrite
  - 파일에 log4j 처럼 파일을 기록할 수 있도록 만들었다.

##  Code 실행시 consol에 기록되는 예시
ex)
### application 실행 ###
[Step 1] 설정 파일 로드 시작...
 >> FileInputStream 연결 성공.
 >> Properties 데이터 로드 완료.
 >> [로드 결과] Level: 4, Path: C:/Tesk/work.txt
[Step 1] 설정 파일 로드 프로세스 종료.

[Step 2] 로그 쓰기 작업 시작...
 >> 파일 쓰기 스트림(FileWriter)을 오픈합니다...
 >> 로그 레벨 필터링 및 기록 진행 중...
    [로그 출력] [2026-03-18 08:05:46.043] [TRACE] Localtest.application.Filewrite(application.java:75) - 로그 메시지 기록
    [로그 출력] [2026-03-18 08:05:46.044] [DEBUG] Localtest.application.Filewrite(application.java:75) - 로그 메시지 기록
    [로그 출력] [2026-03-18 08:05:46.044] [INFO ] Localtest.application.Filewrite(application.java:75) - 로그 메시지 기록
    [로그 출력] [2026-03-18 08:05:46.044] [WARN ] Localtest.application.Filewrite(application.java:75) - 로그 메시지 기록
    [로그 출력] [2026-03-18 08:05:46.044] [ERROR] Localtest.application.Filewrite(application.java:75) - 로그 메시지 기록
 >> 총 5개의 로그 항목이 저장되었습니다.
[Step 2] 로그 쓰기 프로세스 종료.
### application 종료 ###

package Localtest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
public class application {

    private static final String Config_Path = "C:/Tes/info.properties";
    private static final DateTimeFormatter LOG_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final Properties prop = new Properties();

    private int level_value = 0; 
    private String logFilePath = "";

    public enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR, FATAL
    }

    private void PropLoad() {
        System.out.println("[Step 1] 설정 파일 로드 시작...");
        File configFile = new File(Config_Path);
        
        if (!configFile.exists()) {
            System.err.println(" >> [경고] 설정 파일이 존재하지 않습니다: " + Config_Path);
            return;
        }

        try (FileInputStream fis = new FileInputStream(configFile)) {
            System.out.println(" >> FileInputStream 연결 성공.");
            prop.load(fis);
            System.out.println(" >> Properties 데이터 로드 완료.");

            String lvl = prop.getProperty("level_value", "0");
            this.level_value = Integer.parseInt(lvl);
            this.logFilePath = prop.getProperty("Dir_write", "C:/Tes/default.txt");

            System.out.format(" >> [로드 결과] Level: %d, Path: %s\n", this.level_value, this.logFilePath);
        } catch (IOException | NumberFormatException e) {
            System.err.println(" >> [오류] 설정 로드 중 예외 발생: " + e.getMessage());
        }
        System.out.println("[Step 1] 설정 파일 로드 프로세스 종료.\n");
    }

    private void Filewrite() {
        System.out.println("[Step 2] 로그 쓰기 작업 시작...");
        
        if (this.logFilePath == null || this.logFilePath.isEmpty()) {
            System.err.println(" >> [중단] 로그 파일 경로가 유효하지 않습니다.");
            return;
        }

        File logFile = new File(this.logFilePath);
        File parentDir = logFile.getParentFile();

        // 1. 디렉토리 확인 및 생성 단계
        if (parentDir != null && !parentDir.exists()) {
            System.out.println(" >> 디렉토리가 존재하지 않음. 생성을 시도합니다: " + parentDir.getAbsolutePath());
            if (parentDir.mkdirs()) {
                System.out.println(" >> 디렉토리 생성 성공.");
            } else {
                System.err.println(" >> 디렉토리 생성 실패.");
            }
        }

        // 2. 파일 쓰기 단계
        System.out.println(" >> 파일 쓰기 스트림(FileWriter)을 오픈합니다...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            // 0: getStackTrace, 1: Filewrite, 2: main
            StackTraceElement element = stackTrace[1]; 

            String className = element.getClassName();
            String methodName = element.getMethodName();
            String fileName = element.getFileName();
            int lineNumber = element.getLineNumber();

            System.out.println(" >> 로그 레벨 필터링 및 기록 진행 중...");
            int writeCount = 0;

            for (Level lvl : Level.values()) {
                if (lvl.ordinal() >= this.level_value) {
                    String timestamp = LocalDateTime.now().format(LOG_TIME);
                    
                    // 요구하신 상세 포맷
                    String logMessage = String.format("[%s] [%-5s] %s.%s(%s:%d) - 로그 메시지 기록",
                            timestamp,
                            lvl.name(),
                            className,
                            methodName,
                            fileName,
                            lineNumber);

                    // 파일에 기록
                    writer.write(logMessage);
                    writer.newLine();
                    
                    // 콘솔에 기록
                    System.out.println("    [로그 출력] " + logMessage);
                    writeCount++;
                }
            }
            
            writer.flush();
            System.out.format(" >> 총 %d개의 로그 항목이 저장되었습니다.\n", writeCount);
            
        } catch (IOException e) {
            System.err.println(" >> [오류] 파일 쓰기 중 예외 발생: " + e.getMessage());
        }
        System.out.println("[Step 2] 로그 쓰기 프로세스 종료.");
    }

    public static void main(String[] args) {
        System.out.println("### application 실행 ###");
        application app = new application();
        app.PropLoad();
        app.Filewrite();
        System.out.println("### application 종료 ###");
    }
}

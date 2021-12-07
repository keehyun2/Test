package file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class SettleFileHandler {
	
	public static final long DIVIDE_COUNT = 3000;
	
	public static final String PROFILE = "local";

	public static void main(String[] args) throws IOException {
		
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 3000 * 10; i++) {
			list.add(String.format("%s_%06d", "주문", i));
		}

//		List<String> resultList = SettleFileHandler.listObjectToFile(list, "/nas_web02/logs/settle/zxczxc", "SETTLE_DAT");
		
		SettleFileHandler.writeList(list, "/dev/sqlldr/20211208", "SETTLE_DAT");
		
//		resultList.stream().forEach(System.out::println);
		
		System.out.println("############ TEST END ############");
	}

	/**
	 * list collection, 파일 경로, 파일명 prefix 를 받아서 데이터 파일(.dat)을 생성합니다. (폴더가 없으면 생성합니다.)
	 * ex) SettleFileHandler.writeList(objlist, "/nas_web02/logs/settle/zxczxc/", "SETTLE_DAT");
	 * @param <T>
	 * @param objlist 객체 리스트, 객체의 toString() 함수를 호출하여 한줄을 입력합니다. 
	 * @param filePath 파일 위치 절대 경로
	 * @param fileNamePrefix 파일명 prefix
	 * @return 생성된 파일 목록을 반환 
	 */
	public static <T> List<String> writeList(List<T> objlist, String filePath, String fileNamePrefix) {
		
		try {
			Files.createDirectories(Paths.get(filePath)); // 없으면 디렉토리 생성
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int fileCnt = 0;
		BufferedWriter bw = null;
		List<String> fileNameList = new ArrayList<>();
		try {
			for (int i=0; i<objlist.size(); i++) {
				if(i % DIVIDE_COUNT == 0) {
					String fileNameWithFullPath = Paths.get(filePath, String.format("%s_%03d.dat", fileNamePrefix, fileCnt)).toString();
					fileNameList.add(fileNameWithFullPath);
					bw = new BufferedWriter(new FileWriter(fileNameWithFullPath));

					fileCnt++;
				}

				bw.write(objlist.get(i).toString());

				if(i != objlist.size()-1){
					bw.newLine();
				}
				bw.flush();

				if(i % DIVIDE_COUNT == DIVIDE_COUNT -1) {
					bw.close();
					bw = null;
				}
			}
		} catch (Exception e) {
//			logger.error("FileUtil > writeList > Exception : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
//				logger.error("FileUtil > writeList > IOException : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return fileNameList;
	}
	
	/**
	 * 문자열 목록을 전달 받아서 한줄씩 파일에 입력하여 DIVIDE_COUNT 씩 분할하여 (.dat)파일 생성합니다. (파일이 존재하는 경우 덮어쓰기됩니다.)
	 * @param list - String 인자를 가진 List 객체
	 * @param dirPath - 폴더 경로
	 * @param fileName - 파일 이름
	 * @throws IOException - io 예외처리
	 */
	public static List<String> listObjectToFile(List<String> list, String dirPath, String fileName) throws IOException {
		List<String> resultList = new ArrayList<>();
		int fileIdx = 0;
		Path filePath = null;
		if(!"prod".equals(PROFILE)) {
			FileUtils.deleteDirectory(Paths.get(dirPath).toFile()); // spring profile 이 prod 가 아니면 디렉토리 삭제
		}
		Files.createDirectories(Paths.get(dirPath)); // 디렉토리 없는 경우 생성
		
		for (int i = 0; i < list.size(); i++) {
			if(i % DIVIDE_COUNT == 0) {  // 3000 줄) 마다 숫자 증가시켜서 파일 생성
				filePath = Paths.get(dirPath, String.format("%s_%03d.dat", fileName, fileIdx++));
				Files.write(filePath, "".getBytes()); 
				resultList.add(filePath.toString());
			}
			Files.write(filePath, list.get(i).getBytes(StandardCharsets.UTF_8) , StandardOpenOption.APPEND);
//			if(i % DIVIDE_COUNT != DIVIDE_COUNT - 1 && i < list.size() - 1) { // 파일의 마지막 줄이 아닌경우 줄바꿈 추가
				Files.write(filePath, System.lineSeparator().getBytes(StandardCharsets.UTF_8) , StandardOpenOption.APPEND);	// 줄바꿈
//			}
		}
		
		return resultList;
	}

}

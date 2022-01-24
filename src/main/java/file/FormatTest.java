package file;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatTest {
	public static void main(String[] args) {
		String str = String.format("[%s]_%03d", "filename", 1);
		log.info(str);
	}
}

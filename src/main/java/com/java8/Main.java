package com.java8;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class Main {

	private static final boolean isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
	
	public static void main(String[] args) throws IOException {
		
//		String dirPath = "/nas_web02/logs/settle/11st/33";
//		
//		if(isPosix) {
//			System.out.println("is posix");
//            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr--r--");
//            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
//            Files.createDirectories(Paths.get(dirPath), fileAttributes);
//		}else {
//			System.out.println("is not posix");
//		}
//		String.format("zxczx %s", true);
		System.out.println(String.format("zxczx %s", true));

	}

}

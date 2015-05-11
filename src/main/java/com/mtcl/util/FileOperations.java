package com.mtcl.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class FileOperations {

	boolean createSubFolder(String root, String folderName) {

		File folder = new File(root + File.separator + folderName);

		if (folder.exists() && folder.isDirectory()) {
			return false;
		} else {
			folder.mkdir();
			return true;
		}
	}

	/**
	 * Scans the directory using the glob pattern passed as parameter.
	 * 
	 * @param folder
	 *            directory to scan
	 * @param pattern
	 *            glob pattern (filter)
	 */
	String scan(String folder, String pattern) {
		
		String fileName = "";

		// convert the string folder into the Path object
		Path dir = Paths.get(folder);

		// the Files class offers methods for validation
		if (!Files.exists(dir) || !Files.isDirectory(dir)) {
			System.out.println("No such directory!");
		}
		// Try with resources... so nice!
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, pattern)) {
			// iterate over the content of the directory
			//int count = 0;
			for (Path path : ds) {
				fileName = path.getFileName().toString();
			//	count++;
			}
			// System.out.println();
			// System.out.printf("%d Files match the pattern", count);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return fileName;
	}

	// in a class...

	/**
	 * Scans the directory using the patterns passed as parameters. Only 3
	 * patterns will be used.
	 * 
	 * @param folder
	 *            directory to scan
	 * @param patterns
	 *            The first pattern will be used as the glob pattern for the
	 *            DirectoryStream.
	 */
	void scan2(String folder, String... patterns) {
		// obtains the Images directory in the app directory
		Path dir = Paths.get(folder);
		// the Files class offers methods for validation
		if (!Files.exists(dir) || !Files.isDirectory(dir)) {
			System.out.println("No such directory!");
			return;
		}
		// validate at least the glob pattern
		if (patterns == null || patterns.length < 1) {
			System.out.println("Please provide at least the glob pattern.");
			return;
		}

		// obtain the objects that implements PathMatcher
		PathMatcher extraFilterOne = null;
		PathMatcher extraFilterTwo = null;
		if (patterns.length > 1 && patterns[1] != null) {
			extraFilterOne = FileSystems.getDefault().getPathMatcher(
					patterns[1]);
		}
		if (patterns.length > 2 && patterns[2] != null) {
			extraFilterTwo = FileSystems.getDefault().getPathMatcher(
					patterns[2]);
		}

		// Try with resources... so nice!
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir,
				patterns[0])) {
			// iterate over the content of the directory and apply
			// any other extra pattern
			int count = 0;
			for (Path path : ds) {
				System.out.println("Evaluating " + path.getFileName());

				if (extraFilterOne != null
						&& extraFilterOne.matches(path.getFileName())) {
					System.out.println("Match found Do something!");
				}

				if (extraFilterTwo != null
						&& extraFilterTwo.matches(path.getFileName())) {
					System.out.println("Match found Do something else!");
				}

				count++;
			}
			System.out.println();
			System.out.printf("%d Files match the global pattern\n", count);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	

}

package com.yaksha.assignment.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class JavaParserUtils {

	/**
	 * Loads the content of a class file from the given file path and parses it
	 * using JavaParser.
	 *
	 * @param filePath Full path to the class file.
	 * @return The class content as a String.
	 * @throws IOException If an error occurs while reading the file.
	 */
	private static String loadClassContent(String filePath) throws IOException {
		// Create a File object from the provided file path
		File participantFile = new File(filePath);
		if (!participantFile.exists()) {
			System.out.println("Error: Class file not found at path: " + filePath);
			throw new IOException("Class file not found: " + filePath);
		}

		// Read the content of the file
		try (FileInputStream fileInputStream = new FileInputStream(participantFile)) {
			byte[] bytes = fileInputStream.readAllBytes();
			return new String(bytes, StandardCharsets.UTF_8);
		}
	}

	/**
	 * Retrieves the class name from the file path.
	 * 
	 * @param filePath The file path of the class.
	 * @return The class name extracted from the file path.
	 */
	private static String getClassNameFromPath(String filePath) {
		// Extract class name from the file path (e.g., "ApiController.java" ->
		// "ApiController")
		int start = filePath.lastIndexOf("/") + 1;
		int end = filePath.lastIndexOf(".");
		return filePath.substring(start, end);
	}

	// Check if the class has the specified annotation
	public static boolean checkClassAnnotation(String filePath, String classAnnotations) {
		String classContent;
		try {
			classContent = loadClassContent(filePath);
		} catch (IOException e) {
			return false;
		}

		JavaParser javaParser = new JavaParser();
		Optional<CompilationUnit> optionalCompilationUnit = javaParser.parse(classContent).getResult();
		if (optionalCompilationUnit.isEmpty()) {
			System.out.println("Error: Failed to parse the class content at path: " + filePath);
			return false;
		}

		CompilationUnit compilationUnit = optionalCompilationUnit.get();
		boolean hasClassAnnotation = compilationUnit.getClassByName(getClassNameFromPath(filePath)).get()
				.getAnnotations().stream()
				.anyMatch(annotation -> annotation.getNameAsString().equals(classAnnotations));

		if (!hasClassAnnotation) {
			System.out.println("Error: The class " + getClassNameFromPath(filePath) + " is missing the @"
					+ classAnnotations + " annotation.");
			return false;
		}
		return true;
	}

	// Check if the method has the specified annotation
	public static boolean checkMethodAnnotation(String filePath, String methodName, String methodAnnotations) {
		String classContent;
		try {
			classContent = loadClassContent(filePath);
		} catch (IOException e) {
			return false;
		}

		JavaParser javaParser = new JavaParser();
		Optional<CompilationUnit> optionalCompilationUnit = javaParser.parse(classContent).getResult();
		if (optionalCompilationUnit.isEmpty()) {
			System.out.println("Error: Failed to parse the class content at path: " + filePath);
			return false;
		}

		CompilationUnit compilationUnit = optionalCompilationUnit.get();
		MethodDeclaration method = compilationUnit.getClassByName(getClassNameFromPath(filePath)).get()
				.getMethodsByName(methodName).stream().findFirst().orElse(null);

		if (method == null) {
			System.out.println("Error: The method " + methodName + " does not exist in the class "
					+ getClassNameFromPath(filePath) + ".");
			return false;
		}

		boolean hasMethodAnnotation = method.getAnnotationByName(methodAnnotations).isPresent();
		if (!hasMethodAnnotation) {
			System.out.println("Error: The method " + methodName + " is missing the @" + methodAnnotations
					+ " annotation in class " + getClassNameFromPath(filePath) + ".");
			return false;
		}

		return true;
	}

	// Check if the method parameter has the specified annotation
	public static boolean checkMethodParameterAnnotation(String filePath, String methodName, String paramName,
			String paramAnnotation) {
		String classContent;
		try {
			classContent = loadClassContent(filePath);
		} catch (IOException e) {
			return false;
		}

		JavaParser javaParser = new JavaParser();
		Optional<CompilationUnit> optionalCompilationUnit = javaParser.parse(classContent).getResult();
		if (optionalCompilationUnit.isEmpty()) {
			System.out.println("Error: Failed to parse the class content at path: " + filePath);
			return false;
		}

		CompilationUnit compilationUnit = optionalCompilationUnit.get();
		MethodDeclaration method = compilationUnit.getClassByName(getClassNameFromPath(filePath)).get()
				.getMethodsByName(methodName).stream().findFirst().orElse(null);

		if (method == null) {
			System.out.println("Error: The method " + methodName + " does not exist in the class "
					+ getClassNameFromPath(filePath) + ".");
			return false;
		}

		boolean hasParamAnnotation = method.getParameters().stream()
				.anyMatch(param -> param.getNameAsString().equals(paramName)
						&& param.getAnnotationByName(paramAnnotation).isPresent());

		if (!hasParamAnnotation) {
			System.out.println("Error: The parameter " + paramName + " in method " + methodName + " is missing the @"
					+ paramAnnotation + " annotation.");
			return false;
		}

		return true;
	}

	// Check if the method's return type matches the expected return type
	public static boolean checkMethodReturnType(String filePath, String methodName, String expectedReturnType) {
		String classContent;
		try {
			classContent = loadClassContent(filePath);
		} catch (IOException e) {
			return false;
		}

		JavaParser javaParser = new JavaParser();
		Optional<CompilationUnit> optionalCompilationUnit = javaParser.parse(classContent).getResult();
		if (optionalCompilationUnit.isEmpty()) {
			System.out.println("Error: Failed to parse the class content at path: " + filePath);
			return false;
		}

		CompilationUnit compilationUnit = optionalCompilationUnit.get();
		MethodDeclaration method = compilationUnit.getClassByName(getClassNameFromPath(filePath)).get()
				.getMethodsByName(methodName).stream().findFirst().orElse(null);

		if (method == null) {
			System.out.println("Error: The method " + methodName + " does not exist in the class "
					+ getClassNameFromPath(filePath) + ".");
			return false;
		}

		boolean isReturnTypeCorrect = method.getType().asString().equals(expectedReturnType);
		if (!isReturnTypeCorrect) {
			System.out.println("Error: The return type of the method " + methodName + " is not " + expectedReturnType
					+ " in class " + getClassNameFromPath(filePath) + ".");
			return false;
		}

		return true;
	}
}

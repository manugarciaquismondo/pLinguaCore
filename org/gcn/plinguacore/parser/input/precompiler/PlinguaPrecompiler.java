package org.gcn.plinguacore.parser.input.precompiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gcn.plinguacore.util.PlinguaCoreException;

public class PlinguaPrecompiler {
	
	private static final int OPEN_COMMENT_FOUND = 0;
	private static final int CLOSE_COMMENT_FOUND = 1;
	private static final int NO_COMMENT_FOUND = 2;
	protected final String INCLUDE_FILE_IDENT ="@include", 
			OPEN_COMMENT_CHARACTER="/\\*", CLOSE_COMMENT_CHARACTER="\\*/";
	protected final Pattern INCLUDE_PATTERN = Pattern.compile(INCLUDE_FILE_IDENT+"\\(\"((\\w|\\.|/|\\\\)+)\"\\)");
	protected final Pattern INCLUDE_ROOT_PATTERN = Pattern.compile(INCLUDE_FILE_IDENT);
	protected final Pattern OPEN_COMMENT_PATTERN = Pattern.compile(OPEN_COMMENT_CHARACTER);
	protected final Pattern CLOSE_COMMENT_PATTERN = Pattern.compile(CLOSE_COMMENT_CHARACTER);
	protected String sourceRoute, sourceDirectory;
	protected char SEPARATION_CHARACTER='/';
	protected int lineCounter;
	protected BufferedReader bufferedReader;
	protected boolean nullRoute, insideCommentEnvironment;
		
	public InputStream processFileAndTreatExceptions(String source, String referenceRoute) throws PlinguaCoreException{
		return processFileAndTreatExceptions(null, source, referenceRoute);
	}
	
	public InputStream processFileAndTreatExceptions(InputStream inputStream, String source, String referenceRoute) throws PlinguaCoreException{
		String resultingString="";
		try {
			resultingString = processFile(inputStream, source, referenceRoute);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PlinguaCoreException e) {
			System.err.println(e.getMessage());
			// TODO Auto-generated catch block
			throw e;
		}
		return new ByteArrayInputStream(resultingString.getBytes(StandardCharsets.UTF_8));
	}

	protected void extractInputFileRoute(String sourceRoute, String referenceRoute) {
		nullRoute=referenceRoute==null;
		String bufferReferenceRoute=null;
		if(!nullRoute){
			bufferReferenceRoute=referenceRoute.replace('\\', SEPARATION_CHARACTER);
			sourceDirectory=bufferReferenceRoute;
		}
		else{
			bufferReferenceRoute=sourceRoute.replace('\\', SEPARATION_CHARACTER);
			sourceDirectory=bufferReferenceRoute.substring(0, bufferReferenceRoute.lastIndexOf(SEPARATION_CHARACTER));
		}
	}

	protected String processFile(String source, String referecenceRoute) throws FileNotFoundException, IOException, PlinguaCoreException {
		return processFile(null, source, referecenceRoute);
	}

	protected String processFile(InputStream inputStream, String source, String referecenceRoute) throws FileNotFoundException, IOException, PlinguaCoreException {
		sourceRoute=source;
		extractInputFileRoute(source, referecenceRoute);
		String resultingString="";
		if(inputStream==null)
			bufferedReader = new BufferedReader(new FileReader(source));
		else{
			bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
		}
		String readLine="";
		lineCounter=0;
		insideCommentEnvironment = false;
		if((readLine=bufferedReader.readLine())!=null){
			lineCounter++;
			resultingString = checkAndAppendLine(readLine, "");
			while((readLine=bufferedReader.readLine())!=null){
				lineCounter++;
				//resultingString+=checkAndAppendLine(readLine, prependLine?"\n":"");
				resultingString+=checkAndAppendLine(readLine, "\n");
			}
		}			
		bufferedReader.close();
		return resultingString;
	}

	

	protected String checkAndAppendLine(String readLine, String prependString) throws IOException, PlinguaCoreException {
		String processedLine=processLine(readLine);
		if(!processedLine.isEmpty()){
			String printedLine=processedLine;
			if(!prependString.isEmpty())
				printedLine=prependString+processedLine;
			return printedLine;
		}
		else{
			return prependString;
		}
		
	}
	

	private String processLine(String readLine) throws PlinguaCoreException {
		Matcher openCommentMatcher = OPEN_COMMENT_PATTERN.matcher(readLine);
		Matcher closeCommentMatcher = CLOSE_COMMENT_PATTERN.matcher(readLine);
		boolean openCommentFound= openCommentMatcher.find(), closeCommentFound=closeCommentMatcher.find();
		String commentTrimmedLine="", resultLine="";
		if(openCommentFound||closeCommentFound)
		{
			int nextCommentMark=getNextCommentMark(openCommentMatcher, closeCommentMatcher, openCommentFound, closeCommentFound);
			if(!insideCommentEnvironment&&nextCommentMark==CLOSE_COMMENT_FOUND){
				throw new PlinguaCoreException("Closing comment symbol without pairing opening comment symbol found");
			}
			if(nextCommentMark==CLOSE_COMMENT_FOUND){
				insideCommentEnvironment=false;
				int positionAfterComment=closeCommentMatcher.end();//+1;
				if(readLine.length()>positionAfterComment){
					commentTrimmedLine=processLine(readLine.substring(positionAfterComment));
				}
				else{
					commentTrimmedLine="";
				}
				resultLine = readLine.substring(0,positionAfterComment) + commentTrimmedLine;
					
			} else if(nextCommentMark==OPEN_COMMENT_FOUND){
				String prev="";
				if (!insideCommentEnvironment)
					prev = processLine(readLine.substring(0,openCommentMatcher.start()));
				insideCommentEnvironment=true;
				int positionAfterComment=openCommentMatcher.end();//+1;
				if(readLine.length()>positionAfterComment){
					commentTrimmedLine=processLine(readLine.substring(positionAfterComment));
				}
				else{
					commentTrimmedLine="";
				}
				
				resultLine = prev + "/*"  + commentTrimmedLine;
			}
			
		} else {
			if(!insideCommentEnvironment && readLine.contains(INCLUDE_FILE_IDENT)){
				resultLine= processInclude(readLine);
			}
			else
			{
				resultLine= readLine;
			}
		}
		
		return resultLine;
	}

	private int getNextCommentMark(Matcher openCommentMatcher,
			Matcher closeCommentMatcher, boolean openCommentFound,
			boolean closeCommentFound){ 
		if(openCommentFound&!closeCommentFound){
			return OPEN_COMMENT_FOUND;
		} else if(!openCommentFound&closeCommentFound){
			return CLOSE_COMMENT_FOUND;
		} else if(!openCommentFound&!closeCommentFound){
			return NO_COMMENT_FOUND;
		} else if (openCommentMatcher.start()<closeCommentMatcher.start()){
			return OPEN_COMMENT_FOUND;
		} else{
			return CLOSE_COMMENT_FOUND;
		}
	}

	private String processInclude(String readLine) throws PlinguaCoreException {
		Matcher includeMatcher = INCLUDE_PATTERN.matcher(readLine);
		if(!includeMatcher.find()){
			throwException("Line "+lineCounter+" does not contain a valid "+INCLUDE_FILE_IDENT+" sentence");
		}
		else{
			Matcher includeRootMatcher = INCLUDE_ROOT_PATTERN.matcher(readLine);
			includeRootMatcher.find();
			if(includeRootMatcher.find()){
				throwException("Line "+lineCounter+" contains at least 2 "+INCLUDE_FILE_IDENT+" sentences");
			}
			String includedFileRoute=includeMatcher.group(1);
			String composedInternalIncludedFile=includedFileRoute.replace('\\', SEPARATION_CHARACTER);
			composedInternalIncludedFile=sourceDirectory+SEPARATION_CHARACTER+composedInternalIncludedFile;
			PlinguaPrecompiler internalPrecompiler=new PlinguaPrecompiler();
			try{
				return internalPrecompiler.processFile(composedInternalIncludedFile, nullRoute?null:sourceDirectory);
			}
			catch(IOException e){
				throwException("Errors occurred while parsing file "+composedInternalIncludedFile+" with root "+includedFileRoute+": "+e.getMessage());
			}
		}
		return "";
		
		// TODO Auto-generated method stub
		
	}
	
	private boolean commentedPrimitive(String readLine,
			String ident) {
		// TODO Auto-generated method stub
		if(readLine.contains(OPEN_COMMENT_CHARACTER)){
			int indexOfIdent=readLine.indexOf(ident);
			int indexOfComment=readLine.indexOf(OPEN_COMMENT_CHARACTER);
			if(indexOfComment>=0&&indexOfIdent>indexOfComment)
				return true;
		}
		return false;
	}

	protected void throwException(String exceptionMessage) throws PlinguaCoreException{
		try {
			bufferedReader.close();
		}
		catch(IOException e){}
		throw new PlinguaCoreException("Error while parsing file: "+sourceRoute+".\n"+exceptionMessage+"\n");
	}
	
	public 	String extractModelDirectory(String modelRoute) {
		char separationCharacter='/';
		if(modelRoute.contains(""+'\\')){
			separationCharacter='\\';
		}
		if(!modelRoute.contains(separationCharacter+""))
			return "";
		return modelRoute.substring(0, modelRoute.lastIndexOf(separationCharacter));
		
	}

}

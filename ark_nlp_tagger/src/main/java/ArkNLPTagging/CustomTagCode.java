package ArkNLPTagging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import cmu.arktweetnlp.Tagger;

import com.carrotsearch.labs.langid.DetectedLanguage;
import com.carrotsearch.labs.langid.LangIdV3;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CustomTagCode {

    public static final String MODEL_LOCATION = "/model.20120919";
    public static final String FILE_ENCODING = "UTF-8";


    private static final LangIdV3 mLangid = new LangIdV3();

    private String getText(String line, boolean isJsonFile) {
        if(isJsonFile) {
            JsonObject root = new JsonParser().parse(line).getAsJsonObject();
            JsonElement text = root.get("text");
            if (text != null) {
                return text.getAsString();
            }
            return null;
        } else {
            return line.replace("\n", "");
        }
    }

	public void tag(String fileName, boolean isJsonFile, String outputFile, boolean checkIsEnglish) throws IOException{
		Tagger posTagger=new Tagger();
		try {
			posTagger.loadModel(MODEL_LOCATION);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileName), FILE_ENCODING));
		BufferedWriter writer =
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(outputFile), FILE_ENCODING));
		
		String line = "";
		int lineCount = 0;
		while((line = reader.readLine()) != null){
			System.out.println(lineCount);
            String text = getText(line,isJsonFile);
            if(text != null){
                writer.append(lineCount++ + "\n");

                String langcode = mLangid.classify(text, false).getLangCode();
                if(checkIsEnglish &&
                   !mLangid.classify(text, false).getLangCode().equals("en")){

                    writer.append("\tNON_ENGLISH_TWEET\tNON_ENGLISH_TWEET\n");
                    continue;
                }

				List<Tagger.TaggedToken> tokens = posTagger.tokenizeAndTag(text);
				int tokenSize = tokens.size();
				String currToken = tokens.get(0).token;
				String currTag = tokens.get(0).tag;

				for(int i = 1; i < tokenSize;i++){
					if(tokens.get(i).tag.equals(currTag)){
						currToken += " " + tokens.get(i).token;
					} else{
						writer.append("\t" + currToken + "\t" + currTag + "\n");
						currToken = tokens.get(i).token;
						currTag = tokens.get(i).tag;
					}
				}
				writer.append("\t" + currToken + "\t" + currTag + "\n");
			}

		}
		reader.close();
		writer.close();

	}
	
	public static void main(String[] args){
        System.out.println("Args given: ");
        for(String arg : args){
           System.out.println("\t" + arg);
        }
		if(args.length != 4){

            throw new RuntimeException("\nUsage: [fileToParse] [is_json_file] [outputFileName] [check_is_english]");
		}
		String file = args[0];
        boolean isJsonFile = args[1].equals("1");
		String outputFileName =  args[2];
        boolean checkIsEnglish = args[3].equals("1");
		try {
			new CustomTagCode().tag(file, isJsonFile, outputFileName, checkIsEnglish);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}


package StreamSampler.samplers;

import com.mongodb.MongoClient;
import StreamSampler.user.ValidUser;


import java.io.*;
import java.util.ArrayList;


public class KeywordsFromFileSampler extends KeywordSampler{

	public KeywordsFromFileSampler(ValidUser user, MongoClient mongoClient,
			String outputDirectory, String dbName, String collectionName, String filename)
			throws InterruptedException {
		super(user,  outputDirectory, dbName, collectionName, mongoClient);
		ArrayList<String> keywords = new ArrayList<String>();
		try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filename), "UTF8"));
			String line = null;
		
			while((line = reader.readLine()) != null){
				keywords.add(line.trim());
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setKeywords(keywords);
	}

}

/**
 * @author kjoseph
 * This file shows an example of how to:
 * 	Load users from a config file
 * 	Run a bunch of samplers, including a LocationSampler 
 *   and a sampler that takes keywords from a bunch of text files in a directory
 */

package StreamSampler.examples;

import com.mongodb.MongoClient;
import StreamSampler.samplers.KeywordsFromFileSampler;
import StreamSampler.samplers.LocationSampler;
import StreamSampler.samplers.Sampler;
import StreamSampler.user.ValidUser;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class DirectoryOfKeywordFilesAndLocationExample {

	
	
	public static void main(String args[]) throws InterruptedException, UnknownHostException{
		//TODO: make this more commandline/interface friendly
		
		//on each line of the config file, you have:
		//username,consumer_key,consumer_secret,access_token,access_token_secret
		String configFile = args[0];
		String outputDirectory = args[1];
		String keywordFilesDirectory = args[2];
		long timeToRunFor = Long.valueOf(args[3])*60*1000;
		
		System.out.println("Running w/ config: "+ configFile + 
							", outputDir: "+ outputDirectory +
							", for "+args[3] + " minutes");
		
		ArrayList<ValidUser> users = new ArrayList<ValidUser>();
		try {
			users = ValidUser.getUsersFromConfig(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		MongoClient mongoClient = new MongoClient();
		ArrayList<Sampler> samplers = new ArrayList<Sampler>();

		samplers.add(new LocationSampler(users.get(0),
										mongoClient,
										outputDirectory,
										"olympics",
										"geobox",
										null,
										36.606445,42.268671,42.231445,45.874234));
		users.remove(0);
		
		File folder = new File(keywordFilesDirectory);
		File[] listOfFiles = folder.listFiles(); 
		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isFile() & users.size() > 0  & listOfFiles[i].getName().charAt(0)!= '.'){
				System.out.println(listOfFiles[i].getName());
				samplers.add(new KeywordsFromFileSampler(users.get(0),
														 mongoClient, 
														 outputDirectory,
														 "olympics",
														 listOfFiles[i].getName(),
														 listOfFiles[i].getAbsolutePath()));
				users.remove(0);
			} else {
				System.out.println("skipping: " + listOfFiles[i].getName());
			}
		  }
		
		for(Sampler s:samplers){
			s.start();
		}
		Thread.sleep(timeToRunFor);
		for(Sampler s:samplers){
			s.stop();
		}
	}
}

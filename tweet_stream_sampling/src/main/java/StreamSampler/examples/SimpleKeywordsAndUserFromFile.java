/**
 * @author kjoseph
 * This file shows an example of how to:
 * 	Load users from a config file
 * 	Run a bunch of samplers, including a LocationSampler 
 *   and a sampler that takes keywords from a bunch of text files in a directory
 */

package StreamSampler.examples;

import StreamSampler.samplers.KeywordSampler;
import StreamSampler.samplers.KeywordsFromFileSampler;
import StreamSampler.samplers.Sampler;
import StreamSampler.user.ValidUser;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;


public class SimpleKeywordsAndUserFromFile {

    public static void main(String args[]) throws InterruptedException, UnknownHostException{
        for(String a : args){
            System.out.println(a);
        }
        if(args.length != 3){
            System.out.println("Usage: [userFile] [keywordFile] [durationInMinutes]");
            System.exit(-1);
        }

        String userFile = args[0];
        String keywordFile = args[1];
        Long duration = Long.valueOf(args[2]);

        //on each line of the config file, you have:
        //username,consumer_key,consumer_secret,access_token,access_token_secret
        try {
            List<ValidUser> validUsers = ValidUser.getUsersFromConfig(userFile);

            long timeToRunFor = Long.valueOf(duration)*60*1000;

            System.out.println("Running");

            MongoClient mongoClient = new MongoClient();

            Sampler s = new KeywordsFromFileSampler(
                                validUsers.get(0),
                                mongoClient,
                                "will_output",
                                "nuclear",
                                "tweets",
                                keywordFile);
            s.start();

            Thread.sleep(timeToRunFor);
            s.stop();
        } catch(Exception e){
            e.printStackTrace();
        }


    }
}

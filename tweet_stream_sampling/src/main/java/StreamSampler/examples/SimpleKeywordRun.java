/**
 * @author kjoseph
 * This file shows an example of how to:
 * 	Load users from a config file
 * 	Run a bunch of samplers, including a LocationSampler 
 *   and a sampler that takes keywords from a bunch of text files in a directory
 */

package StreamSampler.examples;

import StreamSampler.samplers.KeywordSampler;
import com.mongodb.MongoClient;
import StreamSampler.samplers.KeywordsFromFileSampler;
import StreamSampler.samplers.LocationSampler;
import StreamSampler.samplers.Sampler;
import StreamSampler.user.ValidUser;

import java.net.UnknownHostException;


public class SimpleKeywordRun {

    public static void main(String args[]) throws InterruptedException, UnknownHostException{


        ValidUser myUser = new ValidUser(
                                "rachljos",
                                "J0uCBvMrekOKSMTsWaAyrw",
                                "9P5KeC48JbwbLJnbY1RzeZy1C3926it5IOR4sVer4",
                                "151733022-YIiOaJsOqe406jxll4fWOay9dzL01l2fORYf41r4",
                                "ZIMrvxCUYNFN37GtljIuA1cNwOv1ddtmYmhZdcjrAvbPB");

            //on each line of the config file, you have:
        //username,consumer_key,consumer_secret,access_token,access_token_secret
        long timeToRunFor = Long.valueOf(10080)*60*1000;

        System.out.println("Running");

        MongoClient mongoClient = new MongoClient();

        Sampler s = new KeywordSampler(
                        myUser,
                        "ferg_output",
                        "ferguson",
                        "ferguson",
                        mongoClient,
                        "#ferguson");
        s.start();

        Thread.sleep(timeToRunFor);
        s.stop();
    }
}

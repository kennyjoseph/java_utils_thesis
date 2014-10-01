/**
 * @author kjoseph
 * Sampling with a bounding box
 * See examples directory for how to use
 */

package StreamSampler.samplers;

import com.mongodb.MongoClient;
import com.twitter.hbc.core.endpoint.Location;
import com.twitter.hbc.core.endpoint.Location.Coordinate;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import StreamSampler.user.ValidUser;

import java.util.ArrayList;
import java.util.List;

public class LocationSampler extends Sampler {

	public LocationSampler(
			ValidUser user,
			MongoClient mongoClient,
			String outputDirectory, 
			String dbName,
			String collectionName,
			List<String> terms,
			double ... coordinates) throws InterruptedException {
		super(user, 
			  mongoClient, 
			  outputDirectory, 
			  dbName,
			  collectionName);
		
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		ArrayList<Location> locations = new ArrayList<Location>();
		for(int i = 0; i <= coordinates.length-4;i+=4){
			locations.add( new Location(new Coordinate(coordinates[i], coordinates[i+1]),
						  				new Coordinate(coordinates[i+2], coordinates[i+3])));
		}
		if(terms != null){
			endpoint.trackTerms(terms);
		}
		endpoint.locations(locations);
		setEndpoint(endpoint);
		
	}

}

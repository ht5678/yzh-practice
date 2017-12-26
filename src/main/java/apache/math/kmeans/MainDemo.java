package apache.math.kmeans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;

/**
 * 
 * @author yuezh2   2017年12月25日 下午3:45:21
 *
 */
public class MainDemo {

	
	public static void main(String[] args) {
		// we have a list of our locations we want to cluster. create a      
		List<Location> locations = new ArrayList<>();
		List<LocationWrapper> clusterInput = new ArrayList<LocationWrapper>(locations.size());
		for (Location location : locations){
		    clusterInput.add(new LocationWrapper(location));
		}
		
		
		// initialize a new clustering algorithm. 
		// we use KMeans++ with 10 clusters and 10000 iterations maximum.
		// we did not specify a distance measure; the default (euclidean distance) is used.
		KMeansPlusPlusClusterer<LocationWrapper> clusterer = new KMeansPlusPlusClusterer<LocationWrapper>(10, 10000);
		List<CentroidCluster<LocationWrapper>> clusterResults = clusterer.cluster(clusterInput);

		// output the clusters
		for (int i=0; i<clusterResults.size(); i++) {
		    System.out.println("Cluster " + i);
		    for (LocationWrapper locationWrapper : clusterResults.get(i).getPoints())
		        System.out.println(locationWrapper.getLocation());
		    System.out.println();
		}
		
	}
	
	
}

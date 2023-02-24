package vttp2022.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.services.S3Service;

import java.io.IOException;

@Repository
public class MapCache {

	@Autowired
	private S3Service s3Service;

	final String MAP_SERVER_URL = "http://map.chuklee.com/map";

	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public String getMap(LatLng latlng) throws IOException {

		String url = UriComponentsBuilder.fromUriString(MAP_SERVER_URL)
				.queryParam("lat", latlng.getLatitude())
				.queryParam("lng", latlng.getLongitude())
				.toUriString();

		System.out.println("URL>>>>>>" + url);

		RequestEntity<Void> request = RequestEntity.get(url)
				.build();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> response = restTemplate.exchange(request, byte[].class);
		byte[] payload = response.getBody();

		return s3Service.upload(payload);





		
	}

	// You may add other methods to this class

}

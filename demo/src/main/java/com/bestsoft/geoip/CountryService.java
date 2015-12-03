package com.bestsoft.geoip;

import java.io.IOException;
import java.net.InetAddress;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

public class CountryService {
	public static void main(String args[]) throws IOException, GeoIp2Exception{
		// This creates a WebServiceClient object that can be reused across requests.
		// Replace "42" with your user ID and "license_key" with your license key.
		WebServiceClient client = new WebServiceClient.Builder(42, "license_key").build();

		InetAddress ipAddress = InetAddress.getByName("128.101.101.101");

		// Do the lookup
		CountryResponse response = client.country(ipAddress);

		Country country = response.getCountry();
		System.out.println(country.getIsoCode());            // 'US'
		System.out.println(country.getName());               // 'United States'
		System.out.println(country.getNames().get("zh-CN")); // '美国'
	}
}

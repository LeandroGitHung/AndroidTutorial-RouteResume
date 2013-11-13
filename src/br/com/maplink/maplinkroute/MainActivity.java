package br.com.maplink.maplinkroute;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import br.com.maplink.address.Address;
import br.com.maplink.address.GeocodedAddress;
import br.com.maplink.core.Point;
import br.com.maplink.core.exception.MapLinkAPIException;
import br.com.maplink.map.MapTemplate;
import br.com.maplink.maplinkroute.RouteAsync.RouteAsyncCallback;
import br.com.maplink.route.Route;
import br.com.maplink.route.bean.RouteDetails;
import br.com.maplink.route.bean.RouteInfoResume;
import br.com.maplink.route.bean.RouteOptions;
import br.com.maplink.route.bean.RouteType;
import br.com.maplink.route.bean.Vehicle;

public class MainActivity extends Activity {
	String token = "Your access token here";
	MapTemplate map;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			map = (MapTemplate) findViewById(R.id.map);
			Point point = new Point(-23.592310, -46.689228);
			map.startMapOn(token, point);
			
			Route r1 = createRoute();
			List<Route> routes = new ArrayList<Route>();
			routes.add(r1);

			// Since version 3.0 Android don't permit us to do networking access from UIThread 
			new RouteAsync(token, new RouteAsyncCallback() {
				@Override
				public void onResult(List<RouteInfoResume> routes) {
					Toast.makeText(MainActivity.this, "All right", Toast.LENGTH_LONG).show();
				}
			}).execute(routes);
			
		} catch (MapLinkAPIException e) {
			e.printStackTrace();
		}
	}

	private Route createRoute() {
		//route options
		RouteDetails routeDetails = new RouteDetails(1, RouteType.FASTEST_WITH_TRANSIT_TIME, false, null);
		Vehicle vehicle = new Vehicle(10, 10, 3, 90, 1);
		RouteOptions routeOptions = new RouteOptions("portugues", routeDetails, vehicle);
		
		//start and end address
		GeocodedAddress start = new GeocodedAddress(new Address("R. Funchal", "129", null, "São Paulo", "SP", null), new Point(-23.556680,-46.799765), 0.01);
		GeocodedAddress end = new GeocodedAddress(new Address("R. Dr Geraldo de Campos Moreira", "110", null, "São Paulo", "SP", null), new Point(-23.601621,-46.693528), 0.01);
		
		return new Route(start, end, routeOptions);
	}

}

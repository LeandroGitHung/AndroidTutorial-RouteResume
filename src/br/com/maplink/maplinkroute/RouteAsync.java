package br.com.maplink.maplinkroute;

import java.util.List;

import android.os.AsyncTask;
import br.com.maplink.route.Route;
import br.com.maplink.route.RouteManager;
import br.com.maplink.route.bean.RouteInfoResume;
import br.com.maplink.route.exception.CouldNotCompleteRouteException;

public class RouteAsync extends AsyncTask<List<Route>, Void, List<RouteInfoResume>> {
	
	private String token;
	private final RouteAsyncCallback callback;
	
	// Callback interface
	public interface RouteAsyncCallback {
		public void onResult(List<RouteInfoResume> routes);
	} 
	
	public RouteAsync(String token, RouteAsyncCallback callback) {
		this.token = token;
		this.callback = callback;
	}
	
	@Override
	protected List<RouteInfoResume> doInBackground(List<Route>... params) {
		List<Route> routes = params[0];
		RouteManager manager = new RouteManager();
		
		List<RouteInfoResume> infos = null;
		try {
			//call getRouteResume	
			infos = manager.doRouteResume(token, routes);
		} catch (CouldNotCompleteRouteException e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	@Override
	protected void onPostExecute(List<RouteInfoResume> routeInfo) {
		callback.onResult(routeInfo);
	}
}

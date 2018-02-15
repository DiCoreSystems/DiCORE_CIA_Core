package de.uniks.vs.scenario;

import de.uniks.vs.services.*;

public class StartServices {

    private static int port = 9501;

    public static void main(String[] args) {

        RestService restService = new RestService();
        restService.startServer(RegistryService.class, RegistryService.REGISTRY_PORT);
        restService = new RestService();
        restService.startServer(SmartASRService.class, getFreePort());
        restService = new RestService();
        restService.startServer(MapService.class, getFreePort());
        restService = new RestService();
        restService.startServer(GeoMapService.class, getFreePort());
        restService = new RestService();
        restService.startServer(PollutionService.class, getFreePort());
        restService = new RestService();
        restService.startServer(WebViewService.class, getFreePort());
        restService = new RestService();
        restService.startServer(TemperatureService.class, getFreePort());
        restService = new RestService();
        restService.startServer(GPSService.class, getFreePort());
        restService = new RestService();
        restService.startServer(DataAnalysisService.class, getFreePort());
        restService = new RestService();
        restService.startServer(DBService.class, getFreePort());
        restService = new RestService();
        restService.startServer(PoiService.class, getFreePort());
        restService = new RestService();
        restService.startServer(WLANService.class, getFreePort());


        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getFreePort() {
        return port++;
    }
}

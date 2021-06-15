package es.udc.grandapp.ssdpconnect.client;

import es.udc.grandapp.ssdpconnect.client.impl.SsdpClientImpl;
import es.udc.grandapp.ssdpconnect.model.DiscoveryListener;
import es.udc.grandapp.ssdpconnect.model.DiscoveryRequest;

/**
 * @author Loïc Ortola on 11/03/2016.
 */
public abstract class SsdpClient {

  /**
   * Discover specific devices of particular ServiceType.
   *
   * @param req      the discovery request
   * @param callback the discovery listener
   */
  public abstract void discoverServices(DiscoveryRequest req, DiscoveryListener callback);

  /**
   * Stop discovery.
   */
  public abstract void stopDiscovery();

  /**
   * @return new instance of SsdpClient.
   */
  public static SsdpClient create() {
    return new SsdpClientImpl();
  }

}

package es.udc.grandapp.ssdpconnect.client.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import es.udc.grandapp.ssdpconnect.client.SsdpClient;
import es.udc.grandapp.ssdpconnect.client.SsdpParams;
import es.udc.grandapp.ssdpconnect.client.parser.ResponseParser;
import es.udc.grandapp.ssdpconnect.client.request.SsdpDiscovery;
import es.udc.grandapp.ssdpconnect.client.response.SsdpResponse;
import es.udc.grandapp.ssdpconnect.client.util.Utils;
import es.udc.grandapp.ssdpconnect.exception.NoSerialNumberException;
import es.udc.grandapp.ssdpconnect.model.DiscoveryListener;
import es.udc.grandapp.ssdpconnect.model.DiscoveryRequest;
import es.udc.grandapp.ssdpconnect.model.SsdpService;
import es.udc.grandapp.ssdpconnect.model.SsdpServiceAnnouncement;

/**
 * The SsdpClient handles all multicast SSDP content.
 * One can send search requests or just listen to the incoming events related to cached services.
 *
 * @author Loïc Ortola on 05/08/2017
 */
public class SsdpClientImpl extends SsdpClient {

  // Interval in ms between subsequent discovery requests
  public static final long DEFAULT_INTERVAL_BETWEEN_REQUESTS = 10000;
  private static final DiscoveryListener NOOP_LISTENER = new DiscoveryListener() {
    @Override
    public void onServiceDiscovered(SsdpService service) {

    }

    @Override
    public void onServiceAnnouncement(SsdpServiceAnnouncement announcement) {

    }

    @Override
    public void onFailed(Exception ex, SsdpResponse response) {

    }
  };

  private enum State {
    ACTIVE, IDLE, STOPPING
  }

  private ScheduledExecutorService sendExecutor = Executors.newScheduledThreadPool(1);
  private ExecutorService receiveExecutor = Executors.newSingleThreadExecutor();

  // Stateful attributes
  private List<DiscoveryRequest> requests;
  private DiscoveryListener callback = NOOP_LISTENER;
  private State state;
  private Map<String, SsdpService> cache = new ConcurrentHashMap<String, SsdpService>();
  private MulticastSocket clientSocket;
  private List<NetworkInterface> interfaces;

  /**
   * Reset all stateful attributes.
   *
   * @param req      the new discovery request
   * @param callback the callback
   */
  private void reset(DiscoveryRequest req, DiscoveryListener callback) {
    this.callback = callback;
    this.state = State.ACTIVE;
    this.requests = new ArrayList<DiscoveryRequest>();
    if (req != null) {
      requests.add(req);
    }
    // Lazily Remove expired entries
    for (Map.Entry<String, SsdpService> e : this.cache.entrySet()) {
      if (e.getValue().isExpired()) {
        this.cache.remove(e.getKey());
      } else {
        // Notify entry which is non expired
        callback.onServiceDiscovered(e.getValue());
      }
    }
  }

  @Override
  public void discoverServices(DiscoveryRequest req, final DiscoveryListener callback) {
    if (State.ACTIVE.equals(state)) {
      callback.onFailed(new IllegalStateException("Another discovery is in progress. Stop the first discovery before starting a new one."), null);
      return;
    }
    // Reset attributes
    reset(req, callback);
    // Open and bind client socket to send / receive datagrams
    openAndBindSocket(req.getDiscoveryOptions().getPort());

    // Send UDP Discover Request Datagrams at a fixed rate
    sendExecutor.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        sendDiscoveryRequest();
      }
    }, 0, req.getDiscoveryOptions().getIntervalBetweenRequests(), TimeUnit.MILLISECONDS);

    // Receive all incoming datagrams and handle them on-the-fly
    receiveExecutor.execute(new Runnable() {
      @Override
      public void run() {
        try {
          while (State.ACTIVE.equals(state)) {
            byte[] buffer = new byte[8192];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(packet);
            handleIncomingPacket(packet);
          }
        } catch (IOException e) {
          if (clientSocket.isClosed() && !State.ACTIVE.equals(state)) {
            // This could happen when closing socket. In that case, this is not an issue.
            return;
          }
          callback.onFailed(e, new SsdpResponse());
        }
      }
    });
  }

  /**
   * Thid handler handles incoming SSDP packets.
   *
   * @param packet the received datagram
   */
  private void handleIncomingPacket(DatagramPacket packet) {
    SsdpResponse response = ResponseParser.parse(packet);
    if (response == null) {
      // Unknown to protocol
      return;
    }
    if (response.getType().equals(SsdpResponse.Type.DISCOVERY_RESPONSE)) {
      handleDiscoveryResponse(response);
    } else if (response.getType().equals(SsdpResponse.Type.PRESENCE_ANNOUNCEMENT)) {
      handlePresenceAnnouncement(response);
    }
  }

  /**
   * Send discovery Multicast request.
   */
  private void sendDiscoveryRequest() {
    try {
      if (requests.isEmpty()) {
        // Do nothing if no request has been set
        return;
      }
      for (DiscoveryRequest req : requests) {
        if (req.getServiceTypes() == null || req.getServiceTypes().isEmpty()) {
          sendOnAllInterfaces(SsdpDiscovery.getDatagram(null, req.getDiscoveryOptions()));
        } else {
          for (String st : req.getServiceTypes()) {
            sendOnAllInterfaces(SsdpDiscovery.getDatagram(st, req.getDiscoveryOptions()));
          }
        }
      }
    } catch (IOException e) {
      if (clientSocket.isClosed() && !State.ACTIVE.equals(state)) {
        // This could happen when closing socket. In that case, this is not an issue.
        return;
      }
      callback.onFailed(e, new SsdpResponse());
    }
  }

  /**
   * Open MulticastSocket and bind to Ssdp port.
   */
  private void openAndBindSocket(int port) {
    try {
      this.clientSocket = new MulticastSocket(port);
      this.clientSocket.setReuseAddress(true);
      interfaces = Utils.getMulticastInterfaces();
      joinGroupOnAllInterfaces(SsdpParams.getSsdpMulticastAddress());
    } catch (IOException e) {
      callback.onFailed(e, new SsdpResponse());
    }
  }


  /**
   * Handle presence announcement Datagrams.
   *
   * @param response the incoming announcement
   */
  private void handlePresenceAnnouncement(SsdpResponse response) {
    SsdpServiceAnnouncement ssdpServiceAnnouncement = response.toServiceAnnouncement();
    if (ssdpServiceAnnouncement.getSerialNumber() == null) {
      callback.onFailed(new NoSerialNumberException(), response);
      return;
    }
    if (cache.containsKey(ssdpServiceAnnouncement.getSerialNumber())) {
      callback.onServiceAnnouncement(ssdpServiceAnnouncement);
    } else {
      requests.add(DiscoveryRequest.builder().serviceType(ssdpServiceAnnouncement.getServiceType()).build());
    }
  }

  /**
   * Handle discovery response Datagrams.
   *
   * @param response the incoming response
   */
  private void handleDiscoveryResponse(SsdpResponse response) {
    SsdpService ssdpService = response.toService();
    if (ssdpService.getSerialNumber() == null) {
      callback.onFailed(new NoSerialNumberException(), response);
      return;
    }
    if (!cache.containsKey(ssdpService.getSerialNumber())) {
      callback.onServiceDiscovered(ssdpService);
    }
    cache.put(ssdpService.getSerialNumber(), ssdpService);
  }

  /**
   * Send the datagram packet on all interfaces.
   *
   * Falls back to the default send() if the interfaces list is not populated
   * @param packet the datagram to send
   * @throws IOException from the MulticastSocket
   */
  private void sendOnAllInterfaces(DatagramPacket packet) throws IOException {
    if (interfaces != null && interfaces.size() > 0) {
      for (NetworkInterface iface : interfaces) {
        clientSocket.setNetworkInterface(iface);
        clientSocket.send(packet);
      }
    } else {
      clientSocket.send(packet);
    }
  }

  /**
   * Joins the given multicast group on all interfaces.
   *
   * Falls back to the default joinGroup() if the interfaces list is not populated
   * @param address the multicast group address
   * @throws IOException from the MulticastSocket
   */
  private void joinGroupOnAllInterfaces(InetAddress address) throws IOException {
    if (interfaces != null && interfaces.size() > 0) {
      InetSocketAddress socketAddress = new InetSocketAddress(address, 65535); // the port number does not matter here. it is ignored

      for (NetworkInterface iface : interfaces) {
        this.clientSocket.joinGroup(socketAddress, iface);
      }
    } else {
      this.clientSocket.joinGroup(address);
    }
  }

  /**
   * Leaves the multicast group on all interfaces.
   *
   * Falls back to the default leaveGroup() if the interfaces list is not populated
   * @param address the multicast group address
   * @throws IOException from the MulticastSocket
   */
  private void leaveGroupOnAllInterfaces(InetAddress address) throws IOException {
    if (interfaces != null && interfaces.size() > 0) {
      InetSocketAddress socketAddress = new InetSocketAddress(address, 65535); // the port number does not matter here. it is ignored

      for (NetworkInterface iface : interfaces) {
        this.clientSocket.leaveGroup(socketAddress, iface);
      }
    } else {
      this.clientSocket.leaveGroup(address);
    }
  }

  @Override
  public void stopDiscovery() {
    this.state = State.STOPPING;
    this.receiveExecutor.shutdownNow();
    this.sendExecutor.shutdownNow();
    this.callback = NOOP_LISTENER;
    this.requests = null;
    try {
      leaveGroupOnAllInterfaces(SsdpParams.getSsdpMulticastAddress());
    } catch (IOException e) {
      // Fail silently
    } finally {
      this.clientSocket.close();
    }
    this.interfaces = null;
    this.state = State.IDLE;
  }
}

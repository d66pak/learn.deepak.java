package learn.deepak.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by deepak on 24/10/15.
 */
public class MultiPortEcho {

    private int _ports[];
    private ByteBuffer _echoBuffer = ByteBuffer.allocate(1024);

    public MultiPortEcho(int ports[]) throws IOException {

        _ports = ports;
    }

    public void startListening() throws IOException {

        // Create new Selector
        Selector selector = Selector.open();

        registerListeners(selector);

        while (true) {
            // Blocking call until one of the registered events occur
            int numEvents = selector.select();
            System.out.println("Received " + numEvents + " keys");
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    System.out.println("Received ACCEPT");
                    acceptConnection(key, selector);
                } else if (key.isReadable()) {
                    System.out.println("Received READ");
                    echo(key);
                }
                it.remove();
            }
        }
    }

    private void echo(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();

        int bytesRead = 0;
        while (true) {
            _echoBuffer.clear();
            int r = sc.read(_echoBuffer);

            // end-of-stream
            if (r == 0) {
                break;
            } else if (r < 0) {
                System.out.println("Client closed connection");
                key.cancel();
                sc.close();
                break;
            }

            _echoBuffer.flip();
            sc.write(_echoBuffer);
            bytesRead += r;
        }
        System.out.println("Echoed " + bytesRead + " to " + sc);
    }

    private void acceptConnection(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false); // asynchronous

        // Register for read
        SelectionKey selectionKey = sc.register(selector, SelectionKey.OP_READ);

        System.out.println("Established connection with: " + sc);
    }

    private void registerListeners(Selector selector) throws IOException {

        for (int port : _ports) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false); // Imp for asynchronous behavior
            ssc.socket().bind(new InetSocketAddress(port));
            // Register for connection ACCEPT events
            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Listening on port: " + port);
        }
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("Missing arg(s): port [port port ...]");
            System.exit(1);
        }

        int ports[] = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            ports[i] = Integer.parseInt(args[i]);
        }

        MultiPortEcho mpe = new MultiPortEcho(ports);
        mpe.startListening();
    }
}

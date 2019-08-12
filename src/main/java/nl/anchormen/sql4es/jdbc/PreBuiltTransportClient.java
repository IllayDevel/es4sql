package nl.anchormen.sql4es.jdbc;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.nio.NioTransportPlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class PreBuiltTransportClient extends TransportClient {
    private static final Collection<Class<? extends Plugin>> PRE_INSTALLED_PLUGINS;

    public PreBuiltTransportClient(Settings settings, Class... plugins) {
        this(settings, (Collection)Arrays.asList(plugins));
    }

    public PreBuiltTransportClient(Settings settings, Collection<Class<? extends Plugin>> plugins) {
        this(settings, plugins, (HostFailureListener)null);
    }

    public PreBuiltTransportClient(Settings settings, Collection<Class<? extends Plugin>> plugins, TransportClient.HostFailureListener hostFailureListener) {
        super(settings, Settings.EMPTY, addPlugins(plugins, PRE_INSTALLED_PLUGINS), hostFailureListener);
    }

    public void close() {
        super.close();
    }

    static {
        PRE_INSTALLED_PLUGINS = Collections.unmodifiableList(Arrays.asList(NioTransportPlugin.class));
    }
}

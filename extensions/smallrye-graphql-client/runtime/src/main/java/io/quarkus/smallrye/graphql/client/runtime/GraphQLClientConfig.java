package io.quarkus.smallrye.graphql.client.runtime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

@ConfigGroup
public class GraphQLClientConfig {

    /**
     * The URL location of the target GraphQL service.
     */
    @ConfigItem
    public Optional<String> url;

    /**
     * HTTP headers to add when communicating with the target GraphQL service.
     */
    @ConfigItem(name = "header")
    public Map<String, String> headers;

    /**
     * WebSocket subprotocols that should be supported by this client for running GraphQL operations over websockets.
     * Allowed values are:
     * - `graphql-ws` for the deprecated Apollo protocol
     * - `graphql-transport-ws` for the newer GraphQL over WebSocket protocol (default value)
     * If multiple protocols are provided, the actual protocol to be used will be subject to negotiation with
     * the server.
     */
    @ConfigItem(defaultValue = "graphql-transport-ws")
    public Optional<List<String>> subprotocols;

    /**
     * The trust store location. Can point to either a classpath resource or a file.
     */
    @ConfigItem
    public Optional<String> trustStore;

    /**
     * The trust store password.
     */
    @ConfigItem
    public Optional<String> trustStorePassword;

    /**
     * The type of the trust store. Defaults to "JKS".
     */
    @ConfigItem
    public Optional<String> trustStoreType;

    /**
     * The key store location. Can point to either a classpath resource or a file.
     */
    @ConfigItem
    public Optional<String> keyStore;

    /**
     * The key store password.
     */
    @ConfigItem
    public Optional<String> keyStorePassword;

    /**
     * The type of the key store. Defaults to "JKS".
     */
    @ConfigItem
    public Optional<String> keyStoreType;

    /**
     * Hostname of the proxy to use.
     */
    @ConfigItem
    public Optional<String> proxyHost;

    /**
     * Port number of the proxy to use.
     */
    @ConfigItem
    public Optional<Integer> proxyPort;

    /**
     * Username for the proxy to use.
     */
    @ConfigItem
    public Optional<String> proxyUsername;

    /**
     * Password for the proxy to use.
     */
    @ConfigItem
    public Optional<String> proxyPassword;

    /**
     * Maximum number of redirects to follow.
     */
    @ConfigItem
    public Optional<Integer> maxRedirects;

}

package com.github.barakb.consul

import com.github.barakb.consul.data.*
import com.github.barakb.http.HttpClient
import com.github.barakb.http.HttpConfigBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mu.KotlinLogging
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.cookie.StandardCookieSpec
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy
import org.apache.hc.core5.http.ssl.TLS
import org.apache.hc.core5.http2.HttpVersionPolicy
import org.apache.hc.core5.pool.PoolConcurrencyPolicy
import org.apache.hc.core5.pool.PoolReusePolicy
import org.apache.hc.core5.ssl.SSLContextBuilder
import org.apache.hc.core5.ssl.SSLContexts
import org.apache.hc.core5.util.TimeValue
import org.apache.hc.core5.util.Timeout
import java.io.Closeable
import java.math.BigInteger
import java.time.Duration

@Suppress("unused")
private val logger = KotlinLogging.logger {}

@Suppress("unused")
class ConsulClient(init: ConsulConfigBuilder.() -> Unit) : Closeable {
    private val consulConfig = ConsulConfigBuilder().apply(init).build()
    private val httpClient = createHttpClient(consulConfig)

    @Suppress("unused")
    val agent = Agent(httpClient)

    @Suppress("unused")
    val catalog = Catalog(httpClient)

    @Suppress("unused")
    val config = Config(httpClient)

    @Suppress("unused")
    val session = Session(httpClient)

    @Suppress("unused")
    val kv = Kv(httpClient)


    class Agent(private val client: HttpClient) {
        @Suppress("unused")
        suspend fun list(wan: Boolean? = null, segment: String? = null): List<Member> {
            return client.get {
                path = "agent/members"
                param("wan", wan)
                param("segment", segment)
            }
        }

        @Suppress("unused")
        suspend fun self(): com.github.barakb.consul.data.Agent {
            return client.get {
                path = "agent/self"
            }
        }

        @Suppress("unused")
        suspend fun reload(): JsonObject? {
            return client.put {
                path = "agent/reload"
            }
        }

        @Suppress("unused")
        suspend fun maintenance(enable: Boolean, reason: String? = null) {
            return client.put {
                path = "agent/reload"
                param("enable", enable)
                param("reason", reason)
            }
        }

        @Suppress("unused")
        suspend fun metrics(): JsonObject {
            return client.get {
                path = "agent/metrics"
            }
        }

        @Suppress("unused")
        suspend fun join(address: String, wan: Boolean? = null) {
            return client.put {
                path = "agent/join/$address"
                param("enable", address)
                param("reason", wan)
            }
        }

        @Suppress("unused")
        suspend fun leave() {
            return client.put {
                path = "agent/leave"
            }
        }

        @Suppress("unused")
        suspend fun forceLeave(node: String, prune: Boolean = false) {
            return client.put {
                path = "agent/force-leave/$node"
                if (prune) param("prune")
            }
        }

        @Suppress("unused")
        suspend fun updateToken(token: String) {
            return client.put {
                path = "agent/token/acl_token"
                body = mapOf(("Token" to token))
            }
        }

        @Suppress("unused")
        suspend fun updateAgentToken(token: String) {
            return client.put {
                path = "agent/token/acl_agent_token"
                body = mapOf(("Token" to token))
            }
        }

        @Suppress("unused")
        suspend fun updateMasterToken(token: String) {
            return client.put {
                path = "agent/token/acl_agent_master_token"
                body = mapOf(("Token" to token))
            }
        }

        @Suppress("unused")
        suspend fun updateReplicationToken(token: String) {
            return client.put {
                path = "agent/token/acl_replication_token"
                body = mapOf(("Token" to token))
            }
        }

        @Suppress("unused")
        suspend fun checks(filter: String? = null): Map<String, HealthCheck> {
            return client.get {
                path = "agent/checks"
                param("filter", filter)
            }
        }

        @Suppress("unused", "SpellCheckingInspection")
        suspend fun registerCheck(
            name: String,
            args: List<String>,
            id: String? = null,
            interval: String? = null,
            notes: String? = null,
            deregisterCriticalServiceAfter: String? = null,
            aliasNode: String? = null,
            aliasService: String? = null,
            dockerContainerId: String? = null,
            grpc: String? = null,
            grpcUseTLS: Boolean? = null,
            http: String? = null,
            method: String? = null,
            body: String? = null,
            header: Map<String, List<String>>? = null,
            timeout: Duration? = null,
            outputMaxSize: Int? = null,
            tlsSkipVerify: Boolean? = null,
            tcp: String? = null,
            ttl: String? = null,
            serviceId: String? = null,
            status: String? = null,
            successBeforePassing: Int? = null,
            failureBeforeCritical: Int? = null,
        ): Map<String, HealthCheck> {
            return client.put {
                path = "agent/check/register"
                this.body = mapOf(
                    ("Name" to name),
                    ("Args" to args),
                    ("Id" to id),
                    ("Interval" to interval),
                    ("Notes" to notes),
                    ("DeregisterCriticalServiceAfter" to deregisterCriticalServiceAfter),
                    ("AliasNode" to aliasNode),
                    ("AliasService" to aliasService),
                    ("DockerContainerId" to dockerContainerId),
                    ("GRPC" to grpc),
                    ("GRPCUseTLS" to grpcUseTLS),
                    ("HTTP" to http),
                    ("Method" to method),
                    ("Body" to body),
                    ("Header" to header),
                    ("Timeout" to timeout),
                    ("OutputMaxSize" to outputMaxSize),
                    ("TLSSkipVerify" to tlsSkipVerify),
                    ("TCP" to tcp),
                    ("TTL" to ttl),
                    ("ServiceId" to serviceId),
                    ("Status" to status),
                    ("SuccessBeforePassing" to successBeforePassing),
                    ("FailureBeforeCritical" to failureBeforeCritical)
                ).filterValues { it != null }
            }
        }

        @Suppress("unused", "SpellCheckingInspection")
        suspend fun deregisterCheck(checkId: String) {
            return client.put {
                path = "agent/check/deregister/$checkId"
            }
        }

        @Suppress("unused")
        suspend fun ttlCheckPass(checkId: String, note: String? = null) {
            return client.put {
                path = "agent/check/pass/$checkId"
                this.body = mapOf(("note" to note)).filterValues { it != null }
            }
        }

        @Suppress("unused")
        suspend fun ttlCheckFail(checkId: String, note: String? = null) {
            return client.put {
                path = "agent/check/fail/$checkId"
                this.body = mapOf(("note" to note)).filterValues { it != null }
            }
        }

        @Suppress("unused")
        suspend fun ttlCheckUpdate(checkId: String, status: String, note: String? = null) {
            return client.put {
                path = "agent/check/update/$checkId"
                this.body = mapOf(("status" to status), ("note" to note)).filterValues { it != null }
            }
        }

        @Suppress("unused")
        suspend fun services(filter: String? = null): Map<String, Service> {
            return client.get {
                path = "agent/services"
                param("filter", filter)
            }
        }

        @Suppress("unused")
        suspend fun service(id: String): FullService {
            return client.get {
                path = "agent/service/$id"
            }
        }

        @Suppress("unused")
        suspend fun healthByName(serviceName: String): Map<String, JsonArray> {
            return client.get {
                path = "agent/health/service/name/$serviceName"
            }
        }

        @Suppress("unused")
        suspend fun healthById(serviceId: String) {
            return client.get {
                path = "agent/health/service/id/$serviceId"
            }
        }

        @Suppress("unused")
        suspend fun register(
            name: String,
            id: String? = null,
            tags: List<String>? = null,
            address: String? = null,
            taggedAddress: Map<String, *>? = null,
            meta: Map<String, String>? = null,
            port: Int? = null,
            kind: String? = null,
            proxy: ServiceProxy? = null,
            connect: Connect? = null,
            check: ServiceCheck? = null,
            enableTagOverride: Boolean? = null,
            weights: ServiceWeights? = null,
            replaceExistingChecks: Boolean? = null
        ) {
            return client.put {
                path = "agent/service/register"
                param("replace-existing-checks", replaceExistingChecks)
                body = mapOf(
                    ("Name" to name),
                    ("Id" to id),
                    ("Tags" to tags),
                    ("Address" to address),
                    ("TaggedAddress" to taggedAddress),
                    ("Meta" to meta),
                    ("Port" to port),
                    ("Kind" to kind),
                    ("Proxy" to proxy),
                    ("Connect" to connect),
                    ("Check" to check),
                    ("EnableTagOverride" to enableTagOverride),
                    ("Weights" to weights)
                ).filterValues { it != null }
            }
        }

        @Suppress("unused", "SpellCheckingInspection")
        suspend fun deregister(serviceId: String) {
            return client.put {
                path = "agent/health/deregister/$serviceId"
            }
        }

        @Suppress("unused")
        suspend fun maintenance(serviceId: String, enable: Boolean, reason: String? = null) {
            return client.put {
                path = "agent/health/maintenance/$serviceId"
                param("enable", enable)
                param("reason", reason)
            }
        }

        @Suppress("unused")
        suspend fun authorizeConnect(
            target: String,
            clientCertURI: String,
            clientCertSerial: String,
            namespace: String? = null
        ): JsonObject {
            return client.post {
                path = "agent/connect/authorize"
                body = mapOf(
                    ("Target" to target),
                    ("ClientCertURI" to clientCertURI),
                    ("ClientCertSerial" to clientCertSerial),
                    ("Namespace" to namespace),
                ).filterValues { it != null }
            }
        }

        @Suppress("unused")
        suspend fun caRoots(): JsonObject {
            return client.get {
                path = "agent/connect/ca/roots"
            }
        }

        @Suppress("unused")
        suspend fun serviceCert(serviceName: String): JsonObject {
            return client.get {
                path = "agent/connect/ca/leaf/$serviceName"
            }
        }
    }

    class Catalog(private val client: HttpClient) {
        @Suppress("unused")
        suspend fun register(
            node: String,
            id: String? = null,
            address: String? = null,
            datacenter: String? = null,
            taggedAddress: Map<String, *>? = null,
            meta: Map<String, String>? = null,
            service: Service? = null,
            interval: String? = null,
            check: ServiceCheck? = null,
            skipNodeUpdate: Boolean? = null,
            ns: String? = null
        ) {
            return client.put {
                path = "catalog/register"
                body = mapOf(
                    ("Node" to node),
                    ("ID" to id),
                    ("Datacenter" to datacenter),
                    ("Address" to address),
                    ("TaggedAddresses" to taggedAddress),
                    ("NodeMeta" to meta),
                    ("Service" to service),
                    ("Interval" to interval),
                    ("Check" to check),
                    ("SkipNodeUpdate" to skipNodeUpdate),
                    ("ns" to ns),
                ).filterValues { it != null }
            }
        }

        @Suppress("unused", "SpellCheckingInspection")
        suspend fun deregister(
            node: String,
            datacenter: String? = null,
            checkId: String? = null,
            serviceId: String? = null,
            namespace: String? = null,
        ) {
            return client.put {
                path = "catalog/register"
                body = mapOf(
                    ("Node" to node),
                    ("Datacenter" to datacenter),
                    ("CheckID" to checkId),
                    ("ServiceID" to serviceId),
                    ("Namespace" to namespace)
                ).filterValues { it != null }
            }
        }

        @Suppress("unused")
        suspend fun datacenters(): List<String> {
            return client.get {
                path = "catalog/datacenters"
            }
        }

        @Suppress("unused")
        suspend fun nodes(dc: String? = null, near: String? = null, nodeMeta: String? = null,
                          filter: String? = null): List<Node> {
            return client.get {
                path = "catalog/nodes"
                param("dc", dc)
                param("near", near)
                param("node-meta", nodeMeta)
                param("filter", filter)
            }
        }

        @Suppress("unused")
        suspend fun services(dc: String? = null, nodeMeta: String? = null, ns: String? = null): Map<String, List<String>> {
            return client.get {
                path = "catalog/services"
                param("dc", dc)
                param("node-meta", nodeMeta)
                param("ns", ns)
            }
        }

        @Suppress("unused")
        suspend fun service(
            service: String,
            near: String? = null,
            dc: String? = null,
            nodeMeta: String? = null,
            tag: String? = null,
            ns: String? = null,
            filter: String? = null
        ): CatalogService {
            return client.get {
                path = "catalog/services/$service"
                param("dc", dc)
                param("near", near)
                param("tag", tag)
                param("node-meta", nodeMeta)
                param("ns", ns)
                param("filter", filter)
            }
        }

        @Suppress("unused")
        suspend fun connectService(service: String): CatalogService {
            return client.get {
                path = "catalog/connect/$service"
            }
        }

        @Suppress("unused")
        suspend fun node(node: String, dc: String? = null, ns: String? = null, filter: String? = null): CatalogNode {
            return client.get {
                path = "catalog/node/$node"
                param("dc", dc)
                param("ns", ns)
                param("filter", filter)
            }
        }

        @Suppress("unused")
        suspend fun nodeServices(node: String, dc: String? = null, ns: String? = null, filter: String? = null): CatalogNode {
            return client.get {
                path = "catalog/node-services/$node"
                param("dc", dc)
                param("ns", ns)
                param("filter", filter)
            }
        }

        @Suppress("unused")
        suspend fun gatewayServices(gateway: String, dc: String? = null, ns: String? = null): JsonArray {
            return client.get {
                path = "catalog/gateway-services/$gateway"
                param("dc", dc)
                param("ns", ns)
            }
        }

    }

    class Config(private val client: HttpClient) {
        @Suppress("unused")
        suspend fun apply(kind: String, name: String, key: String, value: String,
                          dc: String? = null, cas: Int? = null, ns: String? = null) {
            return client.put {
                path = "config"
                param("dc", dc)
                param("cas", cas)
                param("ns", ns)
                body = mapOf(("Kind" to kind), ("Name" to name), (key to value))
            }
        }

        @Suppress("unused")
        suspend fun read(kind: String, name: String, dc: String? = null, ns: String? = null): JsonObject {
            return client.get {
                path = "config/$kind/$name"
                param("dc", dc)
                param("ns", ns)
            }
        }

        @Suppress("unused")
        suspend fun list(kind: String, dc: String? = null, ns: String? = null): JsonArray {
            return client.get {
                path = "config/$kind"
                param("dc", dc)
                param("ns", ns)
            }
        }

        @Suppress("unused")
        suspend fun delete(kind: String, name: String, dc: String? = null, ns: String? = null) {
            return client.delete {
                path = "config/$kind/$name"
                param("dc", dc)
                param("ns", ns)
            }
        }
    }

    class Session(private val client: HttpClient) {

        @Suppress("unused")
        enum class Behavior(val label: String) {
            Release("release"),
            Delete("delete"),
        }

        @Suppress("unused")
        suspend fun create(
            name: String,
            node: String? = null,
            dc: String? = null,
            lockDelay: String? = null,
            checks: List<String>? = null,
            behavior: Behavior = Behavior.Release,
            ttl: String? = null
        ): String {
            val res: Map<String, String> = client.put {
                path = "session/create"
                body = hashMapOf(
                    ("dc" to dc),
                    ("LockDelay" to lockDelay),
                    ("Name" to name),
                    ("Node" to node),
                    ("Checks" to checks),
                    ("Behavior" to behavior.label),
                    ("TTL" to ttl),
                ).filterValues { it != null }
            }
            @Suppress("MapGetWithNotNullAssertionOperator")
            return res["ID"]!!
        }

        @Suppress("unused")
        suspend fun delete(session: String, dc: String? = null): Boolean {
            val str: String = client.put {
                path = "session/destroy/$session"
                body = hashMapOf(("dc" to dc)).filterValues { it != null }
            }
            return str.trim().toBoolean()
        }

        @Suppress("unused")
        suspend fun read(session: String, dc: String? = null): List<SessionInfo> {
            return client.get {
                path = "session/info/$session"
                param("dc", dc)
            }
        }

        @Suppress("unused")
        suspend fun listForNode(node: String, dc: String? = null): List<SessionInfo> {
            return client.get {
                path = "session/node/$node"
                param("dc", dc)
            }
        }

        @Suppress("unused")
        suspend fun listActive(dc: String? = null): List<SessionInfo> {
            return client.get {
                path = "session/list"
                param("dc", dc)
            }
        }

        @Suppress("unused")
        suspend fun renew(session: String, dc: String? = null): List<SessionInfo> {
            return client.get {
                path = "session/session/$session"
                param("dc", dc)
            }
        }
    }

    class Kv(private val client: HttpClient) {
        @Suppress("unused")
        suspend fun read(
            key: String, dc: String? = null, recurse: Boolean? = null, index:BigInteger? = null, wait: String? = null
        ): List<KVMetadata>? {
            return client.get {
                path = "kv/$key"
                param("dc", dc)
                param("recurse", recurse)
                param("index", index)
                param("wait", wait)
            }
        }

        @Suppress("unused")
        suspend fun write(
            key: String,
            value: Any,
            dc: String? = null,
            flags: Int? = null,
            cas: Int? = null,
            acquire: String? = null,
            release: String? = null,
        ): Boolean {
            val str: String = client.put {
                path = "kv/$key"
                param("dc", dc)
                param("flags", flags)
                param("cas", cas)
                param("acquire", acquire)
                param("release", release)
                body = value
            }
            return str.trim().toBoolean()
        }

        @Suppress("unused")
        suspend fun delete(
            key: String,
            dc: String? = null,
            recurse: Boolean? = null,
            cas: Int? = null,
        ): Boolean {
            val str: String = client.delete {
                path = "kv/$key"
                param("dc", dc)
                body = mapOf(
                    ("dc" to dc),
                    ("recurse" to recurse),
                    ("cas" to cas),
                ).filterValues { it != null }
            }
            return str.trim().toBoolean()
        }
    }

    override fun close() {
        httpClient.close()
    }

    private fun createHttpClient(config: ConsulConfig): HttpClient {
        val connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
            .setTlsStrategy(
                ClientTlsStrategyBuilder.create()
                    .setSslContext(SSLContexts.createSystemDefault())
                    .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
                    .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setSslContext(SSLContextBuilder().loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE).build())
                    .build()
            )
            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
            .setConnPoolPolicy(PoolReusePolicy.LIFO)
            .setConnectionTimeToLive(TimeValue.ofMinutes(1L))
            .build()

        val httpClientBuilder = HttpConfigBuilder()
            .apply {
                defaultRequest {
                    url = if (config.address.endsWith("/")) "${config.address}v1/" else "${config.address}/v1/"
                    header("Content-Type", "application/json")
                }
            }.apply {
                client {
                    setConnectionManager(connectionManager)
                    setDefaultRequestConfig(
                        RequestConfig.custom()
                            .setConnectTimeout(Timeout.ofSeconds(5))
                            .setResponseTimeout(Timeout.ofSeconds(60 * 5 + 10))
                            .setCookieSpec(StandardCookieSpec.STRICT)
                            .build()
                    )
                    setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
                }
            }
        return HttpClient(httpClientBuilder)
    }
}

@DslMarker
annotation class DslConsulClient

@Suppress("MemberVisibilityCanBePrivate")
@DslConsulClient
class ConsulConfigBuilder {
    var address: String? = null

    fun build(): ConsulConfig {
        val address = address ?: throw IllegalArgumentException("address must be set")
        return ConsulConfig(address)
    }
}

data class ConsulConfig(
    var address: String
)

fun main() {
    println("foo")
}
package com.github.barakb.consul

import com.github.barakb.consul.data.FullService
import com.github.barakb.consul.data.HealthCheck
import com.github.barakb.consul.data.Member
import com.github.barakb.consul.data.Service
import com.github.barakb.http.HttpClient
import com.github.barakb.http.HttpConfigBuilder
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
import java.time.Duration

@Suppress("unused")
private val logger = KotlinLogging.logger {}

@Suppress("unused")
class ConsulClient(init: ConsulConfigBuilder.() -> Unit) : Closeable {
    private val config = ConsulConfigBuilder().apply(init).build()
    private val httpClient = createHttpClient(config)

    @Suppress("unused")
    val agent = Agent(httpClient)

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
                            .setResponseTimeout(Timeout.ofSeconds(5))
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
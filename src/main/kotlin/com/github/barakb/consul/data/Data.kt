package com.github.barakb.consul.data

import com.google.gson.annotations.SerializedName
import java.math.BigInteger
import java.util.*

data class Member(
    @Suppress("SpellCheckingInspection") @SerializedName("Addr") val address: String? = null,
    @SerializedName("Name") val name: String? = null,
    @SerializedName("Port") val port: Int? = null,
    @SerializedName("Tags") val tags: Map<String, String> = mapOf(),
    @SerializedName("Status") val status: Int? = null,
    @SerializedName("ProtocolMin") val protocolMin: Int? = null,
    @SerializedName("ProtocolMax") val protocolMax: Int? = null,
    @SerializedName("ProtocolCur") val protocolCur: Int? = null,
    @SerializedName("DelegateMin") val delegateMin: Int? = null,
    @SerializedName("DelegateMax") val delegateMax: Int? = null,
    @SerializedName("DelegateCur") val delegateCur: Int? = null
)

data class Agent(
    @SerializedName("Config") val config: Config? = null,
    @SerializedName("DebugConfig") val debugConfig: DebugConfig? = null,
    @SerializedName("Member") val member: Member? = null
)

data class Config(
    @SerializedName("Datacenter") val datacenter: String? = null,
    @SerializedName("NodeName") val nodeName: String? = null,
    @SerializedName("Revision") val revision: String? = null,
    @SerializedName("Server") val server: Boolean = false,
    @SerializedName("Version") val version: String? = null
)

data class DebugConfig(
    @SerializedName("Bootstrap") val bootstrap: Boolean = false,
    @SerializedName("SkipLeaveOnInt") val skipLeaveOnInt: Boolean = false,
    @SerializedName("Datacenter") val datacenter: String? = null,
    @SerializedName("DataDir") val dataDir: String? = null,
    @Suppress("SpellCheckingInspection") @SerializedName("DNSRecursors") val recursors: List<String> = listOf(),
    @SerializedName("DNSDomain") val dnsDomain: String? = null,
    @SerializedName("LogLevel") val logLevel: String? = null,
    @SerializedName("NodeName") val nodeName: String? = null,
    @Suppress("SpellCheckingInspection") @SerializedName("ClientAddrs") val clientAddrs: List<String> = listOf(),
    @Suppress("SpellCheckingInspection") @SerializedName("BindAddr") val bindAddr: String? = null,
    @SerializedName("LeaveOnTerm") val leaveOnTerm: Boolean = false,
    @SerializedName("EnableDebug") val enableDebug: Boolean = false,
    @SerializedName("VerifyIncoming") val verifyIncoming: Boolean = false,
    @SerializedName("VerifyOutgoing") val verifyOutgoing: Boolean = false,
    @SerializedName("CAFile") val caFile: String? = null,
    @SerializedName("CertFile") val certFile: String? = null,
    @SerializedName("KeyFile") val keyFile: String? = null,
    @SerializedName("UiDir") val uiDir: List<String> = listOf(),
    @SerializedName("PidFile") val pidFile: String? = null,
    @SerializedName("EnableSyslog") val enableSyslog: Boolean = false,
    @SerializedName("RejoinAfterLeave") val rejoinAfterLeave: Boolean = false,
    @Suppress("SpellCheckingInspection") @SerializedName("AdvertiseAddrLAN") val advertiseAddrLAN: String? = null,
    @Suppress("SpellCheckingInspection") @SerializedName("AdvertiseAddrWAN") val advertiseAddrWAN: String? = null
)

data class HealthCheck(
    @SerializedName("CheckID") val checkId: String? = null,
    @SerializedName("Status") val status: String? = null,
    @SerializedName("Notes") val notes: List<String> = listOf(),
    @SerializedName("Output") val output: List<String> = listOf(),
    @SerializedName("ServiceID") val serviceId: List<String> = listOf(),
    @SerializedName("ServiceName") val serviceName: List<String> = listOf(),
    @SerializedName("ServiceTags") val serviceTags: List<String> = listOf(),
    @SerializedName("Name") val name: String? = null,
    @SerializedName("Node") val node: String? = null
)

data class Service(
    @SerializedName("Address") val address: String? = null,
    @SerializedName("ID") val id: String? = null,
    @SerializedName("Port") val port: Int? = null,
    @SerializedName("Service") val service: String? = null,
    @SerializedName("EnableTagOverride") val enableTagOverride: Boolean = false,
    @SerializedName("Tags") val tags: List<String> = listOf(),
    @SerializedName("Meta") val meta: List<String> = listOf(),
    @SerializedName("Weights") val weights: ServiceWeights? = null
)

data class ServiceWeights(
    @SerializedName("Passing") val passing: Int? = null, @SerializedName("Warning") val warning: Int? = null
)

data class FullService(
    @SerializedName("Address") val address: String? = null,
    @SerializedName("ID") val id: String? = null,
    @SerializedName("Port") val port: Int? = null,
    @SerializedName("Kind") val kind: String? = null,
    @SerializedName("Service") val service: String? = null,
    @SerializedName("Tags") val tags: List<String> = listOf(),
    @SerializedName("Meta") val meta: List<String> = listOf(),
    @SerializedName("Weights") val weights: ServiceWeights? = null,
    @SerializedName("EnableTagOverride") val enableTagOverride: Boolean = false,
    @SerializedName("ContentHash") val contentHash: String? = null,
    @SerializedName("Proxy") val proxy: ServiceProxy? = null
)

data class ServiceProxy(
    @SerializedName("DestinationServiceName") val destinationServiceName: String? = null,
    @SerializedName("Config") val config: List<String> = listOf(),
    @SerializedName("DestinationServiceID") val destinationServiceId: String? = null,
    @SerializedName("LocalServiceAddress") val localServiceAddress: String? = null,
    @SerializedName("LocalServicePort") val localServicePort: Int? = null,
    @SerializedName("Upstreams") val upstreams: List<ServiceProxyUpstream> = listOf()
)

data class ServiceProxyUpstream(
    @SerializedName("DestinationType") val destinationType: String? = null,
    @SerializedName("DestinationName") val destinationName: String? = null,
    @SerializedName("LocalBindPort") val localBindPort: Int? = null
)

data class ServiceCheck(
    @SerializedName("Service") val service: Service? = null,
    @SerializedName("Checks") val checks: List<HealthCheck> = listOf(),
    @SerializedName("Node") val node: Node? = null
)

data class TaggedAddresses(
    @SerializedName("wan") val wan: String? = null, @SerializedName("lan") val lan: String? = null
)

data class Node(
    @SerializedName("Datacenter") val datacenter: String? = null,
    @SerializedName("TaggedAddresses") val taggedAddresses: TaggedAddresses? = null,
    @SerializedName("Meta") val meta: Map<String, String>? = null,
    @SerializedName("Node") val node: String? = null,
    @SerializedName("Address") val address: String? = null
)

data class SidecarService(
    @SerializedName("Tags") val tags: List<String>? = null,
    @SerializedName("Port") val port: String? = null,
    @SerializedName("Proxy") val proxy: ServiceProxy? = null,
)

data class Connect(
    @SerializedName("Native") val native: Boolean? = null,
    @SerializedName("Proxy") val proxy: ServiceProxy? = null,
    @SerializedName("SidecarService") val sidecarService: SidecarService? = null
)

data class CatalogService(
    @SerializedName("Datacenter") val datacenter: String? = null,
    @SerializedName("ServiceName") val serviceName: String? = null,
    @SerializedName("ServiceID") val serviceId: String? = null,
    @SerializedName("ServiceAddress") val serviceAddress: String? = null,
    @SerializedName("ServiceEnableTagOverride") val serviceEnableTagOverride: Boolean = false,
    @SerializedName("ServicePort") val servicePort: Int? = null,
    @SerializedName("ServiceTags") val serviceTags: List<String> = listOf(),
    @SerializedName("ServiceMeta") val serviceMeta: List<String> = listOf(),
    @SerializedName("ServiceWeights") val serviceWeights: ServiceWeights? = null,
    @SerializedName("NodeMeta") val nodeMeta: List<String> = listOf(),
    @SerializedName("Address") val address: String? = null,
    @SerializedName("Node") val node: String? = null
)

data class CatalogNode(
    @SerializedName("Services") val services: Map<String, Service>? = null,
    @SerializedName("Node") val node: Node? = null
)

data class SessionInfo(
    @SerializedName("Node") val node: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("LockDelay") val localDelay: Long,
    @SerializedName("Behavior") val behavior: String,
    @SerializedName("TTL") val ttl: String,
    @SerializedName("NodeChecks") val nodeChecks: List<String>,
    @SerializedName("ServiceChecks") val serviceChecks: List<String>? = null,
    @SerializedName("CreateIndex") val createIndex: BigInteger,
    @SerializedName("ModifyIndex") val ModifyIndex: BigInteger
)

data class KVMetadata(
    @SerializedName("CreateIndex") val createIndex: BigInteger,
    @SerializedName("ModifyIndex") val modifyIndex: BigInteger,
    @SerializedName("LockIndex") val lockIndex: BigInteger,
    @SerializedName("Session") val session: String?,
    @SerializedName("Key") val key: String,
    @SerializedName("Flags") val flags: Int,
    @SerializedName("Value") val value: String,
) {
    @Suppress("unused")
    fun decoded(): ByteArray {
        return Base64.getDecoder().decode(value)
    }
}


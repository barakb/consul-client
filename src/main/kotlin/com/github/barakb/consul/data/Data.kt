package com.github.barakb.consul.data

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("Addr") val address: String? = null,
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
    @SerializedName("DNSRecursors") val recursors: List<String> = listOf(),
    @SerializedName("DNSDomain") val dnsDomain: String? = null,
    @SerializedName("LogLevel") val logLevel: String? = null,
    @SerializedName("NodeName") val nodeName: String? = null,
    @SerializedName("ClientAddrs") val clientAddrs: List<String> = listOf(),
    @SerializedName("BindAddr") val bindAddr: String? = null,
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
    @SerializedName("AdvertiseAddrLAN") val advertiseAddrLAN: String? = null,
    @SerializedName("AdvertiseAddrWAN") val advertiseAddrWAN: String? = null
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


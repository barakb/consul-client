package com.github.barakb.consul

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

private val logger = KotlinLogging.logger("Example")

@ExperimentalTime
fun main(): Unit = runBlocking {
    ConsulClient {
        address = "http://127.0.0.1:8500"
    }.use { client ->
//        val nodes = client.agent.list()
//        logger.info("$nodes")
//        val self = client.agent.self()
//        logger.info("$self")
//        logger.info("${client.agent.metrics()}")
//        logger.info("${client.agent.checks()}")
//        @Suppress("SpellCheckingInspection")
//        val session = client.session.create(node = "barakb-mac.local")
//        logger.info("session created $session")
//        val info = client.session.read(session)
//        logger.info("info $info")
//        val wr = client.kv.write(key = "barak", value="Bar Orion", acquire = session)
//        logger.info("wr $wr")
//        val rv = client.kv.read("barak")
//        logger.info("rv $rv")
//        logger.info("rv map decode ${rv.map { String(it.decoded()) }}")
//
//        val deleted = client.session.delete(session)
//        logger.info("deleted: $deleted")

        // client.session.create()
//        val nodes = client.catalog.nodes().map { it.name }
//        logger.info("discover nodes: $nodes")

        val service = "dbServer"
        val key = "$service/leader"
        client.kv.read(key)?.forEach { it.session?.let { session -> client.session.delete(session) } }
        client.kv.delete(key)
        var session = client.session.create(service)
        suspend fun watch(session: String, key: String): LockState {
            while(true) {
                val read = client.kv.read(key)
                if (read == null || read.isEmpty()) {
                    return LockState.Free
                }
                if (read[0].session == null) {
                    return LockState.Free
                }
                if (read[0].session != session) {
                    return LockState.Busy
                }
                val consulIndex = read[0].consulIndex()
                val readWait = client.kv.read(key = key, index = consulIndex)
                if (readWait == null || readWait.isEmpty()) {
                    return LockState.Free
                }
                if (readWait[0].session == null) {
                    return LockState.Free
                }
                if (readWait[0].session != session) {
                    return LockState.Busy
                }
            }
        }

        logger.info("my session is $session")
        val acquired = client.kv.write(key = key, value = "me", acquire = session)
        logger.info("acquired: $acquired")
        if(acquired){
            logger.info("Im the leader [ $session ] !!!")
        }
        while(true){
            val sinfo = client.session.read(session)
            if(sinfo.isEmpty()){
                logger.info("creating a new session")
                session = client.session.create(service)
            }
            val lockState = watch(session, key)
            if(lockState == LockState.Free){
                val ac = client.kv.write(key = key, value = "me", acquire = session)
                if(ac){
                    logger.info("Im the leader [ $session ] !!!")
                }else{
                    delay(15.seconds)
                }
            }
        }
//        val values = client.kv.read(key) ?: listOf()
//        logger.info("values=$values")
//        logger.info("consulIndex=${values.map { it.consulIndex() }}")
//        logger.info("read: ${values.map { String(it.decoded()) }}")
    }
}

enum class LockState { Free, Busy }

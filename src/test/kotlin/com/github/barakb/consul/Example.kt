package com.github.barakb.consul

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

private val logger = KotlinLogging.logger("Example")

fun main() = runBlocking {
    ConsulClient {
        address = "http://127.0.0.1:8500"
    }.use { client ->
//        val nodes = client.agent.list()
//        logger.info("$nodes")
//        val self = client.agent.self()
//        logger.info("$self")
        logger.info("${client.agent.metrics()}")
//        logger.info("${client.agent.checks()}")
        @Suppress("SpellCheckingInspection")
        val session = client.session.create(node = "barakb-mac.local")
        logger.info("session created $session")
        val info = client.session.read(session)
        logger.info("info $info")
        val wr = client.kv.write(key = "barak", value="Bar Orion", acquire = session)
        logger.info("wr $wr")
        val rv = client.kv.read("barak")
        logger.info("rv $rv")
        logger.info("rv map decode ${rv.map { String(it.decoded()) }}")

        val deleted = client.session.delete(session)
        logger.info("deleted: $deleted")

    }
}
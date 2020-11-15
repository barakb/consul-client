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
        @Suppress("SpellCheckingInspection") val session = client.session.create(node = "barakb-mac.local")
        logger.info("session created $session")
        val info = client.session.read(session)
        logger.info("info $info")
        val deleted = client.session.delete(session)
        logger.info("deleted: $deleted")

    }
}
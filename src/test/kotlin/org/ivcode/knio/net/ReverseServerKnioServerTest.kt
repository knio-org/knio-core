package org.ivcode.knio.net

import org.ivcode.knio.test.servers.createKnioServerSocket
import org.ivcode.knio.test.servers.reverse.KnioReverseServer

/**
 * Runs the ReverseServerTest using KnioReverseServer
 */
class ReverseServerKnioServerTest: ReverseServerTest<KnioReverseServer>() {

    override suspend fun startReverseServer(isSSL: Boolean): KnioReverseServer {
        val server = KnioReverseServer(createKnioServerSocket(isSSL))
        server.start()

        return server
    }

}

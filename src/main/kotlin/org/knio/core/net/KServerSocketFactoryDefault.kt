package org.knio.core.net

import org.knio.core.context.KnioContext
import java.net.InetAddress
import java.net.InetSocketAddress

internal class KServerSocketFactoryDefault (
    private val context: KnioContext
): KServerSocketFactory {
    override suspend fun createServerSocket() =
        KServerSocketImpl(context.channelFactory.openServerSocketChannel())

    override suspend fun createServerSocket(port: Int) =
        createServerSocket().open(port)

    override suspend fun createServerSocket(port: Int, backlog: Int) =
        createServerSocket().open(port, backlog)

    override suspend fun createServerSocket(port: Int, backlog: Int, ifAddress: InetAddress) =
        createServerSocket().open(port, backlog, ifAddress)

    private suspend fun KServerSocket.open(port: Int, backlog: Int = 0, bindAddress: InetAddress? = null): KServerSocket {
        try {
            require(port in 1..0xfffe) { "Port value out of range: $port" }

            val bkLog = if (backlog < 1) 50 else backlog
            val local = InetSocketAddress(bindAddress, port)

            bind(local, bkLog)
        } catch  (th: Throwable) {
            close()
        }

        return this
    }
}
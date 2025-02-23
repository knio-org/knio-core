package org.knio.core.io

import org.knio.core.context.getKnioContext
import org.knio.core.context.getCharBufferSize

suspend fun KReader.readText(): String {
    val context = getKnioContext()
    val buffer = context.byteBufferPool.acquire(getCharBufferSize(context.maxTaskBufferSize))

    try {
        val buff = buffer.asCharBuffer()

        val sb = StringBuilder()
        var read = this.read(buff)
        buff.flip()
        while (read != -1) {
            sb.append(buff)

            buff.clear()
            read = this.read(buff)
            buff.flip()
        }

        return sb.toString()
    } finally {
        context.byteBufferPool.release(buffer)
    }
}

suspend fun KReader.buffered(bufferSize: Int? = null): KBufferedReader {
    return KBufferedReader.open(this, bufferSize)
}
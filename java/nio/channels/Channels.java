/*
 * @(#)Channels.java	1.22 03/01/23
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.nio.channels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import sun.nio.ch.ChannelInputStream;
import sun.nio.cs.StreamDecoder;
import sun.nio.cs.StreamEncoder;


/**
 * Utility methods for channels and streams.
 *
 * <p> This class defines static methods that support the interoperation of the
 * stream classes of the <tt>{@link java.io}</tt> package with the channel
 * classes of this package.  </p>
 *
 *
 * @author Mark Reinhold
 * @author Mike McCloskey
 * @author JSR-51 Expert Group
 * @version 1.22, 03/01/23
 * @since 1.4
 */

public final class Channels {

    private Channels() { }		// No instantiation

 /**
  * Write all remaining bytes in buffer to the given channel.
  * If the channel is selectable then it must be configured blocking.
  */ 
  private static void writeFullyImpl(WritableByteChannel ch, ByteBuffer bb) 
  throws IOException {
     while (bb.remaining() > 0) {
        int n = ch.write(bb);
        if (n <= 0)
           throw new RuntimeException("no bytes written");
  }
  }



 /**
  * Write all remaining bytes in buffer to the given channel.
  *
  * @throws  IllegalBlockingException
  *          If the channel is selectable and configured non-blocking.
  */
  private static void writeFully(WritableByteChannel ch, ByteBuffer bb)
  throws IOException {
     if (ch instanceof SelectableChannel) {
         SelectableChannel sc = (SelectableChannel)ch;
         synchronized (sc.blockingLock()) {
     if (!sc.isBlocking())
         throw new IllegalBlockingModeException();
         writeFullyImpl(ch, bb);
     }
     } else {
           writeFullyImpl(ch, bb);
     }
  }


    // -- Byte streams from channels --

    /**
     * Constructs a stream that reads bytes from the given channel.
     *
     * <p> The <tt>read</tt> methods of the resulting stream will throw an
     * {@link IllegalBlockingModeException} if invoked while the underlying
     * channel is in non-blocking mode.  The stream will not be buffered, and
     * it will not support the {@link InputStream#mark mark} or {@link
     * InputStream#reset reset} methods.  The stream will be safe for access by
     * multiple concurrent threads.  Closing the stream will in turn cause the
     * channel to be closed.  </p>
     *
     * @param  ch
     *         The channel from which bytes will be read
     *
     * @return  A new input stream
     */
    public static InputStream newInputStream(ReadableByteChannel ch) {
	return new sun.nio.ch.ChannelInputStream(ch);
    }

    /**
     * Constructs a stream that writes bytes to the given channel.
     *
     * <p> The <tt>write</tt> methods of the resulting stream will throw an
     * {@link IllegalBlockingModeException} if invoked while the underlying
     * channel is in non-blocking mode.  The stream will not be buffered.  The
     * stream will be safe for access by multiple concurrent threads.  Closing
     * the stream will in turn cause the channel to be closed.  </p>
     *
     * @param  ch
     *         The channel to which bytes will be written
     *
     * @return  A new output stream
     */
    public static OutputStream newOutputStream(final WritableByteChannel ch) {
	return new OutputStream() {

		private ByteBuffer bb = null;
		private byte[] bs = null; 	// Invoker's previous array
		private byte[] b1 = null;

                public synchronized void write(int b) throws IOException {
                   if (b1 == null)
                        b1 = new byte[1];
                    b1[0] = (byte)b;
                    this.write(b1);
                }

                public synchronized void write(byte[] bs, int off, int len)
                    throws IOException
                {
                    if ((off < 0) || (off > bs.length) || (len < 0) ||
                        ((off + len) > bs.length) || ((off + len) < 0)) {
                        throw new IndexOutOfBoundsException();
                    } else if (len == 0) {
                        return;
                    }
                    ByteBuffer bb = ((this.bs == bs)
                                     ? this.bb
                                     : ByteBuffer.wrap(bs));
                    bb.limit(Math.min(off + len, bb.capacity()));
                    bb.position(off);
                    this.bb = bb;
                    this.bs = bs;
                    Channels.writeFully(ch, bb);
                }

		public void close() throws IOException {
		    ch.close();
		}

	    };
    }


    // -- Channels from streams --

    /**
     * Constructs a channel that reads bytes from the given stream.
     *
     * <p> The resulting channel will not be buffered; it will simply redirect
     * its I/O operations to the given stream.  Closing the channel will in
     * turn cause the stream to be closed.  </p>
     *
     * @param  in
     *         The stream from which bytes are to be read
     *
     * @return  A new readable byte channel
     */
    public static ReadableByteChannel newChannel(final InputStream in) {
        if (in instanceof FileInputStream) {
            String inClass = in.getClass().toString();
            if (inClass.equals("java.io.FileInputStream"))
               return ((FileInputStream)in).getChannel();
        }
	return new ReadableByteChannelImpl(in);
    }

    private static class ReadableByteChannelImpl
        extends AbstractInterruptibleChannel	// Not really interruptible
        implements ReadableByteChannel
    {
        InputStream in;
        private static final int TRANSFER_SIZE = 8192;
        private byte buf[] = new byte[0];
        private boolean open = true;
        private Object readLock = new Object();

        ReadableByteChannelImpl(InputStream in) {
            this.in = in;
        }

        public int read(ByteBuffer dst) throws IOException {
            int len = dst.remaining();
            int totalRead = 0;
            int bytesRead = 0;
            synchronized (readLock) {
                while (totalRead < len) {
                    int bytesToRead = Math.min((len - totalRead),
                                               TRANSFER_SIZE);
                    if (buf.length < bytesToRead)
                        buf = new byte[bytesToRead];
                    if ((totalRead > 0) && !(in.available() > 0))
                        break; // block at most once
                    try {
                        begin();
                        bytesRead = in.read(buf, 0, bytesToRead);
                    } finally {
                        end(bytesRead > 0);
                    }
                    if (bytesRead < 0)
                        break;
                    else
                        totalRead += bytesRead;
                    dst.put(buf, 0, bytesRead);
                }
                if ((bytesRead < 0) && (totalRead == 0))
                    return -1;

                return totalRead;
            }
        }

        protected void implCloseChannel() throws IOException {
            in.close();
            open = false;
        }
    }


    /**
     * Constructs a channel that writes bytes to the given stream.
     *
     * <p> The resulting channel will not be buffered; it will simply redirect
     * its I/O operations to the given stream.  Closing the channel will in
     * turn cause the stream to be closed.  </p>
     *
     * @param  out
     *         The stream to which bytes are to be written
     *
     * @return  A new writable byte channel
     */
    public static WritableByteChannel newChannel(final OutputStream out) {
        if (out instanceof FileOutputStream) {
            String outClass = out.getClass().toString();
            if (outClass.equals("java.io.FileOutputStream"))
                return ((FileOutputStream)out).getChannel();
        }
	return new WritableByteChannelImpl(out);
    }

    private static class WritableByteChannelImpl
        extends AbstractInterruptibleChannel	// Not really interruptible
        implements WritableByteChannel
    {
        OutputStream out;
        private static final int TRANSFER_SIZE = 8192;
        private byte buf[] = new byte[0];
        private boolean open = true;
        private Object writeLock = new Object();

        WritableByteChannelImpl(OutputStream out) {
            this.out = out;
        }

        public int write(ByteBuffer src) throws IOException {
            int len = src.remaining();
            int totalWritten = 0;
            synchronized (writeLock) {
                while (totalWritten < len) {
                    int bytesToWrite = Math.min((len - totalWritten),
                                                TRANSFER_SIZE);
                    if (buf.length < bytesToWrite)
                        buf = new byte[bytesToWrite];
                    src.get(buf, 0, bytesToWrite);
                    try {
                        begin();
                        out.write(buf, 0, bytesToWrite);
                    } finally {
                        end(bytesToWrite > 0);
                    }
		    totalWritten += bytesToWrite;
                }
                return totalWritten;
            }
        }

        protected void implCloseChannel() throws IOException {
            out.close();
            open = false;
        }
    }


    // -- Character streams from channels --

    /**
     * Constructs a reader that decodes bytes from the given channel using the
     * given decoder.
     *
     * <p> The resulting stream will contain an internal input buffer of at
     * least <tt>minBufferCap</tt> bytes.  The stream's <tt>read</tt> methods
     * will, as needed, fill the buffer by reading bytes from the underlying
     * channel; if the channel is in non-blocking mode when bytes are to be
     * read then an {@link IllegalBlockingModeException} will be thrown.  The
     * resulting stream will not otherwise be buffered, and it will not support
     * the {@link Reader#mark mark} or {@link Reader#reset reset} methods.
     * Closing the stream will in turn cause the channel to be closed.  </p>
     *
     * @param  ch
     *         The channel from which bytes will be read
     *
     * @param  dec
     *         The charset decoder to be used
     *
     * @param  minBufferCap
     *         The minimum capacity of the internal byte buffer,
     *         or <tt>-1</tt> if an implementation-dependent
     *         default capacity is to be used
     *
     * @return  A new reader
     */
    public static Reader newReader(ReadableByteChannel ch,
				   CharsetDecoder dec,
				   int minBufferCap)
    {
	dec.reset();
	return StreamDecoder.forDecoder(ch, dec, minBufferCap);
    }

    /**
     * Constructs a reader that decodes bytes from the given channel according
     * to the named charset.
     *
     * <p> An invocation of this method of the form
     *
     * <blockquote><pre>
     * Channels.newReader(ch, csname)</pre></blockquote>
     *
     * behaves in exactly the same way as the expression
     *
     * <blockquote><pre>
     * Channels.newReader(ch,
     *                    Charset.forName(csName)
     *                        .newDecoder(),
     *                    -1);</pre></blockquote>
     *
     * @param  ch
     *         The channel from which bytes will be read
     *
     * @param  csName
     *         The name of the charset to be used
     *
     * @return  A new reader
     *
     * @throws  UnsupportedCharsetException
     *          If no support for the named charset is available
     *          in this instance of the Java virtual machine
     */
    public static Reader newReader(ReadableByteChannel ch,
				   String csName)
    {
	return newReader(ch, Charset.forName(csName).newDecoder(), -1);
    }

    /**
     * Constructs a writer that encodes characters using the given encoder and
     * writes the resulting bytes to the given channel.
     *
     * <p> The resulting stream will contain an internal output buffer of at
     * least <tt>minBufferCap</tt> bytes.  The stream's <tt>write</tt> methods
     * will, as needed, flush the buffer by writing bytes to the underlying
     * channel; if the channel is in non-blocking mode when bytes are to be
     * written then an {@link IllegalBlockingModeException} will be thrown.
     * The resulting stream will not otherwise be buffered.  Closing the stream
     * will in turn cause the channel to be closed.  </p>
     *
     * @param  ch
     *         The channel to which bytes will be written
     *
     * @param  enc
     *         The charset encoder to be used
     *
     * @param  minBufferCap
     *         The minimum capacity of the internal byte buffer,
     *         or <tt>-1</tt> if an implementation-dependent
     *         default capacity is to be used
     *
     * @return  A new writer
     */
    public static Writer newWriter(final WritableByteChannel ch,
				   final CharsetEncoder enc,
				   final int minBufferCap)
    {
        enc.reset();
	return StreamEncoder.forEncoder(ch, enc, minBufferCap);
    }

    /**
     * Constructs a writer that encodes characters according to the named
     * charset and writes the resulting bytes to the given channel.
     *
     * <p> An invocation of this method of the form
     *
     * <blockquote><pre>
     * Channels.newWriter(ch, csname)</pre></blockquote>
     *
     * behaves in exactly the same way as the expression
     *
     * <blockquote><pre>
     * Channels.newWriter(ch,
     *                    Charset.forName(csName)
     *                        .newEncoder(),
     *                    -1);</pre></blockquote>
     *
     * @param  ch
     *         The channel to which bytes will be written
     *
     * @param  csName
     *         The name of the charset to be used
     *
     * @return  A new writer
     *
     * @throws  UnsupportedCharsetException
     *          If no support for the named charset is available
     *          in this instance of the Java virtual machine
     */
    public static Writer newWriter(WritableByteChannel ch,
				   String csName)
    {
	return newWriter(ch, Charset.forName(csName).newEncoder(), -1);
    }

}

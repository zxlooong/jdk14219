/*
 * @(#)ByteBufferAs-X-Buffer.java	1.14 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;


class ByteBufferAsShortBufferRL			// package-private
    extends ByteBufferAsShortBufferL
{








    ByteBufferAsShortBufferRL(ByteBuffer bb) {	// package-private












	super(bb);

    }

    ByteBufferAsShortBufferRL(ByteBuffer bb,
				     int mark, int pos, int lim, int cap,
				     int off)
    {





	super(bb, mark, pos, lim, cap, off);

    }

    public ShortBuffer slice() {
	int pos = this.position();
	int lim = this.limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);
	int off = (pos << 1) + offset;
	return new ByteBufferAsShortBufferRL(bb, -1, 0, rem, rem, off);
    }

    public ShortBuffer duplicate() {
	return new ByteBufferAsShortBufferRL(bb,
						    this.markValue(),
						    this.position(),
						    this.limit(),
						    this.capacity(),
						    offset);
    }

    public ShortBuffer asReadOnlyBuffer() {








	return duplicate();

    }

















    public ShortBuffer put(short x) {




	throw new ReadOnlyBufferException();

    }

    public ShortBuffer put(int i, short x) {




	throw new ReadOnlyBufferException();

    }

    public ShortBuffer compact() {
















	throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
	return bb.isDirect();
    }

    public boolean isReadOnly() {
	return true;
    }







































    public ByteOrder order() {




	return ByteOrder.LITTLE_ENDIAN;

    }

}

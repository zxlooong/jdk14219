/*
 * @(#)Direct-X-Buffer.java	1.45 03/04/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.FileChannelImpl;


class DirectDoubleBufferU

    extends DoubleBuffer



    implements DirectBuffer
{



    // Cached unsafe-access object
    protected static final Unsafe unsafe = Bits.unsafe();

    // Cached unaligned-access capability
    protected static final boolean unaligned = Bits.unaligned();

    // Base address, used in all indexing calculations
    // NOTE: moved up to Buffer.java for speed in JNI GetDirectBufferAddress
    //    protected long address;

    // If this buffer is a view of another buffer then we keep a reference to
    // that buffer so that its memory isn't freed before we're done with it
    protected Object viewedBuffer = null;

    public Object viewedBuffer() {
        return viewedBuffer;
    }




































    public Cleaner cleaner() { return null; }





























































    // For duplicates and slices
    //
    DirectDoubleBufferU(DirectBuffer db,	        // package-private
			       int mark, int pos, int lim, int cap,
			       int off)
    {

	super(mark, pos, lim, cap);
	address = db.address() + off;
	viewedBuffer = db;






    }

    public DoubleBuffer slice() {
	int pos = this.position();
	int lim = this.limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);
	int off = (pos << 3);
	return new DirectDoubleBufferU(this, -1, 0, rem, rem, off);
    }

    public DoubleBuffer duplicate() {
	return new DirectDoubleBufferU(this,
					      this.markValue(),
					      this.position(),
					      this.limit(),
					      this.capacity(),
					      0);
    }

    public DoubleBuffer asReadOnlyBuffer() {

	return new DirectDoubleBufferRU(this,
					   this.markValue(),
					   this.position(),
					   this.limit(),
					   this.capacity(),
					   0);



    }



    public long address() {
	return address;
    }

    private long ix(int i) {
	return address + (i << 3);
    }

    public double get() {
	return ((unsafe.getDouble(ix(nextGetIndex()))));
    }

    public double get(int i) {
	return ((unsafe.getDouble(ix(checkIndex(i)))));
    }

    public DoubleBuffer get(double[] dst, int offset, int length) {

	if ((length << 3) > Bits.JNI_COPY_TO_ARRAY_THRESHOLD) {
	    checkBounds(offset, length, dst.length);
	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);
	    if (length > rem)
		throw new BufferUnderflowException();

	    if (order() != ByteOrder.nativeOrder())
		Bits.copyToLongArray(ix(pos), dst,
					  offset << 3,
					  length << 3);
	    else
		Bits.copyToByteArray(ix(pos), dst,
				     offset << 3,
				     length << 3);
	    position(pos + length);
	} else {
	    super.get(dst, offset, length);
	}
	return this;



    }



    public DoubleBuffer put(double x) {

	unsafe.putDouble(ix(nextPutIndex()), ((x)));
	return this;



    }

    public DoubleBuffer put(int i, double x) {

	unsafe.putDouble(ix(checkIndex(i)), ((x)));
	return this;



    }

    public DoubleBuffer put(DoubleBuffer src) {

	if (src instanceof DirectDoubleBufferU) {
	    if (src == this)
		throw new IllegalArgumentException();
	    DirectDoubleBufferU sb = (DirectDoubleBufferU)src;

	    int spos = sb.position();
	    int slim = sb.limit();
	    assert (spos <= slim);
	    int srem = (spos <= slim ? slim - spos : 0);

	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);

	    if (srem > rem)
		throw new BufferOverflowException();
 	    unsafe.copyMemory(sb.ix(spos), ix(pos), srem << 3);
 	    sb.position(spos + srem);
 	    position(pos + srem);
	} else if (!src.isDirect()) {

	    int spos = src.position();
	    int slim = src.limit();
	    assert (spos <= slim);
	    int srem = (spos <= slim ? slim - spos : 0);

	    put(src.hb, src.offset + spos, srem);
	    src.position(spos + srem);

	} else {
	    super.put(src);
	}
	return this;



    }

    public DoubleBuffer put(double[] src, int offset, int length) {

	if ((length << 3) > Bits.JNI_COPY_FROM_ARRAY_THRESHOLD) {
	    checkBounds(offset, length, src.length);
	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);
	    if (length > rem)
		throw new BufferOverflowException();

	    if (order() != ByteOrder.nativeOrder()) 
		Bits.copyFromLongArray(src, offset << 3,
					    ix(pos), length << 3);
	    else
		Bits.copyFromByteArray(src, offset << 3,
				       ix(pos), length << 3);
	    position(pos + length);
	} else {
	    super.put(src, offset, length);
	}
	return this;



    }
    
    public DoubleBuffer compact() {

	int pos = position();
	int lim = limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);

 	unsafe.copyMemory(ix(pos), ix(0), rem << 3);
 	position(rem);
	limit(capacity());
	return this;



    }

    public boolean isDirect() {
	return true;
    }

    public boolean isReadOnly() {
	return false;
    }











































    public ByteOrder order() {





	return ((ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN)
		? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

    }


























}

/*
 * @(#)Heap-X-Buffer.java	1.27 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;


/**

 * A read/write HeapCharBuffer.






 */

class HeapCharBuffer
    extends CharBuffer
{

    // For speed these fields are actually declared in X-Buffer;
    // these declarations are here as documentation
    /*

    protected final char[] hb;
    protected final int offset;

    */

    HeapCharBuffer(int cap, int lim) {		// package-private

	super(-1, 0, lim, cap, new char[cap], 0);
	/*
	hb = new char[cap];
	offset = 0;
	*/




    }

    HeapCharBuffer(char[] buf, int off, int len) { // package-private

	super(-1, off, off + len, buf.length, buf, 0);
	/*
	hb = buf;
	offset = 0;
	*/




    }

    protected HeapCharBuffer(char[] buf,
				   int mark, int pos, int lim, int cap,
				   int off)
    {

	super(mark, pos, lim, cap, buf, off);
	/*
	hb = buf;
	offset = off;
	*/




    }

    public CharBuffer slice() {
	return new HeapCharBuffer(hb,
					-1,
					0,
					this.remaining(),
					this.remaining(),
					this.position() + offset);
    }

    public CharBuffer duplicate() {
	return new HeapCharBuffer(hb,
					this.markValue(),
					this.position(),
					this.limit(),
					this.capacity(),
					offset);
    }

    public CharBuffer asReadOnlyBuffer() {

	return new HeapCharBufferR(hb,
				     this.markValue(),
				     this.position(),
				     this.limit(),
				     this.capacity(),
				     offset);



    }



    protected int ix(int i) {
	return i + offset;
    }

    public char get() {
	return hb[ix(nextGetIndex())];
    }

    public char get(int i) {
	return hb[ix(checkIndex(i))];
    }

    public CharBuffer get(char[] dst, int offset, int length) {
	checkBounds(offset, length, dst.length);
	if (length > remaining())
	    throw new BufferUnderflowException();
	System.arraycopy(hb, ix(position()), dst, offset, length);
	position(position() + length);
	return this;
    }

    public boolean isDirect() {
	return false;
    }



    public boolean isReadOnly() {
	return false;
    }

    public CharBuffer put(char x) {

	hb[ix(nextPutIndex())] = x;
	return this;



    }

    public CharBuffer put(int i, char x) {

	hb[ix(checkIndex(i))] = x;
	return this;



    }

    public CharBuffer put(char[] src, int offset, int length) {

	checkBounds(offset, length, src.length);
	if (length > remaining())
	    throw new BufferOverflowException();
	System.arraycopy(src, offset, hb, ix(position()), length);
	position(position() + length);
	return this;



    }

    public CharBuffer put(CharBuffer src) {

	if (src instanceof HeapCharBuffer) {
	    if (src == this)
		throw new IllegalArgumentException();
	    HeapCharBuffer sb = (HeapCharBuffer)src;
	    int n = sb.remaining();
	    if (n > remaining())
		throw new BufferOverflowException();
	    System.arraycopy(sb.hb, sb.ix(sb.position()),
			     hb, ix(position()), n);
	    sb.position(sb.position() + n);
	    position(position() + n);
	} else if (src.isDirect()) {
	    int n = src.remaining();
	    if (n > remaining())
		throw new BufferOverflowException();
	    src.get(hb, ix(position()), n);
	    position(position() + n);
	} else {
	    super.put(src);
	}
	return this;



    }

    public CharBuffer compact() {

	System.arraycopy(hb, ix(position()), hb, ix(0), remaining());
	position(remaining());
	limit(capacity());
	return this;



    }








































































































































































































































































































































    String toString(int start, int end) {		// package-private
	try {
	    return new String(hb, start + offset, end - start);
	} catch (StringIndexOutOfBoundsException x) {
	    throw new IndexOutOfBoundsException();
	}
    }


    // --- Methods to support CharSequence ---

    public CharSequence subSequence(int start, int end) {
        if ((start < 0)
	    || (end > length())
	    || (start > end))
	    throw new IndexOutOfBoundsException();
        int len = end - start;
        return new HeapCharBuffer(hb,
				      -1, 0, len, len,
				      offset + position() + start);
    }






    public ByteOrder order() {
	return ByteOrder.nativeOrder();
    }



}

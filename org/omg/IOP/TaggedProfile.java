package org.omg.IOP;


/**
* org/omg/IOP/TaggedProfile.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../../../../src/share/classes/org/omg/PortableInterceptor/IOP.idl
* Monday, November 10, 2008 6:32:14 AM GMT
*/


/** 
     * Object references have at least one tagged profile. Each profile 
     * supports one or more protocols and encapsulates all the basic 
     * information the protocols it supports need to identify an object. 
     * Any single profile holds enough information to drive a complete 
     * invocation using any of the protocols it supports; the content 
     * and structure of those profile entries are wholly specified by 
     * these protocols.
     */
public final class TaggedProfile implements org.omg.CORBA.portable.IDLEntity
{

  /** The tag, represented as a profile id. */
  public int tag = (int)0;

  /** The associated profile data. */
  public byte profile_data[] = null;

  public TaggedProfile ()
  {
  } // ctor

  public TaggedProfile (int _tag, byte[] _profile_data)
  {
    tag = _tag;
    profile_data = _profile_data;
  } // ctor

} // class TaggedProfile

package com.sun.corba.se.ActivationIDL.LocatorPackage;


/**
* com/sun/corba/se/ActivationIDL/LocatorPackage/ServerLocationHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../../../../../../src/share/classes/com/sun/corba/se/ActivationIDL/activation.idl
* Sunday, November 9, 2008 11:00:02 PM PST
*/


// endpoint
abstract public class ServerLocationHelper
{
  private static String  _id = "IDL:ActivationIDL/Locator/ServerLocation:1.0";

  public static void insert (org.omg.CORBA.Any a, com.sun.corba.se.ActivationIDL.LocatorPackage.ServerLocation that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.sun.corba.se.ActivationIDL.LocatorPackage.ServerLocation extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "hostname",
            _tcOf_members0,
            null);
          _tcOf_members0 = com.sun.corba.se.ActivationIDL.ORBPortInfoHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (com.sun.corba.se.ActivationIDL.ORBPortInfoListHelper.id (), "ORBPortInfoList", _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "ports",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.sun.corba.se.ActivationIDL.LocatorPackage.ServerLocationHelper.id (), "ServerLocation", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.sun.corba.se.ActivationIDL.LocatorPackage.ServerLocation read (org.omg.CORBA.portable.InputStream istream)
  {
    com.sun.corba.se.ActivationIDL.LocatorPackage.ServerLocation value = new com.sun.corba.se.ActivationIDL.LocatorPackage.ServerLocation ();
    value.hostname = istream.read_string ();
    value.ports = com.sun.corba.se.ActivationIDL.ORBPortInfoListHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.sun.corba.se.ActivationIDL.LocatorPackage.ServerLocation value)
  {
    ostream.write_string (value.hostname);
    com.sun.corba.se.ActivationIDL.ORBPortInfoListHelper.write (ostream, value.ports);
  }

}
